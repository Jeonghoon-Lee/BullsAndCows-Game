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
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jeonghoon
 */
@Service
public class BullsAndCowsGameServiceImpl implements BullsAndCowsGameService {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private RoundDao roundDao;

    private Random randomize;

    public BullsAndCowsGameServiceImpl() {
        this.randomize = new Random();
    }

    @Override
    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    @Override
    public int beginGame() {
        Game newGame = new Game();
        newGame = gameDao.add(newGame);

        // TODO:
        // need to change type of answer to char(4)
        newGame.setAnswer(randomize.nextInt(10000));

        return newGame.getGameId();
    }

    @Override
    public Round guessNumber(Round round) {
        Game game = gameDao.getGameById(round.getGame().getGameId());

        if (game == null) {
            return null;
        }

        // increase round number in game
        game.setCurrentRoundNumber(game.getCurrentRoundNumber() + 1);

        // set round number
        round.setRoundNumber(game.getCurrentRoundNumber());

        // TODO:
        // check answer and generate response
        // need to create help method to compare answer
        return round;
    }

    @Override
    public Game getGameById(int gameId) {
        return gameDao.getGameById(gameId);
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) {
        return roundDao.getAllRoundsByGameId(gameId);
    }
}
