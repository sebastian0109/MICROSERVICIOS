package cl.duoc.ms_cursos.service;

import cl.duoc.ms_cursos.exception.ConflictoException;
import cl.duoc.ms_cursos.exception.RecursoNoEncontradoException;
import cl.duoc.ms_cursos.model.Curso;
import cl.duoc.ms_cursos.model.Modulo;
import cl.duoc.ms_cursos.repository.CursoRepository;
import cl.duoc.ms_cursos.repository.ModuloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final ModuloRepository moduloRepository;

    // Inyección de dependencias por constructor
    public CursoService(CursoRepository cursoRepository, ModuloRepository moduloRepository) {
        this.cursoRepository = cursoRepository;
        this.moduloRepository = moduloRepository;
    }

    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso con id " + id + " no existe"));
    }

    /** Recorre la relación OneToMany: los módulos de un curso, ordenados. */
    @Transactional(readOnly = true)
    public List<Modulo> listarModulos(Long id) {
        buscarPorId(id);
        return moduloRepository.findByCursoIdOrderByOrdenAsc(id);
    }

    @Transactional
    public Curso crear(Curso curso) {
        if (cursoRepository.existsByTitulo(curso.getTitulo())) {
            throw new ConflictoException("Ya existe un curso con el título '" + curso.getTitulo() + "'");
        }
        curso.setId(null);
        return cursoRepository.save(curso);
    }

    @Transactional
    public Curso actualizar(Long id, Curso datos) {
        Curso existente = buscarPorId(id);
        if (cursoRepository.existsByTituloAndIdNot(datos.getTitulo(), id)) {
            throw new ConflictoException("Ya existe otro curso con el título '" + datos.getTitulo() + "'");
        }
        existente.setTitulo(datos.getTitulo());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        existente.setInstructorId(datos.getInstructorId());
        return cursoRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        Curso curso = buscarPorId(id);
        if (moduloRepository.existsByCursoId(id)) {
            throw new ConflictoException("No se puede eliminar el curso: tiene módulos asociados");
        }
        cursoRepository.delete(curso);
    }
}
