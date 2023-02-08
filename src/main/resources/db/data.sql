insert into user_tb ( username, password, email, created_at) values ('ssar','1234','ssar@nate.com',now());
insert into user_tb ( username, password, email, created_at) values ('love','1234','love@nate.com',now());

insert into board_tb ( title, content, thumbnail, user_id, created_at ) values ('안녕하세요 !!','오늘날씨가 좋네요 ㅎㅎㅎ', '/images/dora1.png', 1,now());
insert into board_tb ( title, content, thumbnail, user_id, created_at ) values ('두번째 글입니다.','두번째 내용', '/images/dora1.png', 2,now());
insert into board_tb ( title, content, thumbnail, user_id, created_at ) values ('세번째 글입니다.','세번째 내용', '/images/dora1.png', 1,now());
insert into board_tb ( title, content, thumbnail, user_id, created_at ) values ('네번째 글입니다.','네번째 내용', '/images/dora1.png', 1,now());
insert into board_tb ( title, content, thumbnail, user_id, created_at ) values ('다섯번째 글입니다.','다섯번째 내용', '/images/dora1.png', 2,now());
insert into board_tb ( title, content, thumbnail, user_id, created_at ) values ('여섯번째 글입니다.','여섯번째 내용', '/images/dora1.png', 2,now());

insert into reply_tb (content, board_id, user_id, created_at ) values ('1등 ㅋㅋㅋ', 1, 1,now());
insert into reply_tb (content, board_id, user_id, created_at ) values ('파이팅', 2, 2,now());
insert into reply_tb (content, board_id, user_id, created_at ) values ('세번째 댓글', 3, 2,now());

insert into love_tb (count) values (1);
commit;