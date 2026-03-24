package com.safevoice.controller.Member;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;
import com.safevoice.util.ConfigUtil;

import io.github.cdimascio.dotenv.Dotenv;

public class GoogleCallbackService implements Command {

	private static final String CLIENT_ID = 
			ConfigUtil.get("google.client.id");
			
	private static final String CLIENT_SECRET = 
			ConfigUtil.get("google.client.secret");
			
    private static final String REDIRECT_URI =
    		ConfigUtil.get("google.redirect.uri");

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) {

        try {

            // 1️. code 받기
            String code = request.getParameter("code");

            // 2️. access_token 요청
            URL url = new URL("https://oauth2.googleapis.com/token");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty(
            	    "Content-Type",
            	    "application/x-www-form-urlencoded"
            	);
            
            String params =
                    "code=" + code
                    + "&client_id=" + CLIENT_ID
                    + "&client_secret=" + CLIENT_SECRET
                    + "&redirect_uri=" + REDIRECT_URI
                    + "&grant_type=authorization_code";
            

            OutputStream os = conn.getOutputStream();
            os.write(params.getBytes("UTF-8"));
            os.flush();

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line;

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonObject tokenJson =
                    JsonParser.parseString(result).getAsJsonObject();

            String accessToken =
                    tokenJson.get("access_token").getAsString();


            // 3️. userinfo 요청
            URL userUrl =
                    new URL("https://www.googleapis.com/oauth2/v2/userinfo");

            HttpURLConnection userConn =
                    (HttpURLConnection) userUrl.openConnection();

            userConn.setRequestProperty(
                    "Authorization",
                    "Bearer " + accessToken
            );

            BufferedReader userBr =
                    new BufferedReader(new InputStreamReader(userConn.getInputStream()));

            String userResult = "";

            while ((line = userBr.readLine()) != null) {
                userResult += line;
            }

            JsonObject userJson =
                    JsonParser.parseString(userResult).getAsJsonObject();

            String email = userJson.get("email").getAsString();
            String name = userJson.get("name").getAsString();
            String googleId = userJson.get("id").getAsString();

            MemberDAO mdao = new MemberDAO();

            MemberVO member = mdao.findBySocial(email);

            if(member == null) {

                HttpSession session = request.getSession();

                session.setAttribute("social_email", email);
                session.setAttribute("social_name", name);
                session.setAttribute("social_id", googleId);

                return "redirect:/GoSelectMemberType.do";
            }
            

            // 6️. 세션 로그인
            HttpSession session = request.getSession();
            List<MemberVO> childList = mdao.selectMyChildren(member.getId()); // 로그인한 회원의 아이리스트
			session.setAttribute("loginMember", member); // 로그인한 회원 정보
			session.setAttribute("loginId", member.getId()); // 로그인한 회원의 아이디
			session.setAttribute("childList", childList);
            if(member.getMemType().equals("P")){
                return "redirect:/GoMainPageAdult.do";
            }
            else{
                return "redirect:/GoMainPageChild.do";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/GoLogin.do";
    }
}