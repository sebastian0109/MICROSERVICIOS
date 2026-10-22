package cl.duoc.ms_cursos.service;

import cl.duoc.ms_cursos.exception.RecursoNoEncontradoException;
import cl.duoc.ms_cursos.model.Curso;
import cl.duoc.ms_cursos.model.Modulo;
import cl.duoc.ms_cursos.repository.CursoRepository;
import cl.duoc.ms_cursos.repository.ModuloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;

    public ModuloService(ModuloRepository moduloRepository, CursoRepository cursoRepository) {
        this.moduloRepository = moduloRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional(readOnly = true)
    public List<Modulo> listar() {
        return moduloRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Modulo buscarPorId(Long id) {
        return moduloRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Módulo con id " + id + " no existe"));
    }

    @Transactional
    public Modulo crear(Modulo modulo) {
        modulo.setId(null);
        modulo.setCurso(resolverCurso(modulo.getCurso()));
        return moduloRepository.save(modulo);
    }

    @Transactional
    public Modulo actualizar(Long id, Modulo datos) {
        Modulo existente = buscarPorId(id);
        existente.setTitulo(datos.getTitulo());
        existente.setOrden(datos.getOrden());
        existente.setCurso(resolverCurso(datos.getCurso()));
        return moduloRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        Modulo modulo = buscarPorId(id);
        moduloRepository.delete(modulo);
    }

    /** El curso es obligatorio; debe indicar un id que exista. */
    private Curso resolverCurso(Curso referencia) {
        if (referencia == null || referencia.getId() == null) {
            throw new IllegalArgumentException("El módulo debe indicar el id de su curso");
        }
        return cursoRepository.findById(referencia.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Curso con id " + referencia.getId() + " no existe"));
    }
}
