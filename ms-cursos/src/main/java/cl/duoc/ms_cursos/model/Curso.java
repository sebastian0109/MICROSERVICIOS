package cl.duoc.ms_cursos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

/**
 * Un curso agrupa muchos módulos (relación 1:N).
 * Lado "uno": @OneToMany mappedBy="curso".
 * instructorId guarda solo el id del usuario en ms-usuarios (sin FK entre microservicios).
 */
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título del curso es obligatorio")
    @Column(nullable = false, unique = true)
    private String titulo;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Double precio;

    // Referencia lógica al instructor (ms-usuarios). No es una relación JPA.
    private Long instructorId;

    // @JsonIgnore evita la recursión infinita Curso -> Modulo -> Curso al serializar.
    @JsonIgnore
    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private List<Modulo> modulos = new ArrayList<>();

    public Curso() {}

    public Curso(String titulo, String descripcion, Double precio, Long instructorId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.instructorId = instructorId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
    public List<Modulo> getModulos() { return modulos; }
    public void setModulos(List<Modulo> modulos) { this.modulos = modulos; }
}
