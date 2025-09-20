package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import main.entity.Actividad;
import main.entity.Colaborador;
import main.entity.Usuario;
import main.service.ActividadService;
import main.service.ColaboradorService;
import main.service.IUploadFileService;
import main.service.UsuarioService;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {

    private final IUploadFileService uploadFileService;
    private final ActividadService actividadService;
    private final ColaboradorService colaboradorService;
    private final UsuarioService usuarioService;


    @Autowired
    public ColaboradorController(ActividadService actividadService, UsuarioService usuarioService,ColaboradorService colaboradorService, IUploadFileService uploadFileService) {
        this.actividadService = actividadService;
        this.usuarioService = usuarioService;
        this.colaboradorService = colaboradorService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/cambiar")
    public String CambiarCliente() {
        return "colaborador/cambiar";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "colaborador/dashboard"; // templates/colaborador/dashboard.html
    }

    @PostMapping("/actividades/addAct")
    public String addActivity(@ModelAttribute("actividad") Actividad actividad,
                            @RequestParam("imagenFile") MultipartFile imagenFile,
                            Authentication authentication) {

        String email = authentication.getName();
        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));

        Colaborador colaborador = colaboradorService.obtenerPorUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado para el usuario: " + usuario.getEmail()));

        actividad.setColaborador(colaborador);

        try {
            if (!imagenFile.isEmpty()) {
                String nombreImagen = uploadFileService.copy(imagenFile);
                actividad.setImagen(nombreImagen); // guarda el nombre en la BD
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al subir la imagen", e);
        }

        actividadService.agregarActividad(actividad);

        return "redirect:/colaborador/actividades";
    }



    @GetMapping("/actividades")
    public String listar(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(required = false) String filtroNombre,
                        Authentication authentication,
                        Model model) {

        int pageSize = 10;

        // Paso 1: obtener el usuario autenticado (por email o username)
        String email = authentication.getName();
        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));

        // Paso 2: obtener el colaborador asociado al usuario
        Colaborador colaborador = colaboradorService.obtenerPorUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado para el usuario: " + usuario.getEmail()));

        Long idColaborador = colaborador.getIdColaborador();

        // Paso 3: llamar al servicio con filtros
        Page<Actividad> actividadesPage = actividadService
                .getActividadesConPaginacionDeColaborador(page, pageSize, idColaborador, filtroNombre);

        // Paso 4: pasar datos al modelo
        model.addAttribute("actividades", actividadesPage);
        model.addAttribute("actividad", new Actividad());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", actividadesPage.getTotalPages());
        model.addAttribute("filtroNombre", filtroNombre);

        return "colaborador/actividad"; // Vista Thymeleaf
    }

    // Eliminar actividad por ID
    @GetMapping("/eliminar/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        Actividad actividad = actividadService.listarById(id);
        if (actividad != null && actividad.getImagen() != null) {
            uploadFileService.delete(actividad.getImagen());
        }
        actividadService.deleteActivity(id);
        return "redirect:/colaborador/actividades";
    }




}
