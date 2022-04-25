package com.m3.bullsncows.controllers;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.GuessModel;
import com.m3.bullsncows.dto.Round;
import com.m3.bullsncows.services.BullsAndCowsGameService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jeonghoon
 */
@RestController
@RequestMapping("/bullsncows")
public class GameController {

    private BullsAndCowsGameService service;
    private GuessModel guess;

    @Autowired
    public GameController(BullsAndCowsGameService service, GuessModel guess) {
        this.service = service;
        this.guess = guess;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Optional<Game> game) {
        return service.beginGame();
    }

    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Round> makeGuess(@RequestBody @NonNull GuessModel guessModel) {
        return service.guessNumber(guessModel.getId(), guessModel.getStringifyNumber());
    }
//    public ResponseEntity<Round> makeGuess(@RequestBody Round round) {
//        Round result = service.guessNumber(round);
//        if (result == null) {
//            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
//        }
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> findGameById(@PathVariable int gameId) {
        Game result = service.getGameById(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rounds/{gameId}")
    public ResponseEntity<List<Round>> findAllRoundsByGameId(@PathVariable int gameId) {
        List<Round> result = service.getRoundsByGameId(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

}
