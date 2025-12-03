package com.example.repaso_examen.repository;

import com.example.repaso_examen.model.Inscripcion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InscripcionRepository {

    private final JdbcTemplate jdbc;

    public InscripcionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Guardar una nueva inscripción
    public void save(Inscripcion inscripcion) {
        String sql = "INSERT INTO inscripcion (taller_id, nombre_participante, email_participante, numero_asistentes, precio_total) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql,
                inscripcion.getTallerId(),
                inscripcion.getNombreParticipante(),
                inscripcion.getEmailParticipante(),
                inscripcion.getNumeroAsistentes(),
                inscripcion.getPrecioTotal()
        );
    }

    // Listar todas las inscripciones
    public List<Inscripcion> findAll() {
        String sql = "SELECT * FROM inscripcion";
        return jdbc.query(sql, new RowMapper<Inscripcion>() {
            @Override
            public Inscripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Inscripcion.builder()
                        .id(rs.getInt("id"))
                        .tallerId(rs.getInt("taller_id"))
                        .nombreParticipante(rs.getString("nombre_participante"))
                        .emailParticipante(rs.getString("email_participante"))
                        .numeroAsistentes(rs.getInt("numero_asistentes"))
                        .precioTotal(rs.getBigDecimal("precio_total"))
                        .build();
            }
        });
    }
}