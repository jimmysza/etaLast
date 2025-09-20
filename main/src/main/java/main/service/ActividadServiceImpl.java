package main.service;

import main.entity.Actividad;
import main.repository.ActividadRepository;
import main.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadServiceImpl implements ActividadService {

    private final ActividadRepository actividadRepository;

    @Autowired
    public ActividadServiceImpl(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    @Override
    public Page<Actividad> getActividadesWithPaginationMain(int page, int size, String filtroNombre) {
        Pageable pageable = PageRequest.of(page, size);

        if (filtroNombre != null && !filtroNombre.isEmpty()) {
            return actividadRepository.findByTituloContainingIgnoreCase(filtroNombre, pageable);
        }
        return actividadRepository.findAll(pageable);
    }

    @Override
    public Page<Actividad> getActividadesConPaginacionDeColaborador(int page, int size, Long idColaborador, String filtroTitulo) {
        Pageable pageable = PageRequest.of(page, size);

        if (idColaborador != null && filtroTitulo != null && !filtroTitulo.isEmpty()) {
            return actividadRepository.findByColaborador_IdColaboradorAndTituloContainingIgnoreCase(idColaborador, filtroTitulo, pageable);
        }

        if (idColaborador != null) {
            return actividadRepository.findByColaborador_IdColaborador(idColaborador, pageable);
        }

        if (filtroTitulo != null && !filtroTitulo.isEmpty()) {
            return actividadRepository.findByTituloContainingIgnoreCase(filtroTitulo, pageable);
        }

        return actividadRepository.findAll(pageable);
    }

    @Override
    public Page<Actividad> getActividadesWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return actividadRepository.findAll(pageable);
    }

    @Override
    public List<Actividad> listarActividades() {
        return actividadRepository.findAll();
    }

    @Override
    public Actividad agregarActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public void deleteActivity(long id) {
        actividadRepository.deleteById(id);
    }

    @Override
    public Actividad listarById(Long id) {
        return actividadRepository.findById(id).orElse(null);
    }
}
