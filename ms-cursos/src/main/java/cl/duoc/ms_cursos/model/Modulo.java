package cl.duoc.ms_cursos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Un módulo pertenece a un curso (relación N:1).
 * Lado "muchos": @ManyToOne + @JoinColumn (clave foránea curso_id).
 */
@Entity
@Table(name = "modulos")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título del módulo es obligatorio")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "El orden es obligatorio")
    @Positive(message = "El orden debe ser mayor que 0")
    @Column(nullable = false)
    private Integer orden;

    @NotNull(message = "El módulo debe pertenecer a un curso")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    public Modulo() {}

    public Modulo(String titulo, Integer orden) {
        this.titulo = titulo;
        this.orden = orden;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}
