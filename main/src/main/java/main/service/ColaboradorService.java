package main.service;

import main.entity.Colaborador;
import main.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface ColaboradorService {

    Colaborador registrarColaborador(Colaborador colaborador);

    List<Colaborador> findAll();

    Optional<Colaborador> obtenerPorUsuario(Usuario usuario);
}
