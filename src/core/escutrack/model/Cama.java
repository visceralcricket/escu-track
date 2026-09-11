package core.escutrack.model;


/**
 * Representa una cama de hospital dentro de un departamento.
 * <p>
 * Una cama tiene un identificador único dentro de su departamento, una
 * prioridad numérica, un estado de disponibilidad y, opcionalmente, un
 * {@link Paciente} asignado. Cuando una cama pasa a estar ocupada, su
 * disponibilidad se actualiza automáticamente a través de
 * {@link #setPaciente(Paciente)} y {@link #setPaciente()}.
 *
 * @author Felipe T.S.
 */
public class Cama {
	private String idCama;
	private boolean disponible;
	private Paciente pacienteActual;
	private int prioridad;

	/**
	 * Construye una cama especificando explícitamente todos sus atributos.
	 *
	 * @param idCama         identificador único de la cama dentro del departamento
	 * @param disponible     {@code true} si la cama está libre, {@code false} si está ocupada
	 * @param pacienteActual paciente asignado actualmente a la cama, o {@code null} si está libre
	 * @param prioridad      nivel de prioridad de la cama (mayor valor, mayor prioridad)
	 */
	public Cama(String idCama, boolean disponible, Paciente pacienteActual, int prioridad)
	{
		this.idCama = idCama;
		this.disponible = disponible;
		this.pacienteActual = pacienteActual;
		this.prioridad = prioridad;

	}

	/**
	 * Construye una cama nueva y vacía (disponible, sin paciente asignado).
	 *
	 * @param idCama    identificador único de la cama dentro del departamento
	 * @param prioridad nivel de prioridad de la cama
	 */
	public Cama(String idCama, int prioridad) {
	    this(idCama, true, null, prioridad);
	}


	/**
	 * Devuelve una representación textual de la cama, incluyendo su id,
	 * estado de disponibilidad y el paciente asignado (si existe).
	 *
	 * @return cadena con el detalle de la cama
	 */
	@Override
	public String toString() // SIA 6
	{
		return "Id de la cama:" + this.idCama +
				"\nEstado:" + this.disponible +
				"\nPaciente en la cama actual:\n" + this.pacienteActual;
	}


	/**
	 * Asigna un paciente a la cama y la marca automáticamente como ocupada.
	 *
	 * @param pacienteActual paciente que ocupará la cama
	 */
	public void setPaciente(Paciente pacienteActual)
	{
		this.pacienteActual = pacienteActual;
		this.disponible = false;
	}

	/**
	 * Sobrecarga (SIA-5) que limpia el paciente asignado a la cama y la
	 * marca automáticamente como disponible. Se utiliza al dar de alta a
	 * un paciente.
	 */
	public void setPaciente() //SIA 5
	{
		this.pacienteActual = null;
		this.disponible = true;
	}

	/**
	 * @return el paciente actualmente asignado a la cama, o {@code null} si está libre
	 */
	public Paciente getPaciente() {return this.pacienteActual;}

	/** @return el identificador de la cama */
	public String getIdCama() {return idCama;}
	/** @param idCama nuevo identificador de la cama */
	public void setIdCama(String idCama) {this.idCama = idCama;}

	/** @return {@code true} si la cama está libre, {@code false} si está ocupada */
	public boolean isDisponible() {return disponible;}
	/** @param disponible nuevo estado de disponibilidad de la cama */
	public void setDisponible(boolean disponible) {this.disponible = disponible;}

	/** @return el nivel de prioridad de la cama */
	public int getPrioridad() {return prioridad;}
	/** @param prioridad nuevo nivel de prioridad de la cama */
	public void setPrioridad(int prioridad) {this.prioridad = prioridad;}
}