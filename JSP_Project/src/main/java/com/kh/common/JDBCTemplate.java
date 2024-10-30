package com.kh.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	// JDBC 등록 및 도구 모음

	// 1.Connection 객체 생성(DB 접속) 후 반환 메소드
	public static Connection getConnection() {

		// 파일에 DB접속정보 등록하고 해당 파일 읽어와서 처리하기
		Properties prop = new Properties();

		// 읽어올 파일 경로를 잡아서 해당 파일 읽어와서 등록처리하기
		// JDBCTemplate.class : JDBCTemplate 클래스를 참조 하겠다.
		// .getResource("") : 클래스가 포함된 패키지 경로를 찾는다.
		// .getPath() : 해당 경로를 문자열로 반환
		// .getResource("/") : /를 넣으면 최상단 경로가 기준이 됨
		// 실행되는 피씨환경 기준으로 해당 파일위치를 찾아내기 때문에 시작경로가 달라져도 찾아올 수 있다.
		String filePath = JDBCTemplate.class.getResource("/resources/driver/driver.properties").getPath();
		// 읽어온 파일 경로를 이용하여 파일 내부 정보 추출하기

		// try문 벗어나서 선언구문 미리 작성
		Connection conn = null;

		try {
			// 스트림에 해당 파일경로 및 파일명 넣어서 읽어오기
			prop.load(new FileInputStream(filePath));

			// 1)jdbc 드라이버 등록
			Class.forName(prop.getProperty("driver"));

			// 2)DB 접속정보를 넣은 Connection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));
			
			//3) 자동커밋기능 해제하기 (트랜잭션 관리 직접하기위해)
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(filePath);

		return conn;// 생성된 커넥션 객체 반환
	}

	// 2. 전달받은 Connection 객체를 이용하여 commit 처리 메소드
	public static void commit(Connection conn) {

		try {
			// conn이 null 이 아니고 닫혀있지도 않을때 반납하기
			if (conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 3. 전달받은 Connection 객체를 이용하여 rollback 처리 메소드
	public static void rollback(Connection conn) {

		try {
			// conn이 null 이 아니고 닫혀있지도 않을때 반납하기
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 4. 전달받은 Connection 객체 반납 메소드
	public static void close(Connection conn) {
		try {
			// conn이 null 이 아니고 닫혀있지도 않을때 반납하기
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 5. 전달받은 Statement 객체 반납 메소드
	public static void close(Statement stmt) {
		try {
			// stmt이 null 이 아니고 닫혀있지도 않을때 반납하기
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 6. 전달받은 ResultSet 객체 반납 메소드
	public static void close(ResultSet rset) {
		try {
			// rset이 null 이 아니고 닫혀있지도 않을때 반납하기
			if (rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
