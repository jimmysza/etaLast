package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.service.ActividadService;
import main.service.ColaboradorService;
import main.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/as")
public class ActividadController {

    private final ActividadService actividadService;
    private final ColaboradorService colaboradorService;
    private final UsuarioService usuarioService;


    @Autowired
    public ActividadController(ActividadService actividadService, UsuarioService usuarioService,ColaboradorService colaboradorService) {
        this.actividadService = actividadService;
        this.usuarioService = usuarioService;
        this.colaboradorService = colaboradorService;
    }

    
    
/*
    @PostMapping("/reservar/{idActividad}/{idCliente}")
    public String reservar(@PathVariable Long idActividad, @PathVariable Long idCliente) {
        var actividad = actividadService.busActividadId(idActividad);
        Usuario usuario = usuarioService.buscarPorId(idCliente);

        if (actividad != null && usuario instanceof Cliente cliente) {
            actividad.getClientes().add(cliente);
            cliente.getActividadesReservadas().add(actividad);
            actividadService.crearActividad(actividad);
        }

        return "redirect:/actividades";
    } */
}
