package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entity.Actividad;
import main.service.ActividadService;


@Controller
@RequestMapping("/cliente")
public class ClienteController {
    
    private final ActividadService actividadService;
    
    public ClienteController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        
        return "cliente/dashboard"; // templates/cliente/dashboard.html
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleAcividad(@PathVariable("id") Long id, Model model) {

        Actividad actividad = actividadService.listarById(id);

        
        if (actividad == null) {
            return "redirect:/cliente/dashboard"; // Redirige al dashboard si la actividad no existe
        }

        model.addAttribute("actividad", actividad);

         return "cliente/detalle-actividad"; // templates/cliente/detalle-actividad.html
    }
    

    /**
     * En la carpeta de templates, se creo una carpeta llamada cliente, que contiene el archivo de cliente-dashboard.html, en esa vista es donde se mostrara la lista de cliente. Lo di todo haciendolo XD, 
     * 
     * Se tienen que visualizar las actividades de los clientes
     * 
     */


}
