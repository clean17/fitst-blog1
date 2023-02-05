create table user_tb (
    id int auto_increment primary key,
    username varchar not null unique,
    password varchar not null,
    email varchar not null,
    created_at timestamp not null
);

create table board_tb (
    id int auto_increment primary key,
    title varchar not null,
    body longtext not null,
    user_id int not null,
    created_at timestamp not null
);

create table reply_tb (
    id int auto_increment primary key,
    body varchar,
    user_id int not null,
    created_at timestamp not null
);

create table love_tb (
    id int auto_increment primary key,
    count int    
);