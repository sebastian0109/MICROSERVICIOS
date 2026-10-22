package cl.duoc.ms_cursos.repository;

import cl.duoc.ms_cursos.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    boolean existsByCursoId(Long cursoId);

    List<Modulo> findByCursoIdOrderByOrdenAsc(Long cursoId);
}
