package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;

/**
 * Servlet implementation class MemberIdCheckController
 */
@WebServlet("/idCheck.me")
public class MemberIdCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberIdCheckController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String inputId = request.getParameter("inputId");
		
		//서비스에 전달 및 요청하기 
		
		//조회된 행이 있으면 true 없으면 false
		boolean flag = new MemberService().idCheck(inputId);
		
		String responseData = "";
		
		if(flag) { //true니까 중복일때 (사용불가)
			responseData = "NNNNN";
		}else { //false일때 중복아님 (사용가능)
			responseData = "NNNNY";
		}
		
		response.getWriter().print(responseData);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
