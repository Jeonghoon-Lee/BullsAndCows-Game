package com.m3.bullsncows.dao;

import com.m3.bullsncows.dto.Game;
import java.util.List;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git
 * Design of an interface defining ... on month day, year
 */
public interface GameDao {

    Game add(Game game);


    List<Game>getAllGames();


    Game getGameById(int id);

    // true if item exists and is updated
    boolean update(Game game);
}
