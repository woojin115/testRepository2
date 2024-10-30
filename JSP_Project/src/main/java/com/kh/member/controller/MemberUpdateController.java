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
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateController() {
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
		
		//post요청 - 인코딩설정
		//request.setCharacterEncoding("UTF-8");
		//정보수정시 전달받은 데이터 추출하기 
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] interest = request.getParameterValues("interest");
		
		String interestStr = "";
		if(interest != null) {//null이 아니면 문자열로 합치기 처리 
			interestStr = String.join(",", interest);
		}
		
		//추출한 데이터 객체에 담아서 service에 전달 및 요청처리 
		Member m = new Member(userId,userName,phone,email,address,interestStr);
		//서비스로 전달
		int result = new MemberService().updateMember(m);
		
		
		//메소드명 updateMember 
		//처리된 결과에 따라서 
		//성공시 정보수정 성공! 메시지를 세션에 담고
		//마이페이지로 재요청 
		if(result>0) { //성공시
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "정보수정 성공!");
			//정보수정이 성공됐으면 마이페이지를 다시 보여주기 전에 
			//세션에 담겨있는 기존 정보를 변경된 정보로 갱신 해야한다. 
			//로그인 유저 정보 다시 조회해오기 
			//기존에 로그인할때 사용했던 메소드 이용하여 로그인정보 가져오기 
			//세션에 있는 기존 정보중 비밀번호 추출하여 사용하기 
			String userPwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
			Member updateMem = new MemberService().loginMember(userId, userPwd );
			
			//조회해온 회원정보 세션에 담아주기 (기존 키값에 데이터 담으면 덮어쓰기(갱신)) 
			session.setAttribute("loginUser", updateMem);
			
			//response.sendRedirect(request.getContextPath()+"/views/member/myPage.jsp");
			//마이페이지 요청 매핑을 이용하여 마이페이지로 이동
			response.sendRedirect(request.getContextPath()+"/mypage.me");
			
		}else {//실패시 
			request.setAttribute("errorMsg", "정보 수정 실패!");
			//에러페이지로 위임하기
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
		
		
		//실패시 정보수정 실패! 메시지를 request에 담고 
		//에러 페이지로 위임(포워딩)
		
		
		
		
		
		
		
		
		
		
	}

}
