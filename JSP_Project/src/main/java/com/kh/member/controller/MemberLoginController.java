package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberLoginController
 */
@WebServlet("/login.me")
public class MemberLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLoginController() {
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
		// TODO Auto-generated method stub
		
		//사용자가 로그인 시 입력한 아이디 비밀번호 
		//sysout으로 출력해보기 
		
		//POST방식의 경우 전달값에 한글이 포함되어 있다면 인코딩 처리 할것 
		request.setCharacterEncoding("UTF-8");
		
		//전달받은 데이터는 request의 parameter영역에 담겨 온다.
		//사용자가 전달한 데이터는 input요소의 name값을 key로 사용하고 
		//전달된 값을 value로 갖게 된다.
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		//사용자가 입력한 데이터를 service-dao-db에 순차적으로 전달하여 
		//해당 요청에 대한 처리를 하게 된다. 
		//해당 요청을 처리해줄 service의 메소드를 호출(데이터 전달)
		Member loginUser = new MemberService().loginMember(userId,userPwd);
		
		//사용자의 회원정보를 담은 응답페이지 구성하기 
		/*
		 * 응답페이지에 전달할 값이 있을 경우 값을 담을 수 있는 공간(Servlet Scope 내장 객체 4종류)
		 * 1)application : application에 담은 데이터는 웹 어플리케이션 전역에서 다 접근 가능 
		 * 				   한번 담은 데이터는 직접 지우기 전까지, 서버가 멈추기 전까지 유지된다.
		 * 
		 * 2)session : session에 담은 데이터는 모든 jsp와 servlet에서 접근할 수 있다.
		 * 			   한번 담은 데이터는 직접 지우기 전까지,서버가 멈추기 전까지,브라우저가 종료되기 전까지
		 * 			   접근 가능.
		 * 
		 * 3)request : request에 담은 데이터는 해당 request를 포워딩한 응답 jsp에서만 접근가능 
		 * 4)page : 해당 jsp 페이지 내에서만 접근 가능 
		 * 
		 * 주로 session과 request영역을 활용하여 처리
		 * *데이터를 담고자 한다면 사용하는 공통 메소드
		 * -내장객체.setAttribute("키","값");
		 * *데이터를 추출하고자 한다면 사용하는 공통 메소드
		 * -내장객체.getAttribute("키");
		 * *데이터를 지우고자 한다면 사용하는 공통 메소드
		 * -내장객체.removeAttribute("키");  
		 * */
		
		//session 객체 얻어오기 (로그인 정보 담아둘 내장객체)
		//session에 담아두는 이유 : 다른 페이지를 가도 로그인이 유지될 수 있도록
		HttpSession session = request.getSession(); //세션객체
		
		//로그인 성공시 
		if(loginUser != null) {//회원정보가 있다는 의미(로그인 성공)
			//세션에 로그인 유저 정보 담아놓고 
			session.setAttribute("loginUser",loginUser);
			//성공 메세지도 하나 담아주기 
			session.setAttribute("alertMsg", "로그인 성공!");
			//메인페이지로 이동시키기 
			
		}else {//회원정보가 null이면 (없다는 의미 - 로그인 실패)
			//로그인 실패시 
			//실패 메세지와 함께 메인 페이지로 이동시키기
			session.setAttribute("alertMsg", "로그인 실패!");
		}
		
		//메인페이지로 보내버리기(재요청)
		//기존 요청은 끝내고 새로운 페이지를 요청하는 방식
		//sendRedirect방식 
		//응답에 관련된 객체 : response 를 이용한 방식 
		//response.sendRedirect("경로");
		//메인페이지 : /jsp (컨텍스트 루트 경로를 입력하면 index페이지로 간다)
		//request.getContextPath() : 컨텍스르 루트를 반환하는 메소드
		//System.out.println("확인 : "+request.getContextPath());
		//response.sendRedirect(request.getContextPath());
		
		//사용자가 이전에 있었던 페이지로 돌려주는 작업 
		//request.getHeader("referer") : 이전 페이지 경로 
		String url = request.getHeader("referer");
		
		//System.out.println(url);
		
		response.sendRedirect(url);//이전 경로로 재요청
		
		
		
		
		
	}

}
