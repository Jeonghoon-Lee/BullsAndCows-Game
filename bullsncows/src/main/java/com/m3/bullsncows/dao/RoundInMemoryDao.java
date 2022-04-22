package com.m3.bullsncows.dao;

import com.m3.bullsncows.dto.Round;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;  
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git 
 * Design of a class ... on month day, year
 */
public class RoundInMemoryDao implements RoundDao {

    private static final List<Round> rounds = new ArrayList<>();

    @Override
    public Round add(Round round) {

        int nextId = rounds.stream()
                .mapToInt(i -> i.getRoundId())
                .max()
                .orElse(0) + 1;

        round.setRoundId(nextId);
        rounds.add(round);
        return round;
    }

    @Override
    public List<Round> getAllRounds() {
        return new ArrayList<>(rounds);
    }

    @Override
    public Round getRoundById(int id) {
        return rounds.stream()
                .filter(i -> i.getRoundId() == id)
                .findFirst()
                .orElse(null);
    }

}
