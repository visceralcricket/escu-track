package core.escutrack.model;

/*
 * Clase abstracta que agrupa a las entidades del hospital,
 * permitiendo aplicar polimorfismo en las reglas de negocio.
 */

public abstract class EntidadHospitalaria {
	
	/*
	 * método abstracto -> evalúa la situación de la entidad respecto
	 * a su urgencia o estado operativo dentro de la red del hospital.
	 */
	public abstract String evaluarEstadoOperativo();
	
}
