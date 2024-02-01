create table person
(
    id                   bigserial PRIMARY KEY,
    username             text    not null,
    password             text    not null,
    first_name           text,
    last_name            text,
    locked               boolean not null default false, --Используется, чтобы банить пользователей
    user_authority_group text    not null default 'ROLE_USER'
);

insert into person (username, password, first_name, last_name, user_authority_group)
values ('admin', '$2a$12$/NvJ6lbZ/8hwbQ8CqZi2Que2didJlPad4nzNvELBjoFlBVZqcinoi', 'admin', '',
        'ROLE_ADMIN'); --password=admin


insert into person (username, password, first_name, last_name, user_authority_group)
values ('user', '$2a$12$kZwpZx/aichM0K0otdlFQ.4aEgFBbj3EW1Qghu/PlOQQm.K6RzA2S', 'user', '', 'ROLE_USER');--password=user