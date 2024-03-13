


DROP TABLE IF EXISTS member;
CREATE TABLE member (
	idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,  -- 회원 번호
	member_id VARCHAR(30) NOT NULL,						-- 회원 아이디
	member_pw VARCHAR(200) NOT NULL,						-- 회원 비밀번호
	member_name VARCHAR(20) NOT NULL,					-- 회원 닉네임(게시판에서 사용할)
	member_mail VARCHAR(50) NOT NULL,	        		-- 회원 이메일(계정 인증용)
	member_address VARCHAR(100),							-- 회원 주소(선택사항 - 쇼핑몰에선 필수)
	member_post VARCHAR(100),								-- 회원 우편번호(선택사항 - 쇼핑몰에선 필수)
	member_phone VARCHAR(20),								-- 회원 전화번호(선택사항 - 쇼핑몰에선 필수)
	create_date TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),		-- 회원 가입일
	update_date TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),		-- 회원 수정일
	member_lock VARCHAR(20) DEFAULT 'ACTIVE',	-- active, locked, kicked		-- 회원 탈퇴여부 ***** 정지 추가
	member_role VARCHAR(20) DEFAULT 'USER',	 -- ROLE_USER,  ROLE_ADMIN, ROLE_MASTER				-- 관리자 권한
	failed_attempt INT DEFAULT 0,
	lock_time TIMESTAMP
);

INSERT INTO member VALUES(NULL, 'qwe123', '123456', '이천수', 'qwe123@naver.com', '서울시 마포구 대흥동 123-10', '23154', '010-1234-1234', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, NULL);
INSERT INTO member VALUES(NULL, 'qwe345', '123456', '이을용', 'abc1234@naver.com', '서울시 서대문구 북아현동 13-1', '23214', '010-1234-5678', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, NULL);
INSERT INTO member VALUES(NULL, 'qwe676', '123456', '김남일', 'fdsfa2121@naver.com','서울시 마포구 대흥동 20-3', '65154', '010-4141-2325', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, NULL);
INSERT INTO member VALUES(NULL, 'qwe3534', '123456', '안정환', 'gfd5565@naver.com','서울시 마포구 아현동 102-10', '13154', '010-8705-3123', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, NULL);
INSERT INTO member VALUES(NULL, 'qwe12312', '123456', '조원희', 'xvhx262@naver.com','서울시 마포구 대흥동 16-10', '22154', '010-3456-2342', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, NULL);
INSERT INTO member VALUES(NULL, 'admin1234', 'RHKSflwk!234', '운영자', 'khm123456@naver.com', '서울시 금천구 가산동 151-13', '10107', '010-6666-6666', DEFAULT, DEFAULT, DEFAULT, 'ADMIN', DEFAULT, NULL);



DROP TABLE IF EXISTS item;
CREATE TABLE item (
	idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 상품 번호
	item_category1 VARCHAR(20) NOT NULL,      -- 상품 카테고리1  ** 추가
	item_category2 VARCHAR(20) NOT NULL,      -- 상품 카테고리2  ** 추가
	item_name VARCHAR(20) NOT NULL,							-- 상품 이름
	item_price INT NOT NULL,								-- 상품 가격
	item_discount INT DEFAULT 0,					-- 상품 할인율
	item_amount INT NOT NULL,								-- 상품 재고량
	item_image TEXT,								-- 상품 사진 (AWS S3에서 가져오는거로 수정예정)
	item_detail TEXT NOT NULL,								-- 상품 상세
	create_date TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),								-- 상품 등록일
	update_date TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),								-- 상품 수정일
	item_SELL INT DEFAULT 0						-- 상품 판매량
);

INSERT INTO item VALUES (NULL, '의류', '양말', '수면양말', 3000, 0, 0, NULL, '따끈따끈한 수면양말', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO item VALUES (NULL, '가전', '냉장고', 'LG냉장고', 1000000, 10, 1, NULL, 'LG 디럭스 냉장고', DEFAULT, DEFAULT, 10);
INSERT INTO item VALUES (NULL, '식품', '면류', '신라면', 700, 0 , 1000, NULL, '신라면 1봉', DEFAULT, DEFAULT, 0);


DROP TABLE IF EXISTS board;
CREATE TABLE board (
	idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 게시글 번호
	board_category1 VARCHAR(20) NOT NULL, -- 커뮤니티, 갤러리    	-- 게시글 카테고리1  ** 추가
	board_category2 VARCHAR(20) NOT NULL, -- 하위 카테고리들     -- 상품 카테고리2  ** 추가
	board_title VARCHAR(100) NOT NULL,							-- 게시글 이름
	board_subject VARCHAR(20) DEFAULT 'NORMAL',					-- 말머리 NORMAL, NOTICE, PICTURE
	board_writer VARCHAR(50) NOT NULL,							-- 게시글 작성자
	view_count INT DEFAULT 0,
	like_count INT DEFAULT 0,
	board_image TEXT,										-- 게시글 사진 (AWS S3에서 가져오는거로 수정예정)
	board_content TEXT,								-- 게시글 내용
	create_date TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),								-- 상품 등록일
	update_date TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP)								-- 상품 수정일
);

