package com.kh.board.controller;

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
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class PhotoInsertController
 */
@WebServlet("/insert.th")
public class PhotoInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/views/pictureBoard/photoEnrollForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("UTF-8");
		
		//해당 요청이 multipart 요청인지 확인 
		if(ServletFileUpload.isMultipartContent(request)) {
			//최대 용량
			int maxSize = 10*1024*1024;
			//저장 경로
			String savePath = request.getSession().getServletContext().getRealPath("/resources/uploadFiles/");
			
			//MultipartRequest 객체 생성 
			MultipartRequest multiRequest = new MultipartRequest(request, savePath,maxSize,"UTF-8",new MyFileRenamePolicy());
			
			
			//Board 정보 추출 
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");
			
			Board b = new Board();
			b.setBoardTitle(title);
			b.setBoardContent(content);
			b.setBoardWriter(boardWriter);
			
			//Attachment 정보 추출 
			//첨부파일의 경우 대표이미지에 required 속성을 작성했기 때문에 무조건 1개 이상 전달된다.
			//여러개의 파일정보가 전달된다면 반복문을 이용하여 처리하기 
			//Attachment 객체 여러개를 담아야하니 list 준비
			ArrayList<Attachment> atList = new ArrayList<>();
			
			//전달받은 키값은 file1,file2,file3,file4 형태이기 때문에 file + 증가숫자로 키값 접근 
			
			for(int i=1;i<=4; i++) {//i값 이용하기 4번반복 
				String key = "file"+i; 
				//해당 키값에 넘어온 데이터가 있는지 확인하기 
				if(multiRequest.getOriginalFileName(key) != null) {//원본파일명이 null이 아닐때 즉,해당 키값으로 전달된 파일이 있을때
					
					Attachment at = new Attachment();
					//원본파일명 추출
					at.setOriginName(multiRequest.getOriginalFileName(key));
					//변경된 파일명 추출(서버 업로드 파일명)
					at.setChangeName(multiRequest.getFilesystemName(key));
					//저장경로 추가
					at.setFilePath("/resources/uploadFiles/");
					
					//대표이미지인지 상세 이미지인지 선택 
					if(i==1) {//대표이미지
						at.setFileLevel(1);
					}else {//상세이미지
						at.setFileLevel(2);
					}
					//정보 추출해서 추가했으니 해당 객체 리스트에 담아주기
					atList.add(at);
				}
			}
			
			//서비스에 요청하기 (게시글 정보와 첨부파일 정보 같이 전달)
			int result = new BoardService().insertPhoto(b,atList);
			
			//처리 결과에 따른 응답뷰 지정
			
			HttpSession session = request.getSession();
			
			String alertMsg = "";
			
			if(result>0) {
				alertMsg = "사진 게시글 작성 완료!";
			}else {
				alertMsg = "사진 게시글 작성 실패!";
			}
			
			session.setAttribute("alertMsg", alertMsg);
			response.sendRedirect(request.getContextPath()+"/list.th");
			
			
		}
		
		
		
		
		
		
		
		
		
	}

}
