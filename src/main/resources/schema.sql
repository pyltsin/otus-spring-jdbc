DROP table if exists COMMENT;
DROP TABLE IF EXISTS AUTHOR_TO_BOOK;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS AUTHOR;

CREATE table GENRE (
  ID varchar(2000) primary key
);

CREATE TABLE AUTHOR (
  ID   integer primary key auto_increment,
  name varchar(2000)
);

CREATE TABLE Book (
  ID          integer primary key auto_increment,
  description varchar(2000),
  genre       varchar(2000) references GENRE (ID)
);

CREATE Table AUTHOR_TO_BOOK (
  book_id   integer not null   references Book (id),
  author_id integer not null   references AUTHOR (id),
);

alter table AUTHOR_TO_BOOK
  add primary key (book_id, author_id);

CREATE TABLE COMMENT (
  ID      integer primary key auto_increment,
  BOOK_ID integer not null references Book (ID),
  COMMENT varchar(2000)
);

