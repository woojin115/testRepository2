package com.kh.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {

	// 공지사항 목록 메소드
	public ArrayList<Notice> selectNoticeList() {

		Connection conn = JDBCTemplate.getConnection();

		// dao에게 커넥션만 전달하며 요청
		ArrayList<Notice> list = new NoticeDao().selectNoticeList(conn);

		// 조회는 트랜잭션 처리하지 않음
		// 자원 반납만 하기
		JDBCTemplate.close(conn);

		return list; // 컨트롤러에게 목록반환

	}

	// 공지사항 등록 메소드
	public int insertNotice(Notice n) {

		Connection conn = JDBCTemplate.getConnection();
		// dao에 전달 및 요청
		int result = new NoticeDao().insertNotice(conn, n);

		// dml(insert) 구문이니 트랜잭션 처리하기
		if (result > 0) {// 성공시 확정
			JDBCTemplate.commit(conn);
		} else {// 실패시 되돌리기
			JDBCTemplate.rollback(conn);
		}

		// 트랜잭션처리 완료되었으니 자원반납
		JDBCTemplate.close(conn);

		return result; // 결과값 컨트롤러에게 반환

	}

	// 공지글 조회수 증가 메소드
	public int increaseCount(int nno) {

		Connection conn = JDBCTemplate.getConnection();

		// 처리된 행 수 돌려받기
		int result = new NoticeDao().increaseCount(conn, nno);

		// DML 구문이니 트랜잭션처리
		if (result > 0) {// 성공시
			JDBCTemplate.commit(conn); // 확정
		} else { // 실패시
			JDBCTemplate.rollback(conn);// 되돌리기
		}
		// 자원반납
		JDBCTemplate.close(conn);

		return result; // 결과값 컨트롤러에 리턴하기
	}

	// 공지글 상세 조회 메소드
	public Notice selectNotice(int nno) {

		Connection conn = JDBCTemplate.getConnection();

		// Dao에 요청하고 공지글정보 받기
		Notice n = new NoticeDao().selectNotice(conn, nno);

		// select - 트랜잭션처리 하지 않음(단순조회)
		JDBCTemplate.close(conn);

		// 컨트롤러에게 공지글 객체 반환
		return n;
	}

	// 공지글 수정 메소드
	public int updateNotice(Notice n) {

		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().updateNotice(conn, n);

		// 트랜잭션 처리
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		// 자원반납
		JDBCTemplate.close(conn);

		return result;
	}

	// 공지글 삭제 메소드
	public int deleteNotice(int nno) {

		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().deleteNotice(conn,nno);

		// 트랜잭션 처리
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		// 자원반납
		JDBCTemplate.close(conn);

		return result;
	}

}
