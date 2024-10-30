package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeDeleteController
 */
@WebServlet("/delete.no")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nno = Integer.parseInt(request.getParameter("nno"));
		
		//추출한 글번호로 해당 글 status  'N' 으로 수정하기 
		
		int result = new NoticeService().deleteNotice(nno);
		
		//성공 실패시 알림메시지와 함께 목록으로 보내기 
		String alertMsg = "";
		if(result>0) {
			alertMsg = "글 삭제 성공!";
		}else {
			alertMsg = "글 삭제 실패!";
		}
		
		request.getSession().setAttribute("alertMsg", alertMsg);
		
		response.sendRedirect(request.getContextPath()+"/list.no");
	}

}
