drop database if exists guessNumberDB;
create database guessNumberDB;

use guessNumberDB;

create table game (
	id int primary key auto_increment,
	answer VARCHAR(15) not null,
    status VARCHAR(15)
);

create table round (
	id int primary key auto_increment,
    guess VARCHAR(15) not null,
    `timestamp` DATETIME,
    result VARCHAR(15),
    gameId int not null,
    foreign key (gameId) references game(id)
);

-- for testing
-- insert into game (id, answer, status) values (1, "1234", "IN PROGRESS");
-- select * from game;
-- insert into round (id, guess, `timestamp`, result, gameId) values (1, 5678, now(), "1234", 1);
-- select * from round;