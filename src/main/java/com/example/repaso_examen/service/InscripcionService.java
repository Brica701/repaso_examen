package com.example.repaso_examen.service;

import com.example.repaso_examen.model.Inscripcion;
import com.example.repaso_examen.model.Taller;
import com.example.repaso_examen.repository.InscripcionRepository;
import com.example.repaso_examen.repository.TallerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class InscripcionService {


    private final TallerRepository tallerRepo;
    private final InscripcionRepository inscripcionRepo;

    public InscripcionService(TallerRepository tallerRepo, InscripcionRepository inscripcionRepo) {
        this.tallerRepo = tallerRepo;
        this.inscripcionRepo = inscripcionRepo;
    }

    //Talleres
    // Obtener todos los talleres
    public List<Taller> obtenerTodosTalleres() {
        return tallerRepo.findAll();
    }

    // Obtener un taller por su ID
    public Taller obtenerTallerPorId(int id) {
        return tallerRepo.findById(id);
    }

    //Inscripciones
    // Calcular el precio total de la inscripción
    public BigDecimal calcularPrecioTotal(Taller taller, int numeroAsistentes) {
        return taller.getPrecio().multiply(BigDecimal.valueOf(numeroAsistentes));
    }

    // Guardar una inscripción en la base de datos
    public void guardarInscripcion(Inscripcion inscripcion) {
        inscripcionRepo.save(inscripcion);
    }

    // Listar todas las inscripciones
    public List<Inscripcion> listarInscripciones() {
        return inscripcionRepo.findAll();
    }

    //Admin
    public Inscripcion obtenerInscripcionPorId(int id) {
        return inscripcionRepo.findById(id);
    }

    public void actualizarInscripcion(int id, Inscripcion nuevaInscripcion) {
        nuevaInscripcion.setId(id);
        Taller taller = obtenerTallerPorId(nuevaInscripcion.getTallerId());
        nuevaInscripcion.setPrecioTotal(calcularPrecioTotal(taller, nuevaInscripcion.getNumeroAsistentes()));
        inscripcionRepo.update(nuevaInscripcion);
    }

    public void eliminarInscripcion(int id) {
        inscripcionRepo.deleteById(id);
    }

}