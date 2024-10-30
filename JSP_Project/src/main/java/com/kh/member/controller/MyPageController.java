package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/mypage.me")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//마이페이지로 이동 (포워딩)
		
//		RequestDispatcher view = request.getRequestDispatcher("/views/member/myPage.jsp");
//		view.forward(request, response);
		
		//url로 직접 매핑주소를 입력하여 요청도 가능하기 때문에 
		//로그인이 되어있지 않아도 요청이 된다. 이때 로그인정보에서 데이터를 추출하기때문에 
		//오류가 발생할수밖에 없다. 
		//때문에 해당 요청시에 로그인이 되어있는지 확인 작업을 거쳐야함
		//로그인 전 요청시 - 메인페이지로 보내버리기
		//로그인 후 요청시 - 마이페이지로 보내기 
		//세션에있는 로그인 정보가 있는지 없는지 판별하기 
		HttpSession session = request.getSession();
		//세션 attibute에 loginUser 정보가 있는지 확인
		//만약 해당 키값 정보가 없으면 null을 반환한다.
		
		if(session.getAttribute("loginUser")== null) {//null이면 로그인 안되어있는것이니 메인페이지로 보내기
			
			session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스입니다.");
			response.sendRedirect(request.getContextPath());
		
		}else {//null이 아니라면 ? 로그인 되어있는 상태기 때문에 기존 요청 처리하기
			//한줄처리 
			request.getRequestDispatcher("/views/member/myPage.jsp").forward(request, response);
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
