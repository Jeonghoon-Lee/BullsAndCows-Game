package com.m3.bullsncows.dto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git
 * Design of a class ... on month day, year
 */
public class Game {
    private int id;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String answer;
    private String status = "IN PROGRESS";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String generateRandom(){
        
        // to generate 4 distincts random numbers between 0 inclusive and 4 exclusive
        String randString = "";
        List<Integer> randoms = new ArrayList<>(); // Set would be better
        Random rand = new Random();
        while (randoms.size() < 4) {
            Integer gen = rand.nextInt(10);
            if (!randoms.contains(gen)) {
                randoms.add(gen);
            }
        }
        for (Integer random : randoms) {
            randString += random;
        }
        return randString;
    }
    
    @Override
    public String toString() {
        return "User wins Game{" + "id=" + id + ", answer=" + answer + ", status=" + status + '}';
    }

}
