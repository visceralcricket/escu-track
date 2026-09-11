package core.escutrack.exceptions;

/**
 * Excepción personalizada lanzada cuando se busca una entidad del sistema
 * (Departamento, Cama o Paciente) que no existe dentro de las estructuras
 * de datos del {@link core.escutrack.controller.ControladorHospital}.
 *
 * @author Felipe T.S.
 * @since v0.1.3
 */
public class EntidadNoEncontradaException extends Exception {

	/**
	 * Construye la excepción con un mensaje descriptivo del error.
	 *
	 * @param msg mensaje que explica qué entidad no fue encontrada
	 */
	public EntidadNoEncontradaException(String msg) {
		super(msg);
	}
}
