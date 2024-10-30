package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

//파일명 변환 작업을 수행하기 위해 FileRenamePolicy 구현하기 
public class MyFileRenamePolicy implements FileRenamePolicy{
	
	
	//반드시 미완성인 rename 메소드를 오버라이딩 하여 구현해야함
	//기존 파일을 전달받아 파일명 수정 작업후 수정된 파일을 반환해주는 메소드 
	@Override
	public File rename(File originFile) {
		
		//원본 파일명 추출
		String originName = originFile.getName();
		
		//수정파일명 : 파일 업로드 된 시간(년월일시분초) + 5자리 랜덤값(10000~99999) 
		//파일 확장자 : 원본파일 확장자 그대로
		
		//1.파일 업로드된 시간 
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//2. 5자리 랜덤값 추출
		int ranNum = (int)(Math.random()*90000) +10000;
		
		//3. 원본파일 확장자 
		//원본파일명 : 강.아.지.사.진.jpg 
		//마지막 . 기준으로 잘라내면 확장자 추출가능 
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		//originFile.getParent() : 해당 파일의 부모 폴더 경로 반환 (해당 파일이 속한 경로)
		//부모폴더경로로 변경된 이름의 파일객체 생성됨
		return new File(originFile.getParent(),changeName);
	}

}
