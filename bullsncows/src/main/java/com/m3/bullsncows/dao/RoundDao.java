package com.m3.bullsncows.dao;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import java.util.List;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com; gitRepo:
 * https://github.com/gedegithub/C223-JavaDev.git Design of an interface
 * defining ... on month day, year
 */
public interface RoundDao {

    public Round add(Round round);

    public List<Round> getAllRounds();

    public List<Round> getAllRoundsByGameId(int gameId);

    public Round getRoundById(int id);
}