INSERT INTO board VALUES (NULL, '커뮤니티', '유머', '노잼개그', 'NOTICE', '김말이', DEFAULT, DEFAULT,NULL, '어쩌고 저쩌고', DEFAULT, DEFAULT);
INSERT INTO board VALUES (NULL, '커뮤니티', '자유', '취업 언제하냐...', 'NORMAL', '취준생', DEFAULT, DEFAULT, NULL, '', DEFAULT, DEFAULT);
INSERT INTO board VALUES (NULL, '커뮤니티', 'QnA', '게임추천좀요', 'NORMAL', '게이머', DEFAULT, DEFAULT, NULL, 'PALWORLD 재밌나여?', DEFAULT, DEFAULT);
INSERT INTO board VALUES (NULL, '갤러리', '짤방', '페페짤', 'PICTURE', '퍼오는 사람', DEFAULT, DEFAULT,  NULL, '어쩌고 저쩌고', DEFAULT, DEFAULT);
INSERT INTO board VALUES (NULL, '갤러리', '그림', '낙서', 'PICTURE', '그림쟁이', DEFAULT, DEFAULT, NULL, '', DEFAULT, DEFAULT);


DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
	idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,			-- 주문 번호
	member_idx BIGINT,												-- 회원 번호(FK)
	nonmember_idx BIGINT,											-- 비회원 번호(FK)
	orders_TIMESTAMP TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),											-- 주문 날짜
	senders_name VARCHAR(20) NOT NULL,							-- 보낸사람
	senders_phone VARCHAR(20) NOT NULL,							-- 보낸사람 전화번호
	orders_name VARCHAR(20) NOT NULL,							-- 수령자
	orders_phone VARCHAR(20) NOT NULL,							-- 수령자 전화번호
	orders_address VARCHAR(100) NOT NULL,						-- 수령자 주소
	orders_post VARCHAR(10) NOT NULL,							-- 수령자 우편번호
	orders_amount INT NOT NULL,									-- 주문 총 금액
	orders_payment VARCHAR(6) NOT NULL,  -- 0.무통장 1.카드 2.전화결제		-- 결제방식(예시입니다. 변동가능)
   orders_status VARCHAR(4) NOT NULL DEFAULT '배송준비',	  -- 0.입금대기 1.배송준비 2.배송중 3.배송완료	-- 주문 상태
	orders_deliverypay int NOT NULL,  -- 0.무료 1.일반 2.특수			-- 배송비
   orders_delivery_ps VARCHAR(50) DEFAULT '없음' -- 배달 요청사항
);
SELECT * FROM orders;

INSERT INTO orders VALUES (NULL, NULL, 1, DEFAULT, "박주영","010-0000-0000", "이천수", '010-1234-1234', '서울시 마포구 대흥동 123-10', '23154', 40000, '전화결제', '배송대기', 3000, default);
SELECT * FROM orders;

DROP TABLE IF EXISTS orders_detail;
CREATE TABLE orders_detail (
	idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 주문상세 번호
	orders_idx BIGINT NOT NULL,									-- 주문 번호(FK)
	item_idx BIGINT NOT NULL,										-- 상품 번호(FK)
	odetail_qty INT NOT NULL,									-- 상품별 구매수량
   odetail_status VARCHAR(2) NOT NULL DEFAULT '기본'                 -- 물품 상태. 기본, 교환, 환불
);

INSERT INTO orders_detail VALUES (NULL, '1', '1', '2', default);
INSERT INTO orders_detail VALUES (NULL, '1', '2', '1', '교환');
SELECT * FROM orders_detail;

DROP TABLE IF EXISTS  review;
CREATE TABLE review (
	idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,			-- 리뷰 번호
	orders_idx BIGINT NOT NULL,									-- 주문 번호(FK)
	item_idx BIGINT NOT NULL,										-- 상품 번호(FK)
	review_score INT DEFAULT 0,								-- 리뷰 별점
	review_writer VARCHAR(30),									-- 리뷰 작성자
	review_title VARCHAR(50) NOT NULL,							-- 리뷰 제목
	review_content TEXT NOT NULL,								-- 리뷰 내용
	review_image TEXT DEFAULT '이미지 없음',					-- 리뷰 사진
	create_date TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),								-- 리뷰 등록날짜
	update_date TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),								-- 리뷰 수정날짜
	review_status CHAR(3) DEFAULT '공개'                                -- 리뷰 상태 공개, 비공개
);

INSERT INTO review VALUES (null, '3', '1', '3', '1234', '좋아요', '옷이 이쁘고 배송이 빨리와서 좋았어요', DEFAULT, DEFAULT, DEFAULT, DEFAULT);
SELECT * FROM review;


DROP TABLE IF EXISTS reply ;
CREATE TABLE reply (
	idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,			-- 댓글 번호
	board_idx BIGINT NOT NULL,									-- 게시글 번호(FK)
	reply_writer VARCHAR(30),									-- 댓글 작성자
	reply_comment TEXT NOT NULL,								-- 댓글 내용
	reply_image TEXT DEFAULT '이미지 없음',					-- 댓글 사진
	create_date TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),								-- 댓글 등록날짜
	update_date TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),								-- 댓글 수정날짜
	reply_status CHAR(3) DEFAULT '공개',                                -- 댓글 상태 공개, 비공개
	reply_class INT NOT NULL,						-- 댓글(0), 대댓글(1)
	reply_order INT NOT NULL,						-- 대댓글 순서
	reply_groupNum INT NOT NULL					-- 댓글그룹 번호
);

INSERT INTO reply VALUES (NULL, '1', 'ww', 'ghfghfhfh', null, DEFAULT, DEFAULT, DEFAULT, 0, 0, 0);
SELECT * FROM reply;