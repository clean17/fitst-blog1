insert into user_tb ( username, password, email, created_at) values ('ssar','1234','ssar@nate.com',now());

insert into board_tb ( title, body, user_id, created_at ) values ('안녕하세요 !!','오늘날씨가 좋네요 ㅎㅎㅎ',1,now());
insert into board_tb ( title, body, user_id, created_at ) values ('두번째 글입니다.','두번째 내용',1,now());

insert into reply_tb ( body, user_id, created_at ) values ('1등 ㅋㅋㅋ',1,now());

insert into love_tb (count) values (1);
commit;