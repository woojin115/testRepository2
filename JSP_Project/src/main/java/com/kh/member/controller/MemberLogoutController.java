package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberLogoutController
 */
@WebServlet("/logout.me")
public class MemberLogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//세션 얻어오기
		HttpSession session = request.getSession();
		
		//로그아웃처리는 세션을 만료시키거나(세션무효)
		//session.invalidate(); //세션 만료시키기 (세션초기화)
		
		//로그인 정보 삭제 
		session.removeAttribute("loginUser"); //로그인 정보만 삭제
		
		//로그아웃 처리됐으니 메인페이지로 보내기 
		//메소드로 컨텍스트 루트 반환받아서 처리하기
		String url = request.getHeader("referer");
		
		response.sendRedirect(url);//이전 경로로 재요청
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
