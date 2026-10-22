package cl.duoc.ms_cursos.exception;

/** Se lanza cuando la operación choca con el estado actual de los datos (responde 409). */
public class ConflictoException extends RuntimeException {

    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
