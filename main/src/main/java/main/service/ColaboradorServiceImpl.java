package main.service;

import main.entity.Colaborador;
import main.entity.Usuario;
import main.repository.ColaboradorRepository;
import main.repository.RolRepository;
import main.repository.UsuarioRepository;
import main.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    public ColaboradorServiceImpl(UsuarioRepository usuarioRepository,
                                RolRepository rolRepository,
                                PasswordEncoder passwordEncoder,
                                ColaboradorRepository colaboradorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public Colaborador registrarColaborador(Colaborador colaborador) {
        Usuario usuario = colaborador.getUsuario();

        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        rolRepository.findById(2L).ifPresent(rol -> usuario.setRoles(Set.of(rol)));

        usuarioRepository.save(usuario);

        return colaboradorRepository.save(colaborador);
    }

    @Override
    public List<Colaborador> findAll() {
        return colaboradorRepository.findAll();
    }

    @Override
    public Optional<Colaborador> obtenerPorUsuario(Usuario usuario) {
        return colaboradorRepository.findByUsuario(usuario);
    }
}
