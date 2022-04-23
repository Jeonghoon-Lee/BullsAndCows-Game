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
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Random randomizer;

    public BullsAndCowsGameServiceImpl() {
        this.randomizer = new Random();
    }

    @Override
    public List<Game> getAllGames() {
        return gameDao.getAllGames().stream()
                .map(game -> hideAnswer(game))
                .collect(toList());
    }

    @Override
    public Game beginGame() {
        Game newGame = new Game();
        // generate an answer
        newGame.setAnswer(generateRandomAnswer());

        // add to db with DAO
        Game gameFromDao = gameDao.add(newGame);

        return hideAnswer(gameFromDao);
    }

    @Override
    @Transactional
    public Round guessNumber(Round round) {
        Game currentGame = gameDao.getGameById(round.getGame().getGameId());

        // check if game number exists.
        if (currentGame == null) {
            return null;
        }

        // increase round number in game
        currentGame.increaseRoundNumber();

        // update round information
        round.setGame(currentGame);
        round.setRoundNumber(currentGame.getCurrentRoundNumber());

        String result = checkAnswer(currentGame.getAnswer(), round.getGuess());
        round.setResult(result);

        // if the guess is correct
        if (result.contains("e:4")) {
            currentGame.setFinished(true);
        }
        // update game information using gameDao
        gameDao.update(currentGame);

        // add round using roundDao
        Round roundFromDao = roundDao.add(round);

        // hide answer if need
        roundFromDao.setGame(hideAnswer(currentGame));

        return roundFromDao;
    }

    @Override
    public Game getGameById(int gameId) {
        Game foundGame = gameDao.getGameById(gameId);

        foundGame = hideAnswer(foundGame);

        return foundGame;
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) {
        return roundDao.getAllRoundsByGameId(gameId);
    }

    // Generate a 4-digit number where every digit is different.
    private String generateRandomAnswer() {
        final int MAX_DIGIT = 4;
        StringBuilder str = new StringBuilder();

        while (str.length() < MAX_DIGIT) {
            Integer number = randomizer.nextInt(10);
            // Check if the generated number doesn't exist
            if (str.indexOf(number.toString()) < 0) {
                str.append(number);
            }
        }
        return str.toString();
    }

    // compare answer and user's game
    // return result string
    private String checkAnswer(String answer, String guess) {

        // An exact match occurs when the user guesses
        // the correct digit in the correct position.
        int exactMatches = 0;

        // A partial match occurs when the user guesses
        // the correct digit but in the wrong position.
        int partialMatches = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                exactMatches++;
            } else if (answer.contains(guess.substring(i, i + 1))) {
                partialMatches++;
            }
        }
        // "e" stands for exact matches and "p" stands for partial matches.
        return String.format("e:%d:p:%d", exactMatches, partialMatches);
    }

    // if game is not finished, set answer to null
    private Game hideAnswer(Game game) {
        if (game != null && !game.getFinished()) {
            game.setAnswer(null);
        }
        return game;
    }
}
