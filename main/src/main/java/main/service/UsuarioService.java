package main.service;

import main.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UsuarioService extends UserDetailsService {

    Optional<Usuario> findByEmail(String email);
}
