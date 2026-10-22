package cl.duoc.ms_cursos.controller;

import cl.duoc.ms_cursos.model.Modulo;
import cl.duoc.ms_cursos.service.ModuloService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @GetMapping
    public List<Modulo> obtenerTodos() {
        return moduloService.listar();
    }

    @GetMapping("/{id}")
    public Modulo obtenerPorId(@PathVariable Long id) {
        return moduloService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Modulo> crear(@Valid @RequestBody Modulo modulo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moduloService.crear(modulo));
    }

    @PutMapping("/{id}")
    public Modulo actualizar(@PathVariable Long id, @Valid @RequestBody Modulo modulo) {
        return moduloService.actualizar(id, modulo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        moduloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
