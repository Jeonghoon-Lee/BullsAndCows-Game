package com.m3.bullsncows.services;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import com.m3.bullsncows.dao.GameDao;
import com.m3.bullsncows.dao.RoundDao;
import com.m3.bullsncows.exceptions.GameFinishedException;
import com.m3.bullsncows.exceptions.GameNotFoundException;
import com.m3.bullsncows.exceptions.InvalidRequestParametersException;

import java.util.List;
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

    @Autowired
    public BullsAndCowsGameServiceImpl(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    @Override
    public Game getGameById(int gameId) {
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

        return hideAnswer(newGame);
    }

    @Override
    public Optional<Round> guessNumber(int gameId, String guess) {
        // check if request is valid
        if (gameId == 0) {
            throw new InvalidRequestParametersException("Invalid parameters - Missing game id");
        }
        if (guess == null || guess.length() != 4) {
            throw new InvalidRequestParametersException("Guess number should be 4 digit number.");
        }

        Round round = new Round();
        Game game = gameDao.getGameById(gameId);

        // check if game id exists.
        if (game == null) {
            throw new GameNotFoundException(gameId);
        }
        // check if game was finished
        if (game.getStatus().equals("FINISHED")) {
            throw new GameFinishedException(game.getId());
        }

        round.setGuess(guess);
        round.setGame(game);

        if (game.getAnswer().equals(round.getGuess())) {
            game.setStatus("FINISHED");
//            game.toString();
            round.setResult("e:4:p:0");
            gameDao.update(game);
        }

        if (game.getStatus().equals("IN PROGRESS")) {
            String result = checkAnswer(game.getAnswer(), round.getGuess());
//            String result = matchChar(game.getAnswer(), round.getGuess());
            round.setResult(result);
        }
        roundDao.add(round);

        round.setGame(hideAnswer(game));
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
            for (int i = 0; i < len - 1; i++) { // len - 1: to avoid StringIndexOutOfBound

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

    // compare answer and user's game
    // return result string
    private String checkAnswer(String answer, String guess) {

        // An exact match occurs when the user guesses
        // the correct digit in the correct position.
        int exactMatches = 0;

        // A partial match occurs when the user guesses
        // the correct digit but in the wrong position.
        int partialMatches = 0;

        for (int i = 0; i < guess.length() -1; i++) {
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
