/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.m3.bullsncows.dao;

import com.m3.bullsncows.dto.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class GameDatabaseDao implements GameDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Game add(Game game) {
        final String SQL = "INSERT INTO game(answer) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    SQL,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String SQL = "SELECT * FROM game;";

        return jdbcTemplate.query(SQL, new GameMapper());
    }

    @Override
    public Game getGameById(int id) {
        final String SQL = "SELECT * FROM game WHERE gameId = ?;";

        // game id not exist
        try {
            return jdbcTemplate.queryForObject(SQL, new GameMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean update(Game game) {
        final String SQL = "UPDATE game SET "
                + "currentRoundNumber = ?, "
                + "finished = ? "
                + "WHERE gameId = ?;";

        return jdbcTemplate.update(SQL,
                game.getCurrentRoundNumber(),
                game.getFinished(),
                game.getGameId()) > 0;
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setCurrentRoundNumber(rs.getInt("currentRoundNumber"));
            game.setFinished(rs.getBoolean("finished"));

            return game;
        }
    }

}
