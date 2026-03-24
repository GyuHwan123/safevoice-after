package com.safevoice.frontcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.safevoice.controller.Command;
import com.safevoice.controller.MainPageAdultService;
import com.safevoice.controller.MainPageChildService;
import com.safevoice.controller.MenuMainService;
import com.safevoice.controller.Alert.GetAlertHistoryService;
import com.safevoice.controller.Alert.InputFileService;
import com.safevoice.controller.Alert.SaveSubscriptionService;
import com.safevoice.controller.Alert.SendSosAlertService;
import com.safevoice.controller.Member.ChangePasswordService;
import com.safevoice.controller.Member.GoogleCallbackService;
import com.safevoice.controller.Member.GoogleLoginService;
import com.safevoice.controller.Member.GoogleSignupService;
import com.safevoice.controller.Member.IdDuplicateCheckService;
import com.safevoice.controller.Member.LoginService;
import com.safevoice.controller.Member.LogoutService;
import com.safevoice.controller.Member.MarkAlertsAsRead;
import com.safevoice.controller.Member.ModifyMemberService;
import com.safevoice.controller.Member.RequestConnectionService;
import com.safevoice.controller.Member.SignInService;
import com.safevoice.controller.Member.SignOutService;
import com.safevoice.controller.Member.UpdateMemberService;
import com.safevoice.controller.Member.VerifyCodeService;
import com.safevoice.controller.Member.VerifyIdentityService;
import com.safevoice.controller.Member.ViewChildListService;
import com.safevoice.tomb.DeleteChildService;
import com.safevoice.tomb.RepeatAlertService;
import com.safevoice.tomb.StopNotificationService;

public class CommandRouter {

	private static Map<String, Command> map = new HashMap<>();

	static {
		map.put("SignIn.do", new SignInService()); // 회원가입
		map.put("Login.do", new LoginService()); // 로그인
		map.put("Logout.do", new LogoutService()); // 로그아웃
		map.put("ChangePassword.do", new ChangePasswordService()); // 비밀번호변경 - 진짜변경
		map.put("VerifyIdentity.do", new VerifyIdentityService()); // 비밀번호변경 - 본인확인
		map.put("SignOut.do", new SignOutService()); // 회원탈퇴
		map.put("ModifyMember.do", new ModifyMemberService()); // 회원정보수정
	    map.put("RequestConnection.do", new RequestConnectionService()); // 자녀 등록 - 자녀 연결 (코드 전송)
	    map.put("VerifyCode.do", new VerifyCodeService()); // 자녀 등록 - 자녀 연결 (코드 확인)
//	    map.put("/test/SendPush.do", new SendPushNotificationService());
	    map.put("SaveSubscription.do", new SaveSubscriptionService()); // 알림 보내기 - 구독 정보 저장
	    map.put("InputFile.do", new InputFileService());
	    map.put("RepeatAlert.do", new RepeatAlertService());
	    map.put("StopNotification.do", new StopNotificationService());
	    map.put("GetAlertHistory.do", new GetAlertHistoryService()); // 알림 내역
	    map.put("MenuMain.do", new MenuMainService()); // 메뉴 페이지
	    map.put("MainPageChild.do", new MainPageChildService()); // 아이 페이지
	    map.put("MainPageAdult.do", new MainPageAdultService()); // 부모 페이지
	    map.put("IdDuplicateCheck.do", new IdDuplicateCheckService()); // ID 중복 체크
	    map.put("ViewChildList.do", new ViewChildListService()); // 자녀 관리
	    map.put("DeleteChild.do", new DeleteChildService()); // 자녀 삭제
	    map.put("MarkAlertsAsRead.do", new MarkAlertsAsRead());
	    map.put("UpdateMember.do", new UpdateMemberService());
	    map.put("SendSosAlert.do", new SendSosAlertService()); // SOS 알림
	    map.put("GoogleLogin.do", new GoogleLoginService());
	    map.put("GoogleCallback.do", new GoogleCallbackService());
	    map.put("GoogleSignup.do", new GoogleSignupService());
	}
	
	public static Command getCommand(String path) {
        return map.get(path);
    }
}
