package com.m3.bullsncows.dto;

import org.springframework.stereotype.Component;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git
 * Design of a class ... on month day, year
 */
@Component
public class GuessModel {
    private int id;
    private String guess;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

}
