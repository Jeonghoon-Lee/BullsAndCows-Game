/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.m3.bullsncows.dao;

import com.m3.bullsncows.dto.Game;
import com.m3.bullsncows.dto.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jeonghoon
 */
@Repository
@Profile("database")
public class RoundDatabaseDao implements RoundDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Round add(Round round) {
        final String SQL = "INSERT INTO round(roundNumber, guess, result, gameId) "
                + "VALUES(?, ?, ?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    SQL,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getRoundNumber());
            statement.setString(2, round.getGuess());
            statement.setString(3, round.getResult());
            statement.setInt(4, round.getGame().getGameId());
            return statement;
        }, keyHolder);

        round.setRoundId(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public List<Round> getAllRounds() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Round add(Game game, Round round) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        final String SQL = "SELECT * FROM round "
                + "JOIN game USING(gameId) "
                + "WHERE gameId = ?";

        return jdbcTemplate.query(SQL, new RoundMapper(), gameId);
    }

    @Override
    public Round getRoundById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Add the game infotmation to the last round
    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGuess(rs.getString("guess"));
            round.setRoundNumber(rs.getInt("roundNumber"));
            round.setResult(rs.getString("result"));

            Game game = new Game();
            if (rs.getInt("currentRoundNumber") == rs.getInt("roundNumber")) {
                game.setGameId(rs.getInt("gameId"));
                game.setCurrentRoundNumber(rs.getInt("currentRoundNumber"));
                game.setFinished(rs.getBoolean("finished"));
                if (rs.getBoolean("finished")) {
                    game.setAnswer(rs.getString("answer"));
                } else {
                    game.setAnswer(null);
                }
            } else {
                game = null;
            }
            round.setGame(game);

            return round;
        }
    }
}
