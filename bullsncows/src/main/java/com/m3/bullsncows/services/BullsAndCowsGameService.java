/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.m3.bullsncows.services;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import java.util.List;

/**
 *
 * @author Jeonghoon
 */
public interface BullsAndCowsGameService {

    List<Game> getAllGames();

    int beginGame();

    Round guessNumber(Round round);

    Game getGameById(int gameId);

    List<Round> getRoundsByGameId(int gameId);
}
