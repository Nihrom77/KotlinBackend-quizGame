--Вопросы в квизе
create table question
(
    id            bigserial primary key,
    question_text text not null,
    type          text not null,
    topic         text not null,
    image_id      text
);

--Ответы на вопросы. Ответов у одного вопроса может быть несколько
create table question_answer
(
    id          bigserial primary key,
    question_id bigint references question (id) on delete cascade,
    answer_text text not null
);

--Структура БД гарантирует, что на каждый вопрос будет ровно один правильный ответ
alter table question
    add column correct_answer_id bigserial not null references question_answer (id);


--Комната для игры
create table game
(
    id           bigserial PRIMARY KEY,
    link         text unique not null,
    name         text        not null,
    captain_id   bigserial   not null references person (id),
    start_date   timestamp,
    end_date     timestamp,
    created_date timestamp   not null default now(),
    score        int         not null default 0 check ( score >= 0 )
);

create table player_in_game
(
    id        bigserial PRIMARY KEY,
    player_id bigint references person (id),
    game_id   bigint references game (id)
);


