package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.JDBCTemplate;
import com.kh.common.model.vo.PageInfo;

public class BoardService {
	
	
	//총 게시글 개수 조회 메소드
	public int listCount() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int listCount = new BoardDao().listCount(conn);
		
		//조회구문이니 트랜잭션 처리 하지 않음
		JDBCTemplate.close(conn);
		
		return listCount;
	}
	
	
	//게시글 목록 조회 메소드 
	public ArrayList<Board> selectList(PageInfo pi){
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Board> list = new BoardDao().selectList(conn,pi);
		
		//조회 구문 (트랜잭션 하지 않음)
		JDBCTemplate.close(conn); //자원반납
		
		return list;//목록 반환
	}

	//조회수 증가 메소드
	public int increaseCount(int bno) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().increaseCount(conn,bno);
		
		//DML(UPDATE) 구문이니 트랜잭션 처리하기 
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		//자원반납
		JDBCTemplate.close(conn);
		
		return result;//결과값 반환
	}

	//게시글 상세보기
	public Board selectBoard(int bno) {
		
		Connection conn = JDBCTemplate.getConnection();
				
		Board b = new BoardDao().selectBoard(conn,bno);
		
		//조회구문 트랜잭션처리 할 필요없음
		JDBCTemplate.close(conn);
		return b;
	}
	
	//카테고리 목록 조회
	public ArrayList<Category> selectCategory() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Category> cList = new BoardDao().selectCategory(conn);
		
		JDBCTemplate.close(conn);
		
		return cList;
	}
	
	
	//게시글 등록메소드
	public int insertBoard(Board b,Attachment at) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		//boardNo를 미리 뽑아놓고 해당 번호로 게시글과 첨부파일 넣어주기 
		int boardNo = new BoardDao().selectBoardNo(conn);
		
		if(boardNo != 0) { //추출한 게시글 번호가 0이 아닐때 (제대로 추출되었을때)
			//게시글 객체에 추출한 게시글 번호 넣어주기 
			b.setBoardNo(boardNo);
			int result = new BoardDao().insertBoard(conn,b);//게시글 등록이 잘 되었는지 여부
			
			//첨부파일 등록처리 후 사용할 변수 
			int result2 = 1; //첨부파일이 없어도 게시글 등록처리는 될 수 있도록 1로 초기화해놓기
			//첨부파일이 없는 경우 게시글만 등록할 수 있도록 처리 
			if(result>0 && at!=null) {//게시글 등록이 성공했고 전달받은 첨부파일 정보가 있을때 
				//첨부파일정보 DB에 등록
				//첨부파일이 어떠한 게시글에 등록된 첨부파일인지 알 수 있도록 참조게시글 번호 추가해주기 
				at.setRefBno(boardNo);
				
				result2 = new BoardDao().insertAttachment(conn,at);//첨부파일 등록이 되었는지 여부
			}
			//게시글등록 또는 게시글+첨부파일 등록 처리 후 
			//트랜잭션처리하기 
			if(result*result2>0) { //둘다 0이 아닌경우만 조건 통과
				JDBCTemplate.commit(conn);
			}else {//실패
				JDBCTemplate.rollback(conn);
			}
			//자원반납
			JDBCTemplate.close(conn);
			
			//번호는 잘 뽑혔고 등록처리 후 결과값 
			return result*result2;
			
		}else {//게시글번호부터 제대로 추출 안됐을때
			//자원반납 
			JDBCTemplate.close(conn);
			return boardNo; //등록처리하지 않고 되돌리기 boardNo 제대로 추출안됐으면 0으로 돌아옴
		}
	}

	//첨부파일 조회메소드
	public Attachment selectAttachment(int bno) {

		Connection conn = JDBCTemplate.getConnection();
		
		Attachment at = new BoardDao().selectAttachment(conn,bno);
		
		JDBCTemplate.close(conn);
		
		return at;
	}

	//게시글 수정 메소드
	public int updateBoard(Board b, Attachment at) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		BoardDao dao = new BoardDao();
		
		//게시글 수정 
		int result = dao.updateBoard(conn,b);
		
		//첨부파일 수정 또는 등록 
		int result2 = 1; //첨부파일 처리 필요없을때를 위해 1로 초기화(게시글만 수정시)
		
		
		//첨부파일이 있으면 ?
		if(at != null) {
			if(at.getFileNo()!=0) {//기존 첨부파일이 있는 경우 - 수정 
				result2 = dao.updateAttachment(conn,at);
			}else {//기존 첨부파일 없는 경우 - 등록
				result2 = dao.insertAttachment(conn, at);
			}
		}
		//트랜잭션 처리 하기 
		//둘중 하나라도 문제가 생기면 전체 되돌리기 또는 확정 (트랜잭션 묶음)
		if(result*result2>0) {//확정 (둘다 성공 또는 첨부파일 없는경우)
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result*result2;
	}

	//게시글 삭제 메소드 
	public int deleteBoard(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().deleteBoard(conn,boardNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	//사진게시글 작성 메소드
	public int insertPhoto(Board b, ArrayList<Attachment> atList) {
		Connection conn = JDBCTemplate.getConnection();
				
		BoardDao dao = new BoardDao(); 
		//게시글 번호 추출해오기 
		int boardNo = dao.selectBoardNo(conn);
		
		//게시글 등록 
		b.setBoardNo(boardNo);//게시글 번호 채워주기
		int result = dao.insertPhoto(conn,b);
		
		//첨부파일 등록
		//첨부파일 목록 + 참조 게시글 번호 
		int result2 = dao.insertAttachmentList(conn, atList,boardNo);
		
		
		//트랜잭션 묶음 처리 
		if(result*result2>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result*result2;
	}

	//사진게시글 목록 조회 메소드
	public ArrayList<Board> selectThumbnailList() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Board> list = new BoardDao().selectThumbnailList(conn);
		
		//select 구문이니 트랜잭션처리 하지 않음
		JDBCTemplate.close(conn);
		
		return list;
	}

	//사진게시글 상세 조회 메소드(첨부파일)
	public ArrayList<Attachment> selectAttachmentList(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Attachment> atList =  new BoardDao().selectAttachmentList(conn,boardNo);
		
		JDBCTemplate.close(conn);
		
		return atList;
	}

	//댓글작성
	public int insertReply(Reply r) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().insertReply(conn,r);
		
		//DML
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		//자원반납
		JDBCTemplate.close(conn);
		
		
		return result;
	}

	//댓글 목록 조회
	public ArrayList<Reply> replyList(int refBno) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Reply> rList = new BoardDao().replyList(conn,refBno);
		
		JDBCTemplate.close(conn);
		
		
		return rList;
	}
	
	
	
	
	
	
	

}
