package com.example.repaso_examen.controller;

import com.example.repaso_examen.model.Inscripcion;
import com.example.repaso_examen.repository.InscripcionRepository;
import com.example.repaso_examen.service.InscripcionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/inscripciones")
public class AdminController {

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionService inscripcionService;

    public AdminController(InscripcionRepository inscripcionRepository, InscripcionService inscripcionService) {
        this.inscripcionRepository = inscripcionRepository;
        this.inscripcionService = inscripcionService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Inscripcion> inscripciones = inscripcionService.listarInscripciones();
        model.addAttribute("inscripciones", inscripciones); // Debe ser "inscripciones" y no "inscripcion"
        return "index";
    }

    @GetMapping("/añadir")
    public String aniadirGet(Model model) {
        model.addAttribute("inscripcion", new Inscripcion());
        return "añadir";
    }

    @PostMapping("/añadir")
    public String aniadirPost(@ModelAttribute("inscripcion") Inscripcion inscripcion) {
        inscripcionRepository.save(inscripcion);
        return "redirect:/admin/inscripciones/index";
    }

    @GetMapping("/editar/{id}")
    public String editarGet(@PathVariable int id, Model model) {
        Inscripcion inscripcion = inscripcionService.obtenerInscripcionPorId(id);
        model.addAttribute("inscripcion", inscripcion);
        return "editar";
    }

    @PostMapping("/editar")
    public String editarPost(@ModelAttribute("inscripcion") Inscripcion inscripcion) {
        inscripcionService.actualizarInscripcion(inscripcion.getId(), inscripcion);
        return "redirect:/admin/inscripciones/index";
    }

    @GetMapping("/borrar/{id}")
    public String borrarGet(@PathVariable int id, Model model) {
        Inscripcion inscripcion = inscripcionService.obtenerInscripcionPorId(id);
        model.addAttribute("inscripcion", inscripcion);
        return "borrar";
    }

    @PostMapping("/borrar")
    public String borrarPost(@ModelAttribute("inscripcion") Inscripcion inscripcion) {
        inscripcionService.eliminarInscripcion(inscripcion.getId());
        return "redirect:/admin/inscripciones/index";
    }
}

