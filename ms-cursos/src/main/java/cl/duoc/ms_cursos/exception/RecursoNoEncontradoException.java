package cl.duoc.ms_cursos.exception;

/** Se lanza cuando un recurso buscado por id no existe (responde 404). */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
