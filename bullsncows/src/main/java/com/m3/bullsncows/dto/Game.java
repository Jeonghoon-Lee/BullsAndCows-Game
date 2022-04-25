package com.m3.bullsncows.dto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git
 * Design of a class ... on month day, year
 */
public class Game {
    private int id;
    // private int currentRoundNumber;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String answer;
    private String status = "IN PROGRESS";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getCurrentRoundNumber() {
//        return currentRoundNumber;
//    }
//
//    public void setCurrentRoundNumber(int currentRoundNumber) {
//        this.currentRoundNumber = currentRoundNumber;
//    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User wins Game{" + "id=" + id + ", answer=" + answer + ", status=" + status + '}';
    }

}
