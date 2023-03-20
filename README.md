# What is this?

This is the Project Manager web application.

Default login data: admin -> {admin, admin}; user -> {user, user}.

Hierarchical structure of projects and subprojects. Admin may create, edit and delete them. 
Any user can get the structure.

Tasks for any project/subproject level. Tasks are divided into two types: for the manager, 
for the technician. 
Tasks contain name, description, status (new, progress, done), date of creation, 
date the status was changed.

Any user can create a task for any level of a project/project, change their status, 
delete their tasks and view all tasks. Any administrator can edit, delete a task.

Any administrator can create new accounts and ban/unban users.

Security is implemented with Spring Security, access to the database through Spring JPA, 
the database is H2, SQL-script for creating tables in the database and filling it with test values is 
attached to the project (*"V1.0__create_table_and_insert_input_data.sql "*), 
migration via Flyway, building via maven, simple frontend (http://localhost:8080).

### How to start it up?
For Windows:
1) Make sure you have JDK 17 installed - https://www.oracle.com/java/technologies/downloads/
2) Open a command line: ***Win+R -> cmd***
3) Navigate to the directory with the executable .jar file using the ***cd*** operator.
4) Type ***java -jar AdvEngineeringTestTask-0.1.jar***

________________________
# Что это?

Это веб-приложение "Проектный менеджер".

Дефолтные данные для входа: admin -> {admin, admin}; user -> {user, user}.

Иерархическая структура проектов и подпроектов. Создавать,
редактировать, удалять может админ. Получить структуру может любой
пользователь.

Задачи для любого уровня проекта\подпроекта. Задачи делятся на два
типа: для менеджера, для технического специалиста. Содержат
название, описание, статус (new, progress, done), дату создания, дату изменения
статуса.

Любой пользователь может создать задачу для любого уровня проекта\
подпроекта, изменить статус, удалить свою задачу, посмотреть все задачи.
Любой администратор может редактировать, удалить задачу.

Любой администратор может создавать новые учетные записи и банить/разбанивать пользователей.

Реализована безопасность с помощью Spring Security, доступ к базе данных через Spring JPA, 
база данных - H2, SQL-скрипт создания таблиц в базе данных и наполнения ее тестовыми
данными приложен к проекту (*"V1.0__create_table_and_insert_input_data.sql"*), 
миграция через Flyway, сборка через maven, простейший фронтэнд (http://localhost:8080).

### Как запустить?
Для Windows:

1) Убедитесь, что у вас установлена JDK 17 - https://www.oracle.com/java/technologies/downloads/
2) Откройте командную строку: ***Win+R -> cmd***
3) Перейдите в директорию с исполняемым .jar файлом, используя оператор ***cd***
4) Введите ***java -jar AdvEngineeringTestTask-0.1.jar***
