package cl.duoc.ms_cursos.controller;

import cl.duoc.ms_cursos.model.Curso;
import cl.duoc.ms_cursos.model.Modulo;
import cl.duoc.ms_cursos.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> obtenerTodos() {
        return cursoService.listar();
    }

    @GetMapping("/{id}")
    public Curso obtenerPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id);
    }

    @GetMapping("/{id}/modulos")
    public List<Modulo> obtenerModulos(@PathVariable Long id) {
        return cursoService.listarModulos(id);
    }

    @PostMapping
    public ResponseEntity<Curso> crear(@Valid @RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.crear(curso));
    }

    @PutMapping("/{id}")
    public Curso actualizar(@PathVariable Long id, @Valid @RequestBody Curso curso) {
        return cursoService.actualizar(id, curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
