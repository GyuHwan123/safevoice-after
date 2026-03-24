package com.safevoice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// FC 안에서 동작할 각각의 일반 POJO 클래스 파일들의 규격!
// 모든 POJO의 부모 인터페이스
public interface Command {

	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
