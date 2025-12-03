package com.example.repaso_examen.repository;

import com.example.repaso_examen.model.Taller;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TallerRepository {
    private final JdbcTemplate jdbc;

    public TallerRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<Taller> findAll() {
        return jdbc.query("SELECT * FROM taller", (rs, rowNum) ->
                new Taller(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getBigDecimal("precio")
                )
        );
    }

    public Taller findById(int id) {
        return jdbc.queryForObject("SELECT * FROM taller WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        new Taller(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("descripcion"),
                                rs.getTimestamp("fecha").toLocalDateTime(),
                                rs.getBigDecimal("precio")
                        )
        );
    }
}
