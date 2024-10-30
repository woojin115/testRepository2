package com.kh.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.vo.Member;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter({"/insert.bo","/mypage.me","/update.bo","/insert.no"})
//@WebFilter("*.bo")// .bo 에 대한 모든 요청에 필터 적용
public class LoginFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		//로그인 되어있는 회원인지 확인 후 흐름 유지 또는 되돌리기 
		//Filter에 있는 request는 아직 서블릿을 가기 전 request로 더 상위 타입이다.
		//다운캐스팅을 이용하여 HttpServletRequest 객체로 변경해서 처리해야한다.
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		
		HttpSession session = servletRequest.getSession(); //HttpServletRequest를 이용해서 세션 받기
		
		//세션에 담긴 loginUser 정보 확인 
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser != null) { //로그인 되어있다면 
			chain.doFilter(request, response);//기존 요청 흐름 유지
		}else {
			//흐름 막고 메인으로 되돌리기 
			session.setAttribute("alertMsg", "부적절한 접근입니다.");
			//응답객체인 response도 다운캐스팅하여 처리하기 
			((HttpServletResponse)response).sendRedirect(servletRequest.getContextPath());
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
