package main.config;

import main.entity.Rol;
import main.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    public DataInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Roles que queremos inicializar
        String[] roles = {"ROLE_CLIENTE", "ROLE_COLABORADOR", "ROLE_ADMIN"};

        for (String nombreRol : roles) {
            Optional<Rol> rolExistente = rolRepository.findByNombre(nombreRol);
            if (rolExistente.isEmpty()) {
                Rol rol = new Rol();
                rol.setNombre(nombreRol);
                rolRepository.save(rol);
                System.out.println("Rol creado: " + nombreRol);
            }
        }

        System.out.println("Inicialización de roles completada");
    }
}
