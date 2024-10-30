

SELECT COUNT(*) COUNT
FROM BOARD
WHERE STATUS = 'Y';

rollback;
delete from board;

--글 번호 카테고리 제목 작성자	조회수	작성일
SELECT ROWNUM,
       BOARD_NO,
       CATEGORY_NAME,
       BOARD_TITLE,
       USER_NAME,
       COUNT,
       CREATE_DATE
FROM BOARD B
JOIN CATEGORY USING(CATEGORY_NO)
JOIN MEMBER M ON (USER_NO=BOARD_WRITER)
WHERE B.STATUS ='Y'
AND BOARD_TYPE=1
ORDER BY CREATE_DATE DESC;

--먼저 정렬한 조회결과에 순번 매겨주기 
SELECT ROWNUM,A.*
FROM (SELECT   BOARD_NO,
               CATEGORY_NAME,
               BOARD_TITLE,
               USER_NAME,
               COUNT,
               CREATE_DATE
        FROM BOARD B
        JOIN CATEGORY USING(CATEGORY_NO)
        JOIN MEMBER M ON (USER_NO=BOARD_WRITER)
        WHERE B.STATUS ='Y'
        AND BOARD_TYPE=1
        ORDER BY CREATE_DATE DESC) A
WHERE ROWNUM BETWEEN 1 AND 20;
--ROWNUM은 1번부터 순번을 지정하기때문에 1번기준이 없으면 조회될 수 없다.

--서브쿼리 한번 더 사용하여 순번까지 정해진 조회결과를 이용하여 원하는 번호 추출하기
SELECT *
FROM (SELECT ROWNUM RNUM,A.*
      FROM (SELECT BOARD_NO,
                   CATEGORY_NAME,
                   BOARD_TITLE,
                   USER_NAME,
                   COUNT,
                   CREATE_DATE
            FROM BOARD B
            JOIN CATEGORY USING(CATEGORY_NO)
            JOIN MEMBER M ON (USER_NO=BOARD_WRITER)
            WHERE B.STATUS ='Y'
            AND BOARD_TYPE=1
            ORDER BY CREATE_DATE DESC) A)
WHERE RNUM BETWEEN 21 AND 30;


--게시글 상세조회
--조회할 데이터 : 번호 카테고리명 글제목 글내용 작성자명 작성일
SELECT BOARD_NO
      ,CATEGORY_NAME
      ,BOARD_TITLE
      ,BOARD_CONTENT
      ,USER_NAME
      ,CREATE_DATE
FROM BOARD B
JOIN CATEGORY USING(CATEGORY_NO)
JOIN MEMBER M ON (USER_NO=BOARD_WRITER)
WHERE BOARD_NO = 56
AND B.STATUS ='Y';


SELECT CATEGORY_NO
      ,CATEGORY_NAME
FROM CATEGORY;

SELECT * FROM BOARD;

INSERT INTO BOARD(BOARD_NO
                  ,BOARD_TYPE
                  ,CATEGORY_NO
                  ,BOARD_TITLE
                  ,BOARD_CONTENT
                  ,BOARD_WRITER
                ) VALUES (?,1,?,?,?,?);
                
SELECT * FROM ATTACHMENT;

INSERT INTO ATTACHMENT(FILE_NO
                      ,REF_BNO
                      ,ORIGIN_NAME
                      ,CHANGE_NAME
                      ,FILE_PATH
                      ) VALUES(SEQ_FNO.NEXTVAL
                              ,?
                              ,?
                              ,?
                              ,?
                              );
                              
SELECT * 
FROM ATTACHMENT;

SELECT FILE_NO
      ,ORIGIN_NAME
      ,CHANGE_NAME
      ,FILE_PATH
FROM ATTACHMENT
WHERE REF_BNO = 109
AND STATUS = 'Y';

                
select * from attachment;

UPDATE ATTACHMENT
SET ORIGIN_NAME = ?
   ,CHANGE_NAME = ?
   ,FILE_PATH = ?
   ,UPLOAD_DATE = SYSDATE
WHERE FILE_NO = ?;


--게시글 번호,게시글 제목,조회수, 썸네일용 대표이미지 정보

SELECT BOARD_NO
      ,BOARD_TITLE
      ,COUNT
      ,FILE_PATH||CHANGE_NAME TITLEIMG
FROM BOARD B
JOIN ATTACHMENT AT ON (BOARD_NO=REF_BNO)
WHERE B.STATUS = 'Y'
AND BOARD_TYPE=2
AND FILE_LEVEL=1
ORDER BY CREATE_DATE DESC;


SELECT * FROM ATTACHMENT;

SELECT FILE_NO
      ,ORIGIN_NAME
      ,CHANGE_NAME
      ,FILE_PATH
FROM ATTACHMENT
WHERE REF_BNO = 116;


SELECT BOARD_NO
		      ,CATEGORY_NAME
		      ,BOARD_TITLE
		      ,BOARD_CONTENT
		      ,USER_ID
		      ,CREATE_DATE
		FROM BOARD B
		JOIN CATEGORY USING(CATEGORY_NO)
		JOIN MEMBER M ON (USER_NO=BOARD_WRITER)
		WHERE BOARD_NO = 116
		AND B.STATUS ='Y';


select *from board where board_no = 116;


--아이디 중복확인 
SELECT USER_ID FROM MEMBER WHERE USER_ID = 'qwe';

                
                


