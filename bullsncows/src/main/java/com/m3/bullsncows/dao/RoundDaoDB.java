package com.m3.bullsncows.dao;

import com.m3.bullsncows.dao.GameDaoDB.GameMapper;
import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 * @author Ronald Gedeon; email: gedemarcel0002@hotmail.com; gitRepo:
 * https://github.com/gedegithub/C223-JavaDev.git Design of a class ... on month
 * day, year
 */
@Repository
public class RoundDaoDB implements RoundDao {

    private JdbcTemplate jdbcTemplate;

    //constructor injection
    @Autowired
    public RoundDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // specific field which is a intern private static class to RoundDaoDB
    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("id"));
            round.setGuess(rs.getInt("guess"));
            round.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            round.setResult(rs.getString("result"));
            return round;
        }
    }

    @Override
    public Round add(Round round) {

        final String sql = "INSERT INTO round(guess, timestamp, result, gameId) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGuess());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(round.getTimestamp()));
            statement.setString(3, round.getResult());
            statement.setInt(4, round.getGame().getId());
            return statement;

        }, keyHolder);

        round.setId(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public List<Round> getAllRounds() {
        final String sql = "SELECT id, guess, timestamp, result, gameId FROM round;";
        
        List<Round> rounds = jdbcTemplate.query(sql, new RoundMapper());
        
        for(Round singleRound: rounds)
            getGameForRound(singleRound);
        
        return rounds;
    }

    @Override
    public Round getRoundById(int id) {
        final String sql = "SELECT id, guess, timestamp, result, gameId "
                + "FROM round WHERE roundId = ?;";
        
        Round round = jdbcTemplate.queryForObject(sql, new RoundMapper(), id);
        getGameForRound(round);
        return round;
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        final String sql = "SELECT r.* FROM round r WHERE r.gameId = ? "; //return a []. why?
              //  + "orger by timestamp";
        
        return jdbcTemplate.query(sql, new RoundMapper(), gameId);
    }
    
    // not suppose to return Game ?
    private Game getGameForRound(Round round) {
        final String SELECT_GAME_FOR_ROUND = "SELECT g.* FROM game g "
                + "JOIN round r ON g.id = r.gameId WHERE r.id = ?";
        return jdbcTemplate.queryForObject(SELECT_GAME_FOR_ROUND, new GameMapper(), round.getId());
    }
}
