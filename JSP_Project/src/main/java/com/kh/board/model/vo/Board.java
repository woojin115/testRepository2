package com.kh.board.model.vo;

import java.sql.Date;

public class Board {
	private int boardNo; // BOARD_NO NUMBER
	private int boardType;// BOARD_TYPE NUMBER
	private String categoryNo;// CATEGORY_NO NUMBER (String 카테고리 이름 담기 위해)
	private String boardTitle;// BOARD_TITLE VARCHAR2(100 BYTE)
	private String boardContent;// BOARD_CONTENT VARCHAR2(4000 BYTE)
	private String boardWriter;// BOARD_WRITER NUMBER (String 작성자이름 담기위해)
	private int count;// COUNT NUMBER
	private Date createDate;// CREATE_DATE DATE
	private String status;// STATUS VARCHAR2(1 BYTE)
	
	//썸네일에 필요한 첨부파일 정보 담을 필드 추가 
	private String titleImg;

	public Board() {
		super();
	}

	public Board(int boardNo, String boardTitle, int count, String titleImg) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.count = count;
		this.titleImg = titleImg;
	}

	public Board(int boardNo, String categoryNo, String boardTitle, String boardWriter, int count, Date createDate) {
		super();
		this.boardNo = boardNo;
		this.categoryNo = categoryNo;
		this.boardTitle = boardTitle;
		this.boardWriter = boardWriter;
		this.count = count;
		this.createDate = createDate;
	}
	

	public Board(int boardNo, String categoryNo, String boardTitle, String boardContent, String boardWriter,
			Date createDate) {
		super();
		this.boardNo = boardNo;
		this.categoryNo = categoryNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.createDate = createDate;
	}


	public Board(int boardNo, int boardType, String categoryNo, String boardTitle, String boardContent,
			String boardWriter, int count, Date createDate, String status) {
		super();
		this.boardNo = boardNo;
		this.boardType = boardType;
		this.categoryNo = categoryNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.count = count;
		this.createDate = createDate;
		this.status = status;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getBoardType() {
		return boardType;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public String getTitleImg() {
		return titleImg;
	}


	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}


	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boardType=" + boardType + ", categoryNo=" + categoryNo + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardWriter=" + boardWriter + ", count=" + count
				+ ", createDate=" + createDate + ", status=" + status + "]";
	}

}
