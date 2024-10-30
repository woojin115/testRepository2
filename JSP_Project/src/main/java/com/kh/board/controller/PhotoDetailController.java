package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class PhotoDetailController
 */
@WebServlet("/detail.th")
public class PhotoDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int boardNo = Integer.parseInt(request.getParameter("bno"));
		
		BoardService service = new BoardService();
		
		//일반게시판에서 조회수 증가 작성한 메소드 이용하기 
		int result = service.increaseCount(boardNo);
		
		//조회수 증가 성공시 - 게시글 정보,첨부파일 정보 조회 
		
		if(result>0) {
			
			//게시글정보 조회 
			Board b = service.selectBoard(boardNo);
			
			
			//첨부파일 정보 조회
			ArrayList<Attachment> atList = service.selectAttachmentList(boardNo);
			
			request.setAttribute("b",b);
			request.setAttribute("atList", atList);
			
			
			request.getRequestDispatcher("/views/pictureBoard/photoDetailView.jsp").forward(request, response);
			
		}else { //실패 
			
			request.setAttribute("errorMsg", "사진 게시글 조회 실패");
			
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
			
			
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
