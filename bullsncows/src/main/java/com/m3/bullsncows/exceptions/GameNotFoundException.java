/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.m3.bullsncows.exceptions;

/**
 *
 * @author Jeonghoon
 */
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(int gameId) {
        super(String.format("Game with Id %d is not found.", gameId));
    }
}
