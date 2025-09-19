
create extension if not exists "uuid-ossp";

create table if not exists countries
(
    id                      UUID unique        not null default uuid_generate_v1() primary key,
    name                varchar(50) not null,
    code                varchar(3)  unique     not null
);

