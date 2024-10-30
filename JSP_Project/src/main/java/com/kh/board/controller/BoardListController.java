package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//페이징 처리 (사용자에게 보여줄 게시글 정보들)
		//준비물
		int listCount; //총 게시글 개수 
		int currentPage; //현재 페이지
		int pageLimit; //페이지 하단에 보여질 페이징바 최대 개수 
		int boardLimit; //한 페이지에 보여질 게시글 개수 
		
		int maxPage; //가장 마지막 페이지는 몇번페이지인지 (총 페이지 개수)
		int startPage; // 페이지 하단에 보여질 페이징바 시작 수 
		int endPage; //페이지 하단에 보여질 페이징바 끝 수
		
		//총 게시글 개수 - DB에서 게시글 개수 세어오기
		listCount = new BoardService().listCount(); //10분
		
		//현재 페이지 정보 
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		//페이지 하단에 보여질 페이징바 개수 
		pageLimit = 10; 
		boardLimit = 10;
		
		/*
		 * maxPage : 가장 마지막 페이지가 몇번인지 (총 페이지수)
		 * 
		 * listCount 와 boardLimit의 영향을 받는 수 
		 * 
		 * 게시글 개수 107개일때 한 페이지에서 10개씩 보여준다면 페이지는 10페이지까지 가득 채워지고
		 * 11페이지에서는 나머지 7개의 데이터만 보여지게 된다.
		 * 
		 * 총개수/한페이지 개수 처리 후 나머지 소수점은 올림처리
		 * 
		 * listCount와 boardLimit 모두 int 자료형이기 때문에 나누기 계산시 나오는 소수점은 절삭된다 (정수형 자료형이기 때문)
		 * 때문에 소수점 표현이 필요하다면 둘중 하나를 double자료형으로 형변환해서 처리해야한다.
		 * */
		
		maxPage = (int)Math.ceil((double)listCount/boardLimit);
		
		/*
		 * startPage : 페이징바의 시작수 
		 * pageLimit이 10인 경우
		 * currentPage가 1~10 일땐 1 
		 * currentPage가 11~20 일땐 11
		 * currentPage가 21~30 일땐 21
		 * 0~9 / 10 == 0 *10 ==0 + 1 == 1 
		 * 10~19 /10 == 1 + *10 ==10 +1  == 11
		 * 20~29 /10 == 2 + *10 == 20 +1 == 21 
		 * 
		 * 	
		 * (currentPage - 1)/pageLimit * pageLimit +1;
		 * 
		 * */
		
		startPage = (currentPage-1)/pageLimit * pageLimit + 1;
		
		//endPage : 페이징바의 끝 수 
		endPage = startPage+pageLimit-1; //시작페이지로부터 몇개를 보여줄지에 따라 처리됨
		
		/*
		 * 107개 데이터를 각페이지에서 10개씩 출력하니 11개 페이지가 나옴 
		 * 마지막 페이지수는 11번 페이지인데
		 * 페이징 시작수에따라 끝수를 정해버렸기 때문에 11페이지가 시작이면 끝페이지가 20이 될수밖에 없음
		 * 만약 최대페이지 수 가 끝페이지 수 보다 작다면 끝페이지 수 를 최대페이지 수 로 변경해야한다.
		 * endPage값을 maxPage값으로 변경하기
		 * 
		 * */
		if(maxPage<endPage) {
			endPage = maxPage; //maxPage에 담긴 값을 endPage에 대입하기
		}
		
		//페이징바에 필요한 데이터 7개를 담아줄 수 있는 VO 객체 정의하기
		PageInfo pi = new PageInfo(listCount,currentPage,pageLimit,boardLimit,maxPage,startPage,endPage);
		
		//게시글 목록 조회
		ArrayList<Board> list = new BoardService().selectList(pi);
		
		
		//조회된 게시글 목록과 페이징바 정보객체를 위임시 전달하기
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		
		
		request.getRequestDispatcher("/views/board/boardListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
