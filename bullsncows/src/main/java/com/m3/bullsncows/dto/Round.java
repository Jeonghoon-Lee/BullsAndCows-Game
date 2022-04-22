package com.m3.bullsncows.dto;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;  
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git 
 * Design of a class ... on month day, year
 */
public class Round {
    private int roundId;
    private int roundNumber;
    private int guess;

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }
    
}
