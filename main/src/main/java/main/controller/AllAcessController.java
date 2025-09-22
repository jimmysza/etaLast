package main.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.entity.Actividad;
import main.entity.Usuario;
import main.service.ActividadService;
import main.service.UsuarioService;

@Controller
public class AllAcessController {

    private final UsuarioService usuarioService;
    private final ActividadService actividadService;

    public AllAcessController(UsuarioService usuarioService, ActividadService actividadService) {
        this.usuarioService = usuarioService;
        this.actividadService = actividadService;
    }

    // Página de login
    @GetMapping("/login")
    public String iniciarSesion(@RequestParam(value = "role", required = false) String role, Model model) {
        model.addAttribute("role", role);
        return "auth/login"; // vista login.html}
    }

    @GetMapping("/")
    public String landingPage(Model model, Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String nombre) {

        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {

            String email = auth.getName();
            Usuario usuario = usuarioService.findByEmail(email).orElse(null);

            if (usuario != null) {
                model.addAttribute("nombreUsuario", usuario.getNombre());
            } else {
                model.addAttribute("nombreUsuario", email);
            }

            // ✅ Obtener el rol del usuario
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            String role = authorities.stream()
                    .map(GrantedAuthority::getAuthority) // "ROLE_CLIENTE", "ROLE_ADMIN", etc.
                    .findFirst()
                    .orElse("ROLE_ANONYMOUS"); // fallback

            model.addAttribute("userRole", role); // <-- ¡Listo! Lo puedes usar en Thymeleaf con ${userRole}

        } else {
            model.addAttribute("nombreUsuario", null);
            model.addAttribute("userRole", "ROLE_ANONYMOUS");
        }

        // ... el resto de tu código (paginación, etc.)
        int pageSize = 6;
        Page<Actividad> actividadesPage = actividadService.getActividadesWithPaginationMain(page, pageSize, nombre);
        model.addAttribute("actividades", actividadesPage);
        model.addAttribute("actividad", new Actividad());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", actividadesPage.getTotalPages());
        model.addAttribute("filtroNombre", nombre);

        List<Integer> pageNumbers = IntStream.range(0, actividadesPage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);

        return "main";
    }

    // Página de inicio
    @GetMapping("/index")
    public String verPaginaDeInicio(Model model) {
        /* model.addAttribute("usuarios", usuarioService.listarUsuarios()); */

        return "admin/index";
    }

}