package com.safevoice.controller.Alert;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.safevoice.controller.Command;
import com.safevoice.db.AlertDAO;
import com.safevoice.model.AlertVO;

public class InputFileService implements Command {
	
	// 파일 업로드 -> python으로 음성파일 분석 -> PushService message 담기

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 1. 파일 업로드 처리
        String uploadDir = "C:/Users/smhrd/Desktop/ML_part/recoding";
        int sizeLimit = 100 * 1024 * 1024; // 100MB
        MultipartRequest mtr = new MultipartRequest(request, uploadDir, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());

        // 2. 업로드된 파일명과 자녀 ID 획득
        String fileName = mtr.getFilesystemName("file");
        String filePath = uploadDir + "/" + fileName;
        String childId = mtr.getParameter("memberId");
     // 🛡️ 파일 존재 및 유효성 확인
        File uploadedFile = new File(filePath);
        if (!uploadedFile.exists()) {
            System.out.println("🚫 파일이 존재하지 않습니다: " + filePath);
            throw new IOException("파일이 존재하지 않습니다!");
        }
        if (uploadedFile.length() == 0) {
            System.out.println("🚫 파일이 비어 있습니다: " + filePath);
            throw new IOException("업로드된 파일 크기가 0입니다!");
        }

        // 💤 혹시 파일 저장 직후 바로 실행되는 걸 막기 위한 잠깐의 대기
        try {
            Thread.sleep(300); // 300ms 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("📁 file: " + fileName);
        System.out.println("📁 filePath: " + filePath);
        System.out.println("🧸 memberId = " + childId);

        // 3. Python 분석 실행
        String result = "";
        try {
            ProcessBuilder pb = new ProcessBuilder("C:/Users/smhrd/AppData/Local/Programs/Python/Python310/python.exe", "C:/Users/smhrd/Desktop/ML_part/safevoice.py", fileName);
            Thread.sleep(300);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            result = reader.readLine();
            System.out.println("🐾 [DEBUG] Python 출력: " + result);
            
         // stderr 출력 파일 저장 시도 (임시 디버깅용)
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            BufferedWriter errOut = new BufferedWriter(new FileWriter("C:/Users/smhrd/Desktop/python_error_log.txt"));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errOut.write(errorLine + "\n");
            }
            errOut.close();
            
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (result == null || result.trim().isEmpty()) {
            throw new IOException("Python 실행 결과가 없습니다. MLtest.py에서 print(...)로 결과를 출력했는지 확인하세요.");
        }

        // 4. 결과 숫자 파싱
        
        String level = result.trim();

        // 5. 알림 DB 저장 (메시지 없이 유형만 저장)
        AlertVO alert = new AlertVO();
	    alert.setMemberId(childId);
	    alert.setAlertType(level);
	    alert.setLat("0.0");
	    alert.setLon("0.0");
	    
        if (alert.getAlertType() != null) {
            System.out.println("🔍 ALERT_TYPE 길이: " + alert.getAlertType().length());
            System.out.println("🧾 ALERT_TYPE 내용: " + alert.getAlertType());
        } else {
            System.out.println("🚨 ALERT_TYPE이 null이에요!!");
        }

        AlertDAO dao = new AlertDAO();
        dao.setAlertInfo(alert);

        // 6. 푸시 알림 전송 (level 1 이상만 전송)
        if (level != null && (level.equals("주의") || level.equals("경고"))) {
            String message;
            switch (level) {
                case "주의" -> message = "자녀의 활동에서 주의가 필요한 징후가 발견되었어요.";
                case "경고" -> message = "⚠️ 자녀의 활동에서 위험 징후가 감지되었습니다.";
                default -> message = "분석 결과를 해석할 수 없습니다.";
            }
            PushNotificationService pushService = new PushNotificationService();
            pushService.sendNotification(childId, "AI 분석 결과", message);
        }
        return "redirect:/GoMainPageChild.do";
    }
}
