package com.safevoice.controller;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@WebServlet(name = "SocialAuthServlet", urlPatterns = { "/auth/social", "/auth/social/callback" })
public class SocialLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SocialLoginServlet.class);

	// Google OAuth 설정 값을 하드코딩(아래 설정값을 입력한 상태로 push할 경우에 오류 발생 및 push 불가되기에 삭제)
	private String googleClientId = ""; // 여기에 Google OAuth Client ID 입력
	private String googleClientSecret = ""; // 여기에 Google OAuth Client Secret 입력
	private String googleRedirectUri = ""; // 여기에 Google OAuth Redirect URI 입력
	// 서블릿 초기화 시, Google OAuth 설정 값을 확인합니다.

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// Google OAuth 환경 변수가 설정되지 않았을 경우 예외 처리
		if (googleClientId == null || googleClientSecret == null || googleRedirectUri == null) {
			logger.error("Google OAuth 환경 변수가 설정되지 않았습니다.");
			throw new ServletException("Missing Google OAuth configuration");
		}

		// 설정 값 로그로 출력 (디버깅용)
		logger.info("Google OAuth 설정 로드 완료");
		logger.info("Client ID: {}", googleClientId);
		logger.info("Client Secret: {}", googleClientSecret);
		logger.info("Redirect URI: {}", googleRedirectUri);
	}

	// HTTP GET 요청을 처리하는 메소드
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		String provider = req.getParameter("provider");

		// 경로가 /auth/social 인 경우, Google 로그인 요청을 처리합니다.
		if ("/auth/social".equals(path)) {
			if ("google".equals(provider)) {
				// 1) 구글 로그인 요청 URL 생성
				// GoogleAuthorizationCodeRequestUrl을 사용해 로그인 URL을 생성합니다.
				String authUrl = new GoogleAuthorizationCodeRequestUrl(googleClientId, googleRedirectUri,
						Collections.singletonList("profile email")) // 요청할 권한 (profile, email)
						.setAccessType("offline") // refresh token을 받기 위해 "offline"으로 설정
						.build();

				// 구글 로그인 페이지로 리다이렉트
				resp.sendRedirect(authUrl);
			} else {
				// 지원하지 않는 프로바이더일 경우 로그인 페이지로 리다이렉트
				resp.sendRedirect(req.getContextPath() + "/GoSignIn.do");
			}
		}
		// /auth/social/callback 경로로 콜백을 받는 경우, Google OAuth 인증 후 처리
		else if ("/auth/social/callback".equals(path)) {
			if ("google".equals(provider)) {
				String code = req.getParameter("code"); // Google OAuth 인증 후 받은 code
				String error = req.getParameter("error"); // 오류 발생 시 받는 파라미터

				// 오류가 발생한 경우, 로그인 페이지로 리다이렉트
				if (error != null || code == null) {
					logger.warn("Google OAuth callback error: {}", error);
					resp.sendRedirect(req.getContextPath() + "/GoSignIn.do");
					return;
				}

				try {
					// 2) 코드 -> 토큰 교환
					// 인증 받은 code를 통해 access token과 id token을 요청합니다.
					GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(),
							JacksonFactory.getDefaultInstance(), googleClientId, googleClientSecret, code,
							googleRedirectUri).execute();

					// 3) ID 토큰 파싱
					// Google에서 받은 ID 토큰을 파싱하여 사용자 정보를 확인합니다.
					GoogleIdToken idToken = tokenResponse.parseIdToken();
					JSONObject payload = new JSONObject(idToken.getPayload().toString());
					String email = payload.optString("email"); // 이메일 추출
					String name = payload.optString("name", ""); // 이름 추출

					// TODO: 로그인 처리 로직 (예: DB 조회/생성, 세션 설정 등)
					// 예: User user = userService.findOrCreateByEmail(email, name);
					// req.getSession().setAttribute("user", user);

					// 4) 로그인 성공 후 리다이렉트
					// 로그인 성공 후 메인 페이지로 리다이렉트
					resp.sendRedirect(req.getContextPath() + "/GoMainPageAdult.do");
					return;

				} catch (Exception ex) {
					// 5) 예외 처리
					logger.error("Google OAuth 토큰 교환 또는 파싱 중 예외 발생", ex);
					resp.sendRedirect(req.getContextPath() + "/GoSignIn.do");
					return;
				}

			} else {
				// 기타 프로바이더 처리
				resp.sendRedirect(req.getContextPath() + "/GoSignIn.do");
			}
		}
	}
}
