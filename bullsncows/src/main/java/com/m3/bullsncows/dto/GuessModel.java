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
    private String stringifyNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringifyNumber() {
        return stringifyNumber;
    }

    public void setStringifyNumber(String stringifyNumber) {
        this.stringifyNumber = stringifyNumber;
    }
    
}
