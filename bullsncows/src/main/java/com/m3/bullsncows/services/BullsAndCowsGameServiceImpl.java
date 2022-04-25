package com.m3.bullsncows.services;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import com.m3.bullsncows.dao.GameDao;
import com.m3.bullsncows.dao.RoundDao;
import com.m3.bullsncows.exceptions.GameNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import static java.util.stream.Collectors.toList;
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

    private Random randomizer = new Random();

    @Autowired
    public BullsAndCowsGameServiceImpl(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    @Override
    public Game getGameById(int gameId) {
//        return gameDao.getGameById(gameId);
        Game foundGame = gameDao.getGameById(gameId);

        foundGame = hideAnswer(foundGame);

        // check if game id exists.
        if (foundGame == null) {
            throw new GameNotFoundException(gameId);
        }

        return foundGame;
    }

    @Override
    public List<Game> getAllGames() {
//        return gameDao.getAllGames();
        return gameDao.getAllGames().stream()
                .map(game -> hideAnswer(game))
                .collect(toList());
    }

    @Override
    public Game beginGame() {
        Game newGame = new Game();
        String randString = newGame.generateRandom();

        newGame.setAnswer(randString);
        newGame.setStatus("IN PROGRESS");
        newGame = gameDao.add(newGame);

        return newGame;
    }

    @Override
    public Optional<Round> guessNumber(int gameId, int guess) {

        Round round = new Round();
        Game game = gameDao.getGameById(gameId);
        if (game != null) {
            round.setGuess((Integer) guess);
            round.setGame(game);

            if (game.getAnswer().equals(round.getGuess().toString())) {
                game.setStatus("FINISHED");
                game.toString();
            }
        }

        if (game != null && game.getStatus().equals("IN PROGRESS")) {
            String result = matchChar(game.getAnswer(), round.getGuess().toString());
            round.setResult(result);
        }
        roundDao.add(round);
        return Optional.ofNullable(round);
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) {
        // check if game id exists.
        if (gameDao.getGameById(gameId) == null) {
            throw new GameNotFoundException(gameId);
        }

        return roundDao.getAllRoundsByGameId(gameId);
    }

    public String matchChar(String answer, String response) {
        String result = "";
        if (answer.equals(response)) {
            result = "e e e e"; //e: exact
        } else {
            int len = answer.length();
            for (int i = 0; i < len - 1; i++) { // len -1: to avoid StringIndexOutOfBound

                if (answer.substring(i, i + 1).equals(response.substring(i, i + 1))) {
                    result += "e ";
                } else if (answer.contains(response.substring(i, i + 1))) {
                    result += "p "; // p: partial
                } else if (answer.contains(response.substring(i))) { // to consider the last character in the String
                } else {
                    result += "n "; // n: non-existent
                }
            }
        }
        return result;
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
        if (game != null && !game.getStatus().equals("FINISHED")) {
            game.setAnswer(null);
        }
        return game;
    }
}
