package com.kh.member.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class UpdatePwdController
 */
@WebServlet("/updatePwd.me")
public class UpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePwdController() {
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
	
		//post요청 인코딩처리
		request.setCharacterEncoding("UTF-8");
		
		//session에 있는 로그인 정보중 아이디 추출하기
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		String id = loginUser.getUserId();//로그인한 회원 아이디 추출
	
		//hidden 으로 전송한 데이터 추출 되는지 확인
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String updatePwd = request.getParameter("updatePwd");
		
	
		//데이터 전송시 3개 따로 보내기 또는 저장소에 담아 보내기 
		HashMap<String,String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("userPwd", userPwd);
		map.put("updatePwd", updatePwd);
		
		//담아준 데이터를 서비스에 전달하기 
		//new MemberService().updatePwd(userId,userPwd,updatePwd);
		int result = new MemberService().updatePwd(map);
		
		//결과값에 따라서 사용자에게 보여줄 응답화면 처리 
		
//		성공시 비밀번호 변경 완료 메세지를 세션에 담고 마이페이지 재요청
		if(result>0) {
			session.setAttribute("alertMsg", "비밀번호가 변경되었으니 다시 로그인해주세요.");
			
			//세션에 있는 로그인 정보 삭제하여 다시 로그인 시키기
			session.removeAttribute("loginUser");//기존 로그인 정보 삭제
			
			//인덱스페이지로 보내기(재요청)
			response.sendRedirect(request.getContextPath());
		}else {
			//		실패시 비밀번호 변경 실패 메세지를 request에 담고 에러 페이지로 위임요청
			request.setAttribute("errorMsg", "비밀번호 변경 실패 !");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
		
	}

}
