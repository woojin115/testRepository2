package com.kh.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeListController
 */
@WebServlet("/list.no")
public class NoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//공지사항 게시글 목록 조회해서 request에 담아서 위임하기
		//DB에서 공지사항 테이블에있는 글번호,글제목,작성자이름,조회수,작성일
		//공지사항 정보가 하나면 Notice 객체에 담을 수 있고 만약 여러개면 ? 각 정보를 하나의 객체에 담아주고 
		//그 객체들을 모아서 ArrayList에 담아오면 된다.
		
		//메소드명 : selectNoticeList() 
		//참고자료 : JDBC-Project를 참고하여 목록조회해오기
		//조회한 리스트 출력문으로 출력해보기
		ArrayList<Notice> list = new NoticeService().selectNoticeList();//전달값 없이 메소드 호출
		
		//가지고온 목록 확인 
		//System.out.println(list);
		//페이지를 위임할때 조회해온 목록을 담아주고 같이 위임하기
		request.setAttribute("list", list);
		
		
		//뷰페이지로 요청 위임하기
 		request.getRequestDispatcher("/views/notice/noticeListView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
