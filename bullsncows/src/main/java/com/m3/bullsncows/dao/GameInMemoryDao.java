package com.m3.bullsncows.dao;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com; gitRepo:
 * https://github.com/gedegithub/C223-JavaDev.git Design of a class ... on month
 * day, year
 */
public class GameInMemoryDao implements GameDao {

    private static final List<Game> games = new ArrayList<>();

    @Override
    public Game add(Game game) {

        int nextId = games.stream()
                .mapToInt(i -> i.getGameId())
                .max()
                .orElse(0) + 1;

        game.setGameId(nextId);
        games.add(game);
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    @Override
    public Game getGameById(int id) {
        return games.stream()
                .filter(i -> i.getGameId() == id)
                .findFirst()
                .orElse(null);
    }

}
