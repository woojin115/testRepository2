package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//전달받은 게시글 번호로 게시글 조회하여 수정화면에 보여줄수 있도록 위임처리하기 
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		//수정페이지로 보내야하는 데이터 
		//게시글 정보,카테고리 목록,첨부파일 정보 
		
		BoardService service = new BoardService();
		
		Board b = service.selectBoard(bno);
		Attachment at = service.selectAttachment(bno);
		ArrayList<Category> cList = service.selectCategory();
		
		request.setAttribute("b", b);
		request.setAttribute("at", at);
		request.setAttribute("cList", cList);
		
		request.getRequestDispatcher("/views/board/boardUpdateForm.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//게시글 수정처리
		//request.setCharacterEncoding("UTF-8");
		
		//전송 enctype이 multipart로 설정되었으니 해당 요청 판별부터 하기 
		if(ServletFileUpload.isMultipartContent(request)) {
			
			//게시글 정보 추출하려면 multipartRequest로 변환해야함
			
			//multipartRequest에 필요한 준비물
			//최대 용량 크기 지정
			int maxSize = 10 * 1024 * 1024;
			
			//파일 저장 경로 찾기 
			String savePath = request.getSession().getServletContext().getRealPath("/resources/uploadFiles/");
			
			
			//request객체 multiRequest로 변환
			MultipartRequest multiRequest = new MultipartRequest(request, savePath,maxSize,"UTF-8",new MyFileRenamePolicy());
			
			//게시글 정보 추출하기 
			int boardNo = Integer.parseInt(multiRequest.getParameter("boardNo"));
			String category = multiRequest.getParameter("category");
			String title = multiRequest.getParameter("title");
			String content= multiRequest.getParameter("content");
			
			//첨부파일 여부에 상관 없이 게시글 정보는 수정되어야함.
			
			Board b = new Board();
			b.setBoardNo(boardNo);
			b.setCategoryNo(category);
			b.setBoardTitle(title);
			b.setBoardContent(content);
			
			
			//새로운 첨부파일이 전달되었다면 해당 정보 DB에 수정작업 필요 또는 등록작업 필요
			Attachment at = null;
			
			//서버에 전달된 파일명 확인해서 판별하기 (전달된 업로드 파일명이 있다면)
			if(multiRequest.getOriginalFileName("reUploadFile") != null) {//1.새로운 첨부파일이 있는 경우
				
				at = new Attachment(); //새 파일 정보 담을 객체 준비
				//getOriginalFileName() : 사용자가 올린 파일명
				at.setOriginName(multiRequest.getOriginalFileName("reUploadFile"));
				//getFilesystemName() : 서버에 업로드된 파일명(저장된)
				at.setChangeName(multiRequest.getFilesystemName("reUploadFile"));
				at.setFilePath("/resources/uploadFiles/");
				
				
				//1-1)해당 게시글에 기존 첨부파일이 있었던 경우 == (기존 첨부파일 데이터에서 수정 작업 처리 (update)
				//기존 첨부파일이 있을땐 hidden으로 기존 첨부파일 번호와 이름을 전달했기 때문에 해당 데이터로 확인하기 
				if(multiRequest.getParameter("originFileNo") != null) {
					//기존 첨부파일이 있고 새로운 첨부파일도 있는 경우
					//식별자 용도로 fileNo 추출하여 at에 담아주고 수정처리하기 
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));
				}else {
					//1-2)기존 첨부파일이 없었고 새로운 첨부파일이 있는 경우 == 첨부파일 데이터 등록 처리 (insert)
					at.setRefBno(boardNo);//게시글에 새로운 첨부파일 정보 등록되어야하니 참조게시글 번호 추가 
				}
				
			}//2.새로운 첨부파일이 없는 경우
			
			//서비스에 요청 
			int result = new BoardService().updateBoard(b,at);
			
			//새로운 첨부파일이 없는 경우 : Board테이블 Update
			//새로운 첨부파일이 있고 기존 첨부파일 있는 경우 : Board 테이블 Update / Attachment 테이블 Update
			//새로운 첨부파일이 있고 기존 첨부파일 없는 경우 : Board 테이블 Update / Attachment 테이블 Insert
			
			HttpSession session = request.getSession();
			//결과에 따라 응답 뷰 지정 
			String alertMsg = "";
			
			if(result>0) {//성공
				alertMsg = "게시글 수정 성공!";
				//기존 첨부파일 있었으면 서버에서 파일 삭제처리하기
				if(at!=null && at.getFileNo()!=0) {//fileNo는 기존 첨부파일이 있을때만 담기때문에 조건 추가
					//기존 파일 삭제 (form에서 hidden으로 전달해놓은 원본파일명(서버업로드파일명) 을 이용해서 지워주기 
					new File(savePath+multiRequest.getParameter("originFileName")).delete();
				}
			}else {//실패 
				alertMsg = "게시글 수정 실패!";
			}
			//메시지 담아주기
			session.setAttribute("alertMsg", alertMsg);
			//상세 페이지로 보내기
			response.sendRedirect(request.getContextPath()+"/detail.bo?bno="+boardNo);
			
			
			
			
		}
		
		
		
		
		
		
		
	}

}
