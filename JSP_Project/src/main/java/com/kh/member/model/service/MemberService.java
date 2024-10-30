package com.kh.member.model.service;

import java.sql.Connection;
import java.util.HashMap;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

//Connection 관련 처리 
public class MemberService {
	
	
	//로그인 메소드 
	public Member loginMember(String userId,String userPwd) {
		
		//템플릿에 만들어놓은 Connection 반환 메소드를 이용하여 객체 반환받기
		Connection conn = JDBCTemplate.getConnection();
		
		//DAO에게 요청하기 (커넥션과 전달받은 데이터 전달하며)
		Member m = new MemberDao().loginMember(conn,userId,userPwd);
		
		//Select는 조회구문이기 때문에 트랜잭션 처리 필요 없음 
		//자원 반납만 처리 
		JDBCTemplate.close(conn);//커넥션 반납 
		
		return m;
	}
	
	
	//회원가입 메소드
	public int insertMember(Member m) {
		
		//연결객체 
		Connection conn = JDBCTemplate.getConnection();
		
		//연결객체와 전달받은 데이터 함께 dao에게 요청 및 전달 
		int result = new MemberDao().insertMember(conn,m);
		
		//DML(insert) 구문이니 처리된 결과 행 수를 이용하여 트랜잭션 처리하기 
		//확정 또는 되돌리기 
		if(result>0) {//성공시
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		//자원반납
		JDBCTemplate.close(conn);
		
		//컨트롤러에게 결과값 반환하기
		return result;
	}
	
	
	//정보수정 메소드 
	public int updateMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		//dao에 요청
		int result = new MemberDao().updateMember(conn,m);
		
		//UPDATE는 DML구문이니 트랜잭션처리 해야한다.(commit/rollback)
		if(result>0) {//성공시 확정
			JDBCTemplate.commit(conn);
			
		}else {//실패시 되돌리기
			JDBCTemplate.rollback(conn);
		}
		
		//자원반납
		JDBCTemplate.close(conn);
				
		//컨트롤러에게 결과값 반환하기
		return result;
	}
	
	
	//비밀번호 수정 메소드 
	public int updatePwd(HashMap<String,String> map) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		//결과값 반환받기
		int result = new MemberDao().updatePwd(conn,map);
		
		//DML구문을 수행했으니 반환받은 결과값을 이용하여 트랜잭션 처리하기 
		
		if(result>0) { //성공시 확정
			JDBCTemplate.commit(conn);
		}else {//실패시 원상복구
			JDBCTemplate.rollback(conn);
		}
		//자원반납
		JDBCTemplate.close(conn);
		
		return result;//컨트롤러에게 결과값 반환하기
	}
	
	//회원탈퇴 메소드
	public int deleteMember(String userId,String userPwd) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		//dao에게 요청 전달 및 실행 후 결과값 반환받기
		int result = new MemberDao().deleteMember(conn,userId,userPwd);
				
		//dml구문이니 트랜잭션 처리
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		//자원반납
		JDBCTemplate.close(conn);
		
		//결과값 반환
		return result;
	}

	//아이디 중복확인 메소드
	public boolean idCheck(String inputId) {
		Connection conn = JDBCTemplate.getConnection();
		
		boolean flag = new MemberDao().idCheck(conn,inputId);
		
		JDBCTemplate.close(conn);
		
		return flag;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
