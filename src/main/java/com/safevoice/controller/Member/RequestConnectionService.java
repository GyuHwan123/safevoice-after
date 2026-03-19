package com.safevoice.controller.Member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.mail.*;
import javax.mail.internet.*;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;


public class RequestConnectionService implements Command {
	
	// 자녀 연결 (자녀에게 인증번호 (메일) 보내기)
	
	public String execute (HttpServletRequest request, HttpServletResponse response) throws IOException{
        String targetId = request.getParameter("targetId");
        String targetEmail = request.getParameter("targetEmail");
        
        MemberDAO mdao = new MemberDAO();
        MemberVO mvo = new MemberVO();
        mvo.setId(targetId);
        mvo.setEmail(targetEmail);

        int row = mdao.sendCode(mvo); // ✅ MemberVO로 감싸서 전달

        
		if (row > 0) {
			String code = String.valueOf((int)((Math.random() * 900000) + 100000)); // 랜덤 6자리 수 생성
			
            String fromEmail = "jungm02719@gmail.com"; // 메일 보내는 사람 (나) 이메일
            String appPassword = "jmrtsbrncmqeikjj"; // 2단계 인증 앱 비밀번호 (일회용)
			
			Properties props = new Properties(); // 이거 걍 SMTP 서버 설정 코드
            props.put("mail.smtp.host", "smtp.gmail.com"); // host -> gmail
            props.put("mail.smtp.port", "587"); // port -> 587 (Gmail이 TLS 보안 연결에 사용하는 포트 번호)
            props.put("mail.smtp.auth", "true"); // 인증 필요 여부
            props.put("mail.smtp.starttls.enable", "true"); // 보안 연결 사용할 거임? -> 넹 (이러면 암호화돼서 전송)
            
            Session session = Session.getInstance(props, new Authenticator() { // 로그인 정보 전달
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, appPassword); // 이메일 / 비밀번호 보내주기 (로그인)
                }
            });
            
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(fromEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetEmail));
                    message.setSubject("인증번호 안내");
                    message.setText("인증번호는 " + code + " 입니다.");
                    Transport.send(message);

                    HttpSession codeSession = request.getSession();
                    codeSession.setAttribute("realCode", code);
                    codeSession.setAttribute("receiverId", targetId);
                    codeSession.setAttribute("targetEmail", targetEmail);

                    return "GoRequestConnection.do";

                } catch (MessagingException e) {
                    e.printStackTrace();
                    request.setAttribute("errorMsg", "이메일 전송에 실패했습니다. 이메일 주소나 네트워크 상태를 확인해주세요.");
                    return "GoRequestConnection.do";
                }

            } else {
                request.setAttribute("errorMsg", "입력하신 ID와 이메일이 일치하지 않습니다!");
                return "GoRequestConnection.do";
            }

		}
	}
