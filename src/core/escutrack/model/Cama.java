package core.escutrack.model;


public class Cama {
	private String idCama;
	private boolean disponible;
	private Paciente pacienteActual;
	private int prioridad;

	public Cama(String idCama, boolean disponible, Paciente pacienteActual, int prioridad)
	{
		this.idCama = idCama;
		this.disponible = disponible;
		this.pacienteActual = pacienteActual;
		this.prioridad = prioridad;
		
	}
	
	public Cama(String idCama, int prioridad) { 	
	    this(idCama, true, null, prioridad); 
	}


	@Override
	public String toString() // SIA 6
	{
		return "Id de la cama:" + this.idCama +
				"\nEstado:" + this.disponible +
				"\nPaciente en la cama actual:\n" + this.pacienteActual;
	}


	public void setPaciente(Paciente pacienteActual)
	{
		this.pacienteActual = pacienteActual;
		this.disponible = false;
	}

	public void setPaciente() //SIA 5
	{
		this.pacienteActual = null;
		this.disponible = true;
	}

	public Paciente getPaciente() {return this.pacienteActual;}

	public String getIdCama() {return idCama;}
	public void setIdCama(String idCama) {this.idCama = idCama;}

	public boolean isDisponible() {return disponible;}
	public void setDisponible(boolean disponible) {this.disponible = disponible;}

	public int getPrioridad() {return prioridad;}
	public void setPrioridad(int prioridad) {this.prioridad = prioridad;}
}