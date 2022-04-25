/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.m3.bullsncows.services;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import com.m3.bullsncows.dao.GameDao;
import com.m3.bullsncows.dao.RoundDao;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jeonghoon
 */
@Service
public class BullsAndCowsGameServiceImpl implements BullsAndCowsGameService {

    private final GameDao gameDao;
    private final RoundDao roundDao;

    @Autowired
    public BullsAndCowsGameServiceImpl(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    @Override
    public Game getGameById(int gameId) {
        return gameDao.getGameById(gameId);
    }

    @Override
    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    @Override
    public Game beginGame() {
        Game newGame = new Game();
        String randString = "";

        // to generate 4 distincts random numbers between 0 inclusive and 4 exclusive
        List<Integer> randoms = new ArrayList<>(); // Set would be better
        Random rand = new Random();
        while (randoms.size() < 5) {
            Integer gen = rand.nextInt(10);
            if (!randoms.contains(gen)) {
                randoms.add(gen);
            }
        }
        for (Integer random : randoms) {
            randString += random;
        }

        newGame.setAnswer(randString);
        newGame.setStatus("IN PROGRESS");
        newGame = gameDao.add(newGame);

        return newGame;
    }

    @Override
    public Optional<Round> guessNumber(int gameId, Integer guess) {
    
        Round round = new Round();
        Game game = gameDao.getGameById(gameId);
        if (game != null) {
            round.setGuess(guess);

            if (game.getAnswer().equals(round.getGuess().toString())) {
                game.setStatus("FINISHED");
                game.toString();
            }
        }

        if (game != null && game.getStatus().equals("IN PROGRESS")) {
            String result = matchChar(game.getAnswer(), round.getGuess().toString());
            round.setResult(result);
        }

        return Optional.ofNullable(round);
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) {
        return roundDao.getAllRoundsByGameId(gameId);
    }

//    public String validateGuess(Round round, Game game) {
//        String res = "";
//
//        return res;
//    }

    public String matchChar(String answer, String response) {
        String result = "";
        if (answer.equals(response)) {
            result = "e e e e";
        } else {
            for (int i = 0; i < answer.length(); i++) {

                if (answer.substring(i, i + 1).equals(response.substring(i, i + 1))) {
                    result += "e ";
                } else if (answer.contains(response.substring(i, i+1))) {
                    result += "p ";
                } else {
                    result += "n ";
                }
            }
        }
        return result;
    }
}
