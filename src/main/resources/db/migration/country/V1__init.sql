
create extension if not exists "uuid-ossp";

create table if not exists countries
(
    id                      UUID unique        not null default gen_random_uuid() primary key,
    name                varchar(50) not null,
    code                varchar(2)       not null
);

insert into countries (name, code)
values
('Russia', 'RU'),
('United States', 'US');