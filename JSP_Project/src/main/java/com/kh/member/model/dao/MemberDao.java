package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

import oracle.jdbc.proxy.annotation.Pre;

public class MemberDao {
	
	//파일 읽어오기 
	private Properties prop = new Properties();
	
	//기본생성자 호출될때 파일 읽어와서 처리하기 
	public MemberDao() {
		
		// getResource("/") : /는 최상단 == classes 폴더 
		String filePath = MemberDao.class.getResource("/resources/mappers/member-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//로그인 메소드 
	public Member loginMember(Connection conn,String userId,String userPwd) {
		
		//로그인 기능 : R(Select)
		//Select 문 사용시 준비물 
		ResultSet rset = null;//결과 집합 담을 객체 선언 
		PreparedStatement pstmt = null;//sql구문 준비 및 실행 처리결과 받아낼 객체 선언
		Member m = null; //조회된 결과가 있다면 담아갈 회원 정보 객체
		
		//prop 객체에 읽어온 파일 데이터중 loginMember 라는 키값의 sql구문 추출하기 
		String sql = prop.getProperty("loginMember");
		
		try {
			//미완성 sql구문 전달하며 pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//위치홀더에 알맞은 데이터 끼워넣기
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			//위치홀더 채워서 완성된 구문 만들었다면 실행 및 결과 받기 
			rset = pstmt.executeQuery();//결과집합 ResultSet으로 받기
			
			//아이디값은 유니크 제약조건이 걸려있기 때문에 
			//결과가 나온다면 하나의 행만 나올 수 있다.
			//따라서 있는지 없는지 조건 처리만 하면 됨 (반복할 필요 없음)
			
			//rset.next() : 커서가 다음행으로 이동하며 접근할 행이 있다면 true 없으면 false 반환
			if(rset.next()) {//접근할 행이 있을때
				//접근된 행에 있는 데이터를 추출하여 Member 객체에 담아서 반환하기
				//매개변수 생성자 이용하여 데이터 담아주기 
				m = new Member(rset.getInt("USER_NO"),
							   rset.getString("USER_ID"),
							   rset.getString("USER_PWD"),
							   rset.getString("USER_NAME"),
							   rset.getString("PHONE"),
							   rset.getString("EMAIL"),
							   rset.getString("ADDRESS"),
							   rset.getString("INTEREST"),
							   rset.getDate("ENROLL_DATE"),
							   rset.getDate("MODIFY_DATE"),
							   rset.getString("STATUS"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//연관있는 자원끼리는 생성의 역순으로 반납하기 (자원반납)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return m;
	}
	
	
	
	//회원가입 메소드
	public int insertMember(Connection conn,Member m) {
		
		//C(insert) - DML 
		//DML 구문시 필요한 준비물 
		PreparedStatement pstmt = null; //sql구문 전달 및 실행 객체 선언
		int result =0; //처리된 행 수 반환받을 변수 준비
		
		String sql = prop.getProperty("insertMember");
		
		
		try {
			//미완성 sql 구문 전달하며 pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//미완성 부분 위치홀더 채워주기
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getInterest());
			
			//완성됐으면 실행 및 처리결과 받기 
			//DML구문은 처리된 행수를 반환한다. 
			result = pstmt.executeUpdate();//DML구문은 executeUpdate() 메소드 사용
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	
	//정보수정 메소드
	public int updateMember(Connection conn,Member m) {
		//UPDATE 구문 (dml) 
		
		PreparedStatement pstmt = null; //sql구문을 실행 및 결과 받기 위해 필요
		int result = 0; //처리된 결과 행 수 담을 변수
		//prop 객체로 읽어온 xml파일에 있는 구문 추출하기
		String sql = prop.getProperty("updateMember");
		
		
		try {
			//미완성 sql구문 전달하며 pstmt 객체 생성 
			pstmt = conn.prepareStatement(sql);
			
			//미완성 구문 완성시키기 (위치홀더 채워주기)
			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getPhone());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getInterest());
			pstmt.setString(6, m.getUserId());
			
			//완성된 sql 구문 실행시키고 결과받기 
			result = pstmt.executeUpdate();//dml구문은 executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			//자원반납
			JDBCTemplate.close(pstmt);
		}
		
		return result;//결과값 반환하기
	}
	
	
	
	//비밀번호 수정 메소드
	public int updatePwd(Connection conn,HashMap<String,String> map) {
		
		//UPDATE (DML) 구문 
		PreparedStatement pstmt = null;//sql구문 실행 및 결과받을 객체 선언
		int result = 0; //DML 구문이니 실행 후 처리된 행 수 담을 변수
		
		String sql = prop.getProperty("updatePwd"); //sql 구문 준비
		
		try {
			//미완성 sql 구문 전달하며 pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, map.get("updatePwd"));
			pstmt.setString(2, map.get("userId"));
			pstmt.setString(3, map.get("userPwd"));
			
			
			//완성된 sql 구문 실행 및 결과 받기
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//자원반납
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	
	//회원탈퇴 메소드
	public int deleteMember(Connection conn,String userId,String userPwd) {
		
		//UPDATE(DML) 
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("deleteMember");
		
		try {
			//미완성 sql구문 전달하며 생성
			pstmt = conn.prepareStatement(sql);
			
			//위치홀더 채워주기
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			//실행 및 결과받기
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//자원반납
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	
	//아이디 중복확인 메소드
	public boolean idCheck(Connection conn, String inputId) {
		//SELECT
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("idCheck");
		
		boolean flag = false; //조회된게 있으면 true로 변경할 논리값 변수
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputId);
			
			rset = pstmt.executeQuery();
			
			//if(rset.next()) { //조회된게 있으면 true 없으면 false 반환 
			//}
			//조회된 행이 있으면(아이디 중복) true로 전달됨
			flag = rset.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return flag;
	}
	
	
	

}





