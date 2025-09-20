package main.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.entity.Cliente;
import main.entity.Rol;
import main.entity.Usuario;
import main.repository.ClienteRepository;
import main.repository.RolRepository;
import main.repository.UsuarioRepository;

@Service
@Transactional // Importante: añadir transaccional a nivel de clase
public class ClienteServiceImpl implements ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(UsuarioRepository usuarioRepository,
                            RolRepository rolRepository,
                            PasswordEncoder passwordEncoder,
                            ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional // Asegurar que el método sea transaccional
    public Cliente registrarCliente(Cliente cliente) {
        Usuario usuario = cliente.getUsuario();

        // 🔍 Validar email único
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado: " + usuario.getEmail());
        }

        // 🔒 Validar y encriptar contraseña
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 👤 Asignar rol CLIENTE
        Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));
        usuario.setRoles(Set.of(rolCliente));

        // 💾 SOLUCIÓN: Guardar primero el usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        
        // Asignar el usuario guardado al cliente
        cliente.setUsuario(usuarioGuardado);
        
        // Ahora guardar el cliente
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
}