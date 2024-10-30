package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Reply;

/**
 * Servlet implementation class ReplyInsertController
 */
@WebServlet("/insertReply.bo")
public class ReplyInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//request.setCharacterEncoding("UTF-8");
		
		String replyWriter = request.getParameter("replyWriter");
		String replyContent = request.getParameter("replyContent");
		int refBno = Integer.parseInt(request.getParameter("refBno"));
		
		Reply r = new Reply();
		r.setReplyWriter(replyWriter);
		r.setReplyContent(replyContent);
		r.setRefBno(refBno);
		
		//DML
		int result = new BoardService().insertReply(r);
		
		
		//응답 데이터 반환 (응답 화면 구성은 view에서 진행하기때문에 결과값만 반환한다)
		response.getWriter().print(result);
		
		
		
		
		
		
	}

}
