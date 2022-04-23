/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.m3.bullsncows.controllers;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import com.m3.bullsncows.services.BullsAndCowsGameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/bulls-and-cows")
public class GameController {

    @Autowired
    private BullsAndCowsGameService service;

    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int beginGame() {
        return service.beginGame();
    }

    @PostMapping("/guess")
    public Round makeGuess(@RequestBody Round round) {
//        Round result = service.guessNumber(round);
//        return result;

        // this for testing
        System.out.println(round.getGuess());
        System.out.println(round.getGame());

        // TODO:
        // need to modify game id processing.
        return null;
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