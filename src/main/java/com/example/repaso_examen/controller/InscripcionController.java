package com.example.repaso_examen.controller;

import com.example.repaso_examen.dto.Paso1DTO;
import com.example.repaso_examen.dto.Paso2DTO;
import com.example.repaso_examen.model.Inscripcion;
import com.example.repaso_examen.model.Taller;
import com.example.repaso_examen.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inscripcion")
@SessionAttributes("inscripcion") //Guardar la inscripcion de manera automatica
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    // Inicializamos el objeto de sesión
    @ModelAttribute("inscripcion")
    public Inscripcion initCompra() {
        return new Inscripcion();
    }

    //Paso 1: mostramos los talleres
    @GetMapping("/paso1")
    public String paso1(Model model, @ModelAttribute("inscripcion") Inscripcion inscripcion) {
        List<Taller> talleres = service.obtenerTodosTalleres();

        // Rellenamos el DTO desde el objeto de sesión
        Paso1DTO paso1DTO = new Paso1DTO();
        paso1DTO.setTallerId(inscripcion.getTallerId());
        paso1DTO.setNumeroAsistentes(inscripcion.getNumeroAsistentes());

        model.addAttribute("talleres", talleres);
        model.addAttribute("paso1DTO", paso1DTO);
        return "paso1";
    }

    // Paso 1: procesar selección de taller y número de asistentes
    @PostMapping("/paso1")
    public String procesarPaso1(
            @ModelAttribute("inscripcion") Inscripcion inscripcion,
            @Valid @ModelAttribute("paso1DTO") Paso1DTO dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<Taller> talleres = service.obtenerTodosTalleres();
            model.addAttribute("talleres", talleres);
            return "paso1";
        }
        // Guardamos en el objeto de sesión

        inscripcion.setTallerId(dto.getTallerId());
        inscripcion.setNumeroAsistentes(dto.getNumeroAsistentes());

        return "redirect:/inscripcion/paso2";
    }

    // Paso 2: mostrar formulario de datos del participante
    @GetMapping("/paso2")
    public String mostrarPaso2(@ModelAttribute("inscripcion") Inscripcion inscripcion, Model model) {
        Taller taller = service.obtenerTallerPorId(inscripcion.getTallerId());
        model.addAttribute("taller", taller);

        // Rellenamos el DTO desde el objeto de sesión
        Paso2DTO dto = new Paso2DTO();
        dto.setNombreParticipante(inscripcion.getNombreParticipante());
        dto.setEmailParticipante(inscripcion.getEmailParticipante());
        model.addAttribute("paso2DTO", dto);

        return "paso2";
    }

    // Paso 2: procesar datos del participante y guardar inscripción
    @PostMapping("/paso2")
    public String confirmarInscripcion(
            @ModelAttribute("inscripcion") Inscripcion inscripcion,
            @Valid @ModelAttribute("paso2DTO") Paso2DTO dto,
            BindingResult bindingResult,
            Model model) {

        Taller taller = service.obtenerTallerPorId(inscripcion.getTallerId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("taller", taller);
            return "paso2";
        }

        //Guardamos el objeto de la sesion
        inscripcion.setNombreParticipante(dto.getNombreParticipante());
        inscripcion.setEmailParticipante(dto.getEmailParticipante());
        inscripcion.setPrecioTotal(service.calcularPrecioTotal(taller, inscripcion.getNumeroAsistentes()));

        service.guardarInscripcion(inscripcion);

        return "redirect:/inscripcion/listado";
    }

    // Listado final: mostrar todas las inscripciones
    @GetMapping("/listado")
    public String listado(Model model) {
        List<Inscripcion> inscripciones = service.listarInscripciones();
        model.addAttribute("inscripciones", inscripciones);
        return "listado";
    }
}

