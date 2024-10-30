package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateController
 */
@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int nno = Integer.parseInt(request.getParameter("nno"));
		
		//글번호 이용하여 해당 글 정보 조회해와서 updateForm으로 전달 및 위임하기 
		
		//상세보기시 만들어놨던 메소드 이용하여 해당 글 정보 조회해오기 
		Notice n = new NoticeService().selectNotice(nno);
		//request에 담아주기
		request.setAttribute("notice", n);
		
		request.getRequestDispatcher("views/notice/noticeUpdateForm.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//post방식이니 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		//수정작업하기 (글제목,글내용,글번호) 전달받아 처리하기 
		int nno = Integer.parseInt(request.getParameter("nno"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		//전달받은 데이터 담아서 서비스에 요청하기
		Notice n = new Notice();
		n.setNoticeNo(nno);
		n.setNoticeTitle(title);
		n.setNoticeContent(content);
		
		int result = new NoticeService().updateNotice(n);
		
		
		//해당 글번호의 데이터를 전달받은 글 제목 글내용으로 변경하기 
		//성공시 수정성공 메시지와 함께 해당글 상세보기 페이지로 이동 (재요청방식)
		//실패시 수정실패 메시지와 함께 해당글 상세보기 페이지로 이동 (재요청방식)
		String alertMsg = "";
		if(result>0) {
			alertMsg = "수정성공";
		}else {
			alertMsg = "수정실패";
		}
		
		//메소드명 updateNotice()
		//두 작업 모두 메시지는 session에 담아주기 
		request.getSession().setAttribute("alertMsg", alertMsg);
		response.sendRedirect(request.getContextPath()+"/detail.no?nno="+nno);
		
		
	}

}
