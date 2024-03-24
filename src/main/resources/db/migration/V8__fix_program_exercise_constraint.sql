drop table if exists "program_exercise";

create table "program_exercise"
(
    "program_id"  integer not null,
    "exercise_id" integer not null,
    constraint "pe_program_constraint" foreign key (program_id) references program (id),
    constraint "pe_exercise_context_constraint" foreign key (exercise_id) references exercise_context (id)
);