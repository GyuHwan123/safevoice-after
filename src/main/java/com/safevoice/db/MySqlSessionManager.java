package com.safevoice.db;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySqlSessionManager {
	
	public static SqlSessionFactory sqlSessionFactory;
	
	public static SqlSessionFactory getFactory() {
		return sqlSessionFactory;
	}
	
	static {
		// 설정파일 경로를 읽어서 DBCP 준비
		String resource = "com/safevoice/db/Mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
					  new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
