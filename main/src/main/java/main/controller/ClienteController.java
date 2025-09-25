package main.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String verDetalleAcividad(@PathVariable("id") Long id, Model model,
            @RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String nombre,
            Authentication autenticacion) {

        Actividad actividad = actividadService.listarById(id);

        

        if (actividad == null) {
            return "redirect:/cliente/dashboard"; 
        }

        

        model.addAttribute("actividad", actividad);
        int pageSize = 6;
        Page<Actividad> actividadesPage = actividadService.getActividadesWithPaginationMain(page, pageSize, nombre);
        model.addAttribute("actividades", actividadesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", actividadesPage.getTotalPages());
        model.addAttribute("filtroNombre", nombre);

        List<Integer> pageNumbers = IntStream.range(0, actividadesPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        return "cliente/detalle-actividad"; // templates/cliente/detalle-actividad.html
    }

    /**
     * En la carpeta de templates, se creo una carpeta llamada cliente, que contiene
     * el archivo de cliente-dashboard.html, en esa vista es donde se mostrara la
     * lista de cliente. Lo di todo haciendolo XD,
     * 
     * Se tienen que visualizar las actividades de los clientes
     * 
     */

}
