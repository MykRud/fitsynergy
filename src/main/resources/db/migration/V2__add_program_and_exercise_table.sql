drop table if exists "occupation";
drop table if exists "program";
drop table if exists "exercise";
drop table if exists "exercise_context";
drop sequence if exists "occupation_id_seq";
drop sequence if exists "program_id_seq";
drop sequence if exists "exercise_id_seq";
drop sequence if exists "exercise_context_id_seq";

create table "occupation"
(
    "id"   integer not null,
    "name" varchar(255),
    primary key ("id")
);

create table "program"
(
    "id"            integer     not null,
    "name"          varchar(255),
    "level"         varchar(31) not null,
    "occupation_id" integer,
    primary key ("id"),
    constraint "p_occupation_constraint" foreign key (occupation_id) references occupation (id)
);

create table "exercise"
(
    "id"   integer not null,
    "name" varchar(255),
    primary key ("id")
);

create table "exercise_context"
(
    "id"            integer     not null,
    "exercise_id"   integer     not null,
    "reps"          integer,
    "sets"          integer,
    "execution_time" decimal,
    "is_completed" boolean default false,
    primary key ("id"),
    constraint "ec_exercise_constraint" foreign key (exercise_id) references exercise (id)
);

create table "program_exercise"
(
    "program_id"  integer not null,
    "exercise_id" integer not null,
    constraint "pe_program_constraint" foreign key (program_id) references program (id),
    constraint "pe_exercise_constraint" foreign key (exercise_id) references exercise (id)
);

create sequence "occupation_id_seq" increment by 1;
create sequence "program_id_seq" increment by 1;
create sequence "exercise_id_seq" increment by 1;
create sequence "exercise_context_id_seq" increment by 1;