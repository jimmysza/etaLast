package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import main.entity.Cliente;
import main.entity.Colaborador;
import main.entity.Usuario;
import main.service.ClienteService;
import main.service.ColaboradorService;
import main.service.UsuarioService;

@Controller
@RequestMapping("/registro")
public class RegistroController {



    @Autowired
    private final  UsuarioService usuarioService;
    private final  ClienteService clienteService;
    private final ColaboradorService colaboradorService;
    RegistroController(UsuarioService usuarioService,ClienteService clienteService,ColaboradorService colaboradorService){
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/cliente")
    public String mostrarRegistroCliente(Model model) {
        Cliente cliente = new Cliente();
        cliente.setUsuario(new Usuario());
        model.addAttribute("cliente", cliente);
        model.addAttribute("role", "Cliente");
        return "Auth/registroCliente";
    }


    @PostMapping("/cliente")
    public String registrarCliente(@ModelAttribute("cliente") Cliente cliente,
                                RedirectAttributes redirectAttributes) {
        clienteService.registrarCliente(cliente);
        redirectAttributes.addFlashAttribute("exito", "Cliente registrado con éxito.");
        return "redirect:/login?role=colaborador";
    }


    @GetMapping("/colaborador")
    public String mostrarRegistroColaborador(Model model) {
        Colaborador colaborador = new Colaborador();
        colaborador.setUsuario(new Usuario());
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("role", "Colaborador");
        return "Auth/registroColaborador";
    }


    @PostMapping("/colaborador")
    public String registrarColaborador(@ModelAttribute("colaborador") Colaborador colaborador,
                                RedirectAttributes redirectAttributes) {
        colaboradorService.registrarColaborador(colaborador);
        redirectAttributes.addFlashAttribute("exito", "Cliente registrado con éxito.");
        redirectAttributes.addFlashAttribute("exito", "Cliente registrado con éxito.");
        return "redirect:/login?role=colaborador";
    }




}
