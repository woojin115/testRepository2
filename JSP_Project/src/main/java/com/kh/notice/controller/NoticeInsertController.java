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
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//작성페이지로 이동(요청위임)
		request.getRequestDispatcher("views/notice/noticeEnrollForm.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//같은 매핑주소로 요청을 하려면 get 또는 post 로 요청 방식을 나누어 요청하면 된다.
		//전달받은 데이터 3개 추출
		//작성자 번호,제목,내용
		//post요청이니 인코딩부터 하기 
		request.setCharacterEncoding("UTF-8");
		
		String userNo = request.getParameter("userNo");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		Notice n = new Notice();
		n.setNoticeTitle(title);
		n.setNoticeContent(content);
		n.setNoticeWriter(userNo); //작성자 정보는 noticeWriter에 들어간다
		
		//전달받은 데이터 추출하여 객체에 담아 서비스 - dao - db 전달 후 처리하여 결과값 받아오기 
		int result = new NoticeService().insertNotice(n);
		
		if(result>0) {//성공시
			//등록 성공시 session 알림메세지(공지글 작성 성공!) 담고
			//공지사항 목록페이지로 재요청
			
			request.getSession().setAttribute("alertMsg", "공지글 작성 성공!");
			response.sendRedirect(request.getContextPath()+"/list.no");
		}else {
			//등록 실패시 request에 공지글 작성 실패 메시지 담고 
			//에러페이지로 포워딩(위임)
			request.setAttribute("errorMsg", "공지글 작성 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}
		//메소드명 : insertNotice()
		
		
		
	}

}
