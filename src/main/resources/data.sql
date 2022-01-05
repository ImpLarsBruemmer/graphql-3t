CREATE TABLE AUTHOR (
    id int not null auto_increment,
    name varchar(256) not null ,
    thumbnail text
);

INSERT INTO AUTHOR values (1, 'Author 1', 'thumbnail 1'), (2, 'Author 2', 'thumbnail 2');

CREATE TABLE POST (
    id int not null auto_increment,
    category varchar(256),
    text text,
    title varchar(256) not null,
    author_id int not null
);

ALTER TABLE POST ADD FOREIGN KEY (author_id) REFERENCES AUTHOR(id);

INSERT INTO POST (id, category, text, title, author_id) VALUES
    (1, null, 'text1', 'title1', 1),
    (2, null, 'text2', 'title2', 2);
