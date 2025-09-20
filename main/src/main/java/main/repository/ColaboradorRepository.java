package main.repository;

import main.entity.Colaborador;
import main.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador,Integer> {

    Optional<Colaborador> findById(Integer integer);

    Optional<Colaborador> findByUsuario(Usuario usuario);
}
