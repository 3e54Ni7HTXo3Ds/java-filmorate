create table IF NOT EXISTS FILM
(
    FILM_ID      INTEGER auto_increment,
    FILM_NAME    CHARACTER VARYING(50) not null,
    DESCRIPTION  CHARACTER VARYING(500),
    RELEASE_DATE DATE,
    DURATION     INTEGER
);

create unique index FILM_FILM_ID_UINDEX
    on FILM (FILM_ID);

alter table FILM
    add constraint FILM_PK
        primary key (FILM_ID);

create table IF NOT EXISTS FILM_LIKES
(
    FILM_ID INTEGER,
    USER_ID INTEGER
);

create table IF NOT EXISTS FILM_RATING;

create table IF NOT EXISTS FRIENDSHIP;

create table IF NOT EXISTS GENRE
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING(50) not null,
    constraint GENRE_PK
        primary key (GENRE_ID)
);

create table IF NOT EXISTS FILM_GENRE
(
    FILM_GENRE_ID INTEGER auto_increment,
    GENRE_ID      INTEGER not null,
    FILM_ID       INTEGER,
    constraint FILM_GENRE_FILM_FILM_ID_FK
        foreign key (FILM_ID) references FILM,
    constraint FILM_GENRE_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE
);

create unique index FILM_GENRE_GENRE_ID_UINDEX
    on FILM_GENRE (FILM_GENRE_ID);

create unique index FILM_GENRE_GENRE_NAME_UINDEX
    on FILM_GENRE (GENRE_ID);

alter table FILM_GENRE
    add constraint FILM_GENRE_PK
        primary key (FILM_GENRE_ID);

create unique index GENRE_GENRE_NAME_UINDEX
    on GENRE (GENRE_NAME);

create table IF NOT EXISTS RATING;

create table IF NOT EXISTS USER;