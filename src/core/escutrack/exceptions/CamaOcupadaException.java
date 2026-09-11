package core.escutrack.exceptions;

/**
 * Excepción personalizada lanzada cuando se intenta realizar una operación
 * sobre una {@link core.escutrack.model.Cama} que ya se encuentra ocupada
 * por un paciente (por ejemplo, al intentar registrar un nuevo paciente en
 * una cama que ya tiene uno asignado).
 *
 * @author Felipe T.S.
 * @since v0.1.3
 */
public class CamaOcupadaException extends Exception {

	/**
	 * Construye la excepción con un mensaje descriptivo del error.
	 *
	 * @param msg mensaje que explica por qué la cama no está disponible
	 */
	public CamaOcupadaException(String msg) {
		super(msg);
	}
}
