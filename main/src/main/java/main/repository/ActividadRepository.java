package main.repository;

import main.entity.Actividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    Page<Actividad> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    Page<Actividad> findByColaborador_IdColaborador(Long idColaborador, Pageable pageable);

    Page<Actividad> findByColaborador_IdColaboradorAndTituloContainingIgnoreCase(Long idColaborador, String titulo, Pageable pageable);
}

