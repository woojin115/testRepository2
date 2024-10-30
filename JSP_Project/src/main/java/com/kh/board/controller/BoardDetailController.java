package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class BoardDetailController
 */
@WebServlet("/detail.bo")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//글번호 추출
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		//서비스객체 3번 호출되니 한번 생성하고 사용하기 
		BoardService service = new BoardService();
		
		//해당 글번호로 조회수 증가 처리 
		int result = service.increaseCount(bno);
		
		//조회수 처리 결과에 따라서 게시글 정보 조회 결정하기 
		if(result>0) {//성공 (게시글 정보 조회)
			
			Board b = service.selectBoard(bno);
			//첨부파일 정보 조회해오기 
			Attachment at = service.selectAttachment(bno); //참조게시글번호 전달
			
			//게시글 정보 담아서 위임하기 
			request.setAttribute("b", b);
			//첨부파일정보 담아주기
			request.setAttribute("at", at); 
			
			
			request.getRequestDispatcher("/views/board/boardDetailView.jsp").forward(request, response);
			
			
		}else {//실패
			//조회실패시 alert 메시지 담고 목록으로 되돌리기
			request.getSession().setAttribute("alertMsg", "조회실패");
			response.sendRedirect(request.getContextPath()+"/list.bo?currentPage=1");
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
