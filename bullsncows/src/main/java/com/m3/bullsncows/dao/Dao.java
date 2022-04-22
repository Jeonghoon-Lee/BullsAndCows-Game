package com.m3.bullsncows.dao;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import java.util.List;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;  
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git 
 * Design of an interface defining ... on month day, year
 */
public interface Dao {

    Game add(Game game);
    Round add(Round round);
    
    List<Game>getAllGames();
    List<Round>getAllRounds();
    
    Game getGameById(int id);
    Round getRoundById(int id);
}
