CREATE TABLE USERS
(
    id       BIGINT auto_increment,
    login    VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    role     VARCHAR NOT NULL,
    banned   BOOLEAN NOT NULL default false,
    CONSTRAINT id_pk
        PRIMARY KEY (id)
);

INSERT INTO USERS(login, password, role)
VALUES ('admin', 'admin', 'admin'),
       ('user', 'user', 'user');

create table tickets
(
    id                 int auto_increment,
    user_Id            int     not null,
    type               varchar not null,
    main_Ticket_Id     int     not null,
    for_Type           varchar not null,
    name               varchar not null,
    status             varchar not null,
    description       varchar not null,
    create_Date        varchar not null,
    status_Change_Date varchar not null,
    deleted            boolean not null default false,
    constraint tickets_id_pk
        primary key (id)
);

alter table TICKETS
    add constraint "TICKETS_userId_id_FK"
        foreign key (user_Id) references USERS;

insert into TICKETS(user_Id, type, main_Ticket_Id, for_Type, name, status, description, create_Date,
                    status_Change_Date)
values (1, 'Проект', 0, 'Менеджер', 'Проект 1', 'progress', 'Описание проекта 1', '19.03.2023 20:28',
        '19.03.2023 20:28'),
       (1, 'Проект', 0, 'Менеджер', 'Проект 2', 'new', 'Описание проекта 2', '19.03.2023 20:28', '19.03.2023 20:28'),
       (1, 'Проект', 0, 'Менеджер', 'Проект 3', 'new', 'Описание проекта 3', '19.03.2023 20:28', '19.03.2023 20:28'),
       (1, 'Проект', 1, 'Менеджер', 'Проект 4', 'new', 'Описание проекта 4', '19.03.2023 20:28', '19.03.2023 20:28'),
       (1, 'Проект', 0, 'Менеджер', 'sdfsdfsdf', 'new', 'sdfsdfsdf', '19.03.2023 20:28', '19.03.2023 20:28'),
       (1, 'Проект', 0, 'Менеджер', 'dfgdfgdfg', 'new', 'dfgdfgdfgdfgdfgdfg', '19.03.2023 20:28', '19.03.2023 20:28'),
       (2, 'Задача', 4, 'Менеджер', 'dgdfgdfg', 'new', 'dfgdfgdfg', '19.03.2023 20:28', '19.03.2023 20:28'),
       (1, 'Проект', 1, 'Менеджер', 'sdfsdf', 'progress', '456456456456', '19.03.2023 20:28', '19.03.2023 21:23'),
       (1, 'Проект', 1, 'Менеджер', 'исмисми', 'new', 'смисмисми', '19.03.2023 20:28', '19.03.2023 20:28'),
       (2, 'Задача', 2, 'Менеджер', 'смисмис', 'new', 'смисми', '19.03.2023 20:28', '19.03.2023 20:28'),
       (1, 'Задача', 1, 'Менеджер', 'asdasdasdasdasdasdasdasda', 'new', 'adadasdasdasdasdasdasdasd', '19.03.2023 19:16',
        '19.03.2023 19:16'),
       (1, 'Задача', 1, 'Менеджер', 'TEыавывафывфывыва', 'done', 'TEываываыва', '19.03.2023 20:32', '19.03.2023 20:39'),
       (1, 'Проект', 0, 'Менеджер', 'ghjghj', 'done', 'fgghj', '19.03.2023 20:45', '19.03.2023 21:21'),
       (1, 'Проект', 2, 'Технический cпециалист', 'dfgdfgdfgdfg', 'new', 'dfgdfgdfgdfgdfg', '19.03.2023 20:45',
        '19.03.2023 20:45')
