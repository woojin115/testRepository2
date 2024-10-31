package com.kh.git.notice;

public class Notice {
	private String no;
	private String gender;
	private int age;
	public Notice() {
		super();
	}
	public Notice(String no, String gender, int age) {
		super();
		this.no = no;
		this.gender = gender;
		this.age = age;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
