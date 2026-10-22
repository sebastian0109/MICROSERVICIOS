package cl.duoc.ms_cursos.repository;

import cl.duoc.ms_cursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    boolean existsByTitulo(String titulo);

    boolean existsByTituloAndIdNot(String titulo, Long id);
}
