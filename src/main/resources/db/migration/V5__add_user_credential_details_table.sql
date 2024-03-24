drop table if exists "system_user_details";
drop sequence if exists "system_user_details_id_seq";

create table "system_user_details"
(
    "id"            integer not null,
    "role"          varchar(21) default 'USER',
    "is_expired"    boolean default false,
    "is_locked"     boolean default false,
    "is_credentials_expired"    boolean default false,
    "is_enabled" boolean default false,
    primary key ("id")
);

create sequence "system_user_details_id_seq" increment by 1;

alter table "app_user" ADD COLUMN "user_details_id" integer;
alter table "app_user" add constraint "au_user_details_constraint" foreign key (user_details_id) references system_user_details(id);