Drop table film;

Drop TABLE category;

CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE film (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_year INT NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);
 
 
 
 

 CREATE TABLE category (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE film (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_year INT NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);
INSERT INTO category (id, name) VALUES
(1, 'Action'),
(2, 'Comedy'),
(3, 'Drama'),
(4, 'Sci-Fi'),
(5, 'Animation');


INSERT INTO film (id, title, release_year, category_id) VALUES
(1, 'The Matrix', 1999, 4),
(2, 'The Dark Knight', 2008, 1),
(3, 'Toy Story', 1995, 5),
(4, 'Forrest Gump', 1994, 3),
(5, 'Superbad', 2007, 2),
(6, 'Avengers: Endgame', 2019, 1),
(7, 'Spirited Away', 2001, 5),
(8, 'Arrival', 2016, 4),
(9, 'Parasite', 2019, 3),
(10, 'Shaun of the Dead', 2004, 2); 

INSERT INTO film (id, title, release_year, category_id) VALUES (11, 'Inception', 2010, 1);
INSERT INTO film (id, title, release_year, category_id) VALUES (12, 'The Dark Knight', 2008, 1);
INSERT INTO film (id, title, release_year, category_id) VALUES (13, 'Joker', 2019, 3);