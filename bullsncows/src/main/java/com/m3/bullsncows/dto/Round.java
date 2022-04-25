package com.m3.bullsncows.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git
 * Design of a class ... on month day, year
 */
public class Round {

    @JsonIgnore
    private int id;
    //private int roundNumber;
    private Integer guess;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String result;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Game game;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getRoundNumber() {
//        return roundNumber;
//    }
//
//    public void setRoundNumber(int roundNumber) {
//        this.roundNumber = roundNumber;
//    }

    public Integer getGuess() {
        return guess;
    }

    public void setGuess(Integer guess) {
        this.guess = guess;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
