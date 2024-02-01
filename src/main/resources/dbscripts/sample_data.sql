--1
insert into question_answer(answer_text)
values ('Лев');

insert into question (question_text, type, topic, correct_answer_id)
values ('Именно под этим животным в фильме «Необыкновенные приключения итальянцев в России» герои пытались найти клад. Под каким?',
        'text', 'кино', (select last_value from question_answer_id_seq));

update question_answer
set question_id = (select last_value from question_id_seq)
where id = (select last_value from question_answer_id_seq);

insert into question_answer(question_id, answer_text)
values ((select last_value from question_id_seq), 'Коза'),
       ((select last_value from question_id_seq), 'Орел'),
       ((select last_value from question_id_seq), 'Бык');

--2
insert into question_answer(answer_text)
values ('Лежачий');

insert into question (question_text, type, topic, correct_answer_id)
values ('Под какой камень вода не течёт?',
        'text', 'логика', (select last_value from question_answer_id_seq));

update question_answer
set question_id = (select last_value from question_id_seq)
where id = (select last_value from question_answer_id_seq);

insert into question_answer(question_id, answer_text)
values ((select last_value from question_id_seq), 'Лунный'),
       ((select last_value from question_id_seq), 'Подводный'),
       ((select last_value from question_id_seq), 'Нарисованный');

--3
insert into question_answer(answer_text)
values ('Мягким знаком');

insert into question (question_text, type, topic, correct_answer_id)
values ('И день, и ночь этим заканчиваются. Чем?',
        'text', 'логика', (select last_value from question_answer_id_seq));

update question_answer
set question_id = (select last_value from question_id_seq)
where id = (select last_value from question_answer_id_seq);

insert into question_answer(question_id, answer_text)
values ((select last_value from question_id_seq), 'Утром'),
       ((select last_value from question_id_seq), 'Вечером'),
       ((select last_value from question_id_seq), 'Похмельем');

--4
insert into question_answer(answer_text)
values ('Трость');

insert into question (question_text, type, topic, correct_answer_id)
values ('Основной атрибут Чарли Чаплина – это …',
        'text', 'логика', (select last_value from question_answer_id_seq));

update question_answer
set question_id = (select last_value from question_id_seq)
where id = (select last_value from question_answer_id_seq);

insert into question_answer(question_id, answer_text)
values ((select last_value from question_id_seq), 'Шляпа'),
       ((select last_value from question_id_seq), 'Усы'),
       ((select last_value from question_id_seq), 'Черно-белая картинка');