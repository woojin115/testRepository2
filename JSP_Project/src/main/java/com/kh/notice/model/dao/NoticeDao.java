package com.kh.notice.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.notice.model.vo.Notice;

import oracle.jdbc.proxy.annotation.Pre;

public class NoticeDao {
	
	//생성자구문에서 mapper 파일 읽어오기
	private Properties prop = new Properties();
	
	public NoticeDao() {
		
		//notice-mapper.xml파일 경로 알아오기
		String filePath = NoticeDao.class.getResource("/resources/mappers/notice-mapper.xml").getPath();
		
		try {
			//파일 읽어오기
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//공지사항 목록 조회 메소드
	public ArrayList<Notice> selectNoticeList(Connection conn) {
		
		//SELECT 조회
		PreparedStatement pstmt = null;
		ResultSet rset = null; //조회결과집합 담을 객체 선언
		//목록담을 리스트 
		ArrayList<Notice> list = new ArrayList<>();
		
		String sql = prop.getProperty("selectNoticeList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			//위치홀더 채울 필요 없으니 바로 실행
			rset = pstmt.executeQuery(); //조회 결과 집합 ResultSet으로 받기
			
			//ResultSet에 담긴 데이터가 있다면 추출하여 Notice객체에 담고 리스트에 추가하기 
			
			//조회된 결과가 여러개일 수 있으니 모든 결과를 추출하기 위해 반복문 필요
			while(rset.next()) { //다음 행이 있다면 rset.next() : 다음행이 있으면 true 아니면 false 반환
				list.add(new Notice(rset.getInt("NOTICE_NO"),
									rset.getString("NOTICE_TITLE"),
									rset.getString("USER_NAME"),
									rset.getInt("COUNT"),
									rset.getDate("CREATE_DATE")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//자원반납 (관련있는것들은 생성 역순으로 반납)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;//조회 목록 반환
	}
	
	
	//공지글 등록 메소드 
	
	public int insertNotice(Connection conn,Notice n) {
		
		//INSERT(DML) 구문 
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertNotice");
		
		try {
			//미완성 sql 구문 전달하며 pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			//noticeWriter는 String 자료형이고 Notice테이블의 notice_writer 컬럼은 number타입이니까 형변환으로 맞춰주기
			pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter()));
			
			//완성된 sql 실행 및 결과 받기
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	
	//조회수 증가 메소드
	public int increaseCount(Connection conn,int nno) {
		//UPDATE(DML)
		
		PreparedStatement pstmt = null;
		int result = 0; //처리된 행수 받을 변수
		
		String sql = prop.getProperty("increaseCount");
		
		try {
			//미완성 sql 구문 전달하며 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//위치홀더 채우기
			pstmt.setInt(1, nno);
			
			//완성되었으면 실행 및 결과받기
			result = pstmt.executeUpdate();//dml이니 executeUpdate() 메소드
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	
	//공지글 상세 조회 메소드
	public Notice selectNotice(Connection conn,int nno) {
		
		//SELECT 준비
		PreparedStatement pstmt = null;
		ResultSet rset = null; //조회구문시 결과집합 담을 객체 선언
		
		//조회된 글정보가 있다면 객체 생성하여 담아줄 객체변수 준비 
		Notice n = null; 
		
		String sql = prop.getProperty("selectNotice");
		
		try {
			//미완성 sql구문 전달하며 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//위치홀더 채우기
			pstmt.setInt(1,nno);
			
			//완성된 구문 실행 및 결과 받기 (결과집합 반환받음)
			rset = pstmt.executeQuery(); //select구문은 executeQuery()메소드
			
			//조회 조건이 식별자를 이용한 조건이기 때문에 결과가 나온다면 하나의 행만 나올 수 있음.
			//조건처리를 있는지 없는지만 하면 된다.
			if(rset.next()) {
				//조회된 행이 있다면 해당 데이터 추출하여 자바 VO에 담아주기 
				
				n = new Notice(rset.getInt("NOTICE_NO"),
							   rset.getString("NOTICE_TITLE"),
							   rset.getString("NOTICE_CONTENT"),
							   rset.getString("USER_NAME"),
							   rset.getInt("COUNT"),
							   rset.getDate("CREATE_DATE"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//자원반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return n;//조회되었으면 객체 조회 안되었으면 null 반환		
	}
	
	
	
	
	//공지글 수정 메소드
	public int updateNotice(Connection conn,Notice n) {
		
		///UPDATE(DML)
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("updateNotice");
		
		
		try {
			//미완성 sql전달하며 pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//위치홀더 채워주기 
			pstmt.setString(1,n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3,n.getNoticeNo());
			
			//완성되면 수행 및 결과받기
			result = pstmt.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			JDBCTemplate.close(pstmt);
		}
		return result;//서비스에게 반환
	}
	
	
	//공지글 삭제 메소드
	public int deleteNotice(Connection conn,int nno) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("deleteNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, nno);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
