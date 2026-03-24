package com.safevoice.controller.Member;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;
import com.safevoice.util.ConfigUtil;

import io.github.cdimascio.dotenv.Dotenv;

public class GoogleLoginService implements Command {

	private static final String CLIENT_ID = 
			ConfigUtil.get("google.client.id");
			
    private static final String REDIRECT_URI =
    		ConfigUtil.get("google.redirect.uri");

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String googleUrl =
                "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&response_type=code"
                + "&scope=openid email profile";

		// 외부 이동일 때는 / 를 붙이면 안된다.
		return "redirect:" + googleUrl;
	}
}
