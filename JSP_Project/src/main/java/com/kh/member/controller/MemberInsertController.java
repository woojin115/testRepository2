package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
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
		
		//포스트 요청했으니 인코딩 처리 필수 
		//request.setCharacterEncoding("UTF-8");
		
		//요청시 전달한 전달값 추출하기 
		String userId = request.getParameter("userId");//필수입력
		String userPwd = request.getParameter("userPwd");//필수입력
		String userName = request.getParameter("userName");//필수입력
		String phone = request.getParameter("phone");//작성안하면 빈문자열
		String email = request.getParameter("email");//작성안하면 빈문자열
		String address = request.getParameter("address");//작성안하면 빈문자열
		String[] interest = request.getParameterValues("interest");//체크안하면 null 
		//취미 같은 경우 문자열 배열로 되어있기 때문에 데이터베이스에 그대로 등록할 수 없다.
		//문자열로 만들어서 등록하기 
		//(이때 체크 안한 상태면 null이 나오기 때문에 조건처리로 null이 아닌 경우만 문자열 만들기)
		
		String interestStr = "";
		
		if(interest!=null) { //null이 아닌 경우 에만 문자열로 만들어주기 
			interestStr = String.join(",", interest);
		}
		
		//추출한 데이터들을 service-dao-db 에 전달하기 위해서 회원정보객체에 담아주기
		Member m = new Member(userId,userPwd,userName,phone,email,address,interestStr);
		
		//담아준 데이터 service에 전달하며 요청하기 
		int result = new MemberService().insertMember(m);
		
		//처리된 결과값을 이용하여 사용자에게 보여줄 화면 지정하기
		HttpSession session = request.getSession(); //세션 받아오기
		
		if(result>0) {//성공시 화면
			//회원가입 환영 메시지와 함께 메인페이지로 보내주기 
			session.setAttribute("alertMsg", "회원이 되신것을 환영합니다.");
			
			//메인페이지로 재요청(redirect)
			response.sendRedirect(request.getContextPath());
			
		}else {//실패시 화면
			//회원가입을 하지못했으니 에러페이지로 요청 위임하며 전달하기
			
			request.setAttribute("errorMsg", "회원가입에 실패하셨습니다.");
			
			RequestDispatcher view = request.getRequestDispatcher("/views/common/errorPage.jsp");
			//포워딩 처리 
			view.forward(request, response);
		}
		
	}

}
