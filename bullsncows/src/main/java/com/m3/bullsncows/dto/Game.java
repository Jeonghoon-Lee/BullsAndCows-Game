package com.m3.bullsncows.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git
 * Design of a class ... on month day, year
 */
public class Game {
    private int gameId;
    private int currentRoundNumber;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String answer;

    private boolean finished;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    public void setCurrentRoundNumber(int currentRoundNumber) {
        this.currentRoundNumber = currentRoundNumber;
    }

    public void increaseRoundNumber() {
        this.currentRoundNumber++;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
