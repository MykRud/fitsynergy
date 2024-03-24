drop table if exists "app_user";
drop table if exists "client";
drop table if exists "trainer";
drop sequence if exists "user_id_seq";

create table "app_user"
(
    "id"         integer     not null,
    "email"      varchar(255),
    "password"   varchar(255),
    "first_name" varchar(255),
    "last_name"  varchar(255),
    "age"        integer,
    "gender" varchar(31) check ( gender in ('male', 'female') ),
    primary key ("id")
);

create table "client"
(
    "client_id" INTEGER,
    primary key ("client_id"),
    constraint "c_app_user_constraint" foreign key (client_id) references app_user (id)
);

create table "trainer"
(
    "trainer_id" INTEGER,
    primary key ("trainer_id"),
    constraint "t_app_user_constraint" foreign key (trainer_id) references app_user (id)
);

create sequence "user_id_seq" increment by 1;