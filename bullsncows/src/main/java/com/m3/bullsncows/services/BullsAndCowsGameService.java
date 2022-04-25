/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.m3.bullsncows.services;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jeonghoon
 */
public interface BullsAndCowsGameService {

    public List<Game> getAllGames();

    public Game beginGame();

    public Optional<Round> guessNumber(int gameId, int guess);

    public Game getGameById(int gameId);

    public List<Round> getRoundsByGameId(int gameId);
}
