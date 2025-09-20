package main.service;

import java.util.List;

import org.springframework.data.domain.Page;

import main.entity.Actividad;

public interface ActividadService {

    Page<Actividad> getActividadesWithPaginationMain(int page, int size, String filtroNombre);

    Page<Actividad> getActividadesConPaginacionDeColaborador(int page, int size, Long idColaborador, String filtroTitulo);

    Page<Actividad> getActividadesWithPagination(int page, int size);

    List<Actividad> listarActividades();

    Actividad agregarActividad(Actividad actividad);

    void deleteActivity(long id);

    Actividad listarById(Long id);
}
