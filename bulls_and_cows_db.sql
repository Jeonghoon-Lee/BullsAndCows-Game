drop database if exists guessNumberDB;
create database guessNumberDB;

use guessNumberDB;

create table game (
	gameId int auto_increment primary key,
	answer int not null,
    currentRoundNumber int default(0),
    status tinyint default(0)
)
;

create table round (
	roundId int auto_increment primary key,
    roundNumber int not null,
    guess int not null,
    gameId int not null,
    --
    constraint fk_game_round foreign key (gameId) references game(gameId)
)
;

-- for testing
-- insert into game (gameId, answer) values (1, 1234);
-- select * from game;

-- insert into round (guess, gameId, roundNumber) values (1234, 1, 1);
-- select * from round;