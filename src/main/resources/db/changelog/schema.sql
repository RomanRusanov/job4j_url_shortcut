create table site
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table authorities
(
    id        bigserial primary key,
    authority varchar(50) not null unique
);

create table users
(
    id           bigserial primary key,
    username     varchar(50)  not null unique,
    password     varchar(100) not null,
    authority_id bigint       not null references authorities (id)
);

create table users_site
(
    id      bigserial primary key,
    user_id bigint not null references users (id),
    site_id bigint not null references site (id)

);

create table url
(
    id   bigserial primary key,
    url  varchar(255)        not null,
    code varchar(255) unique not null
);

create table site_url
(
    id      bigserial primary key,
    site_id bigint not null references site (id),
    url_id  bigint not null references url (id)

);

create table statistics
(
    id          bigserial primary key,
    url_id      bigint not null references url (id),
    site_id     bigint not null references site (id),
    total_calls bigint default 0
);

create table site_statistics
(
    id            bigserial primary key,
    site_id       bigint not null references site (id),
    statistics_id bigint not null references statistics (id)

);