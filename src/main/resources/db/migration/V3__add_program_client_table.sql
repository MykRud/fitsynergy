drop table if exists "program_client";
drop sequence if exists "program_user_id_seq";

create table "program_client"
(
    "id"            integer not null,
    "program_id"    integer not null,
    "client_id"     integer not null,
    "start_date"    timestamp,
    "complete_date" timestamp,
    "is_completed"  boolean,
    primary key ("id"),
    constraint "pc_client_constraint" foreign key (client_id) references app_user (id),
    constraint "pc_program_constraint" foreign key (program_id) references program (id)
);

create sequence "program_user_id_seq" increment by 1;