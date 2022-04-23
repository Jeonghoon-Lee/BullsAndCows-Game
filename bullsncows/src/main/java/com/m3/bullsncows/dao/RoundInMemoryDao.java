package com.m3.bullsncows.dao;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com;
 * gitRepo: https://github.com/gedegithub/C223-JavaDev.git
 * Design of a class ... on month day, year
 */
@Repository
@Profile("memory")
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

    // added by JH
    @Override
    public Round add(Game game, Round round) {
        int nextId = rounds.stream()
                .mapToInt(i -> i.getRoundId())
                .max()
                .orElse(0) + 1;

        round.setRoundId(nextId);
        game.setCurrentRoundNumber(game.getCurrentRoundNumber() + 1);
        round.setRoundNumber(game.getCurrentRoundNumber());
        round.setGame(game);
        rounds.add(round);

        return round;
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        return rounds.stream()
                .filter(round -> round.getGame().getGameId() == gameId)
                .collect(toList());
    }

}
