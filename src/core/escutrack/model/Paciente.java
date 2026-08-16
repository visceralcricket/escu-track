package core.escutrack.model;

import java.util.Map;

import java.time.LocalDateTime;
  
 

/* +++
 * Este código de aquí contiene la entidad Paciente, su constructor y distintas funcionalidades
 * de getter y setter a cada uno de sus parámetros. Esto se relaciona después con el archivo de
 * ControladorHospital para mantener los parámetros de Paciente privados y seguros dentro de
 * este archivo.
 * @author Felipe T.S
 * 
 * 
 --- */

public class Paciente {
	
	private String rut;
	private String nombre;
	private int nivelGravedad;
	private String idCamaAsignada;
	
	LocalDateTime fechaIngreso;
	LocalDateTime fechaEgreso;
	 
	
	public Paciente (String rut, String nombre, String gradoGravedad){
		this.rut = rut;
		this.nombre= nombre;
		this.nivelGravedad= parseGradoGravedad(gradoGravedad);
		this.idCamaAsignada= null;
	}
	
	/* +++
	 * @author Felipe T.S.
	 * Aquí hacemos uso de una tabla hash incluida en la librería de util.Map de Java
	 * para poder asignar los niveles de gravedad con la menor complejidad temporal
	 * posible haciendo uso de sus etiquetas cualitativas como keys.
	 *
	 * Como punto extra, considerar añadir una variable de tipo date para almacenar
	 * la fecha y ahora a la que el paciente ingresó al establecimiento, cuándo se
	 * dió de alta y cuándo quedó disponible la cama que se le fue asignada.
	 * 
	 * AGREGADO por Jonathan 
	 --- */
	private static final Map<String, Integer> ESTADO_CLINICO = Map.of("indefinido",0, "estable",1, "moderado",2, "urgente",3, "severo",4, "critico",5);
	
	private static int parseGradoGravedad(String gradoGravedad) {
		if(gradoGravedad == null) return 0;
		
		Integer nivelGravedad = ESTADO_CLINICO.get(gradoGravedad.trim().toLowerCase()); 
		if(nivelGravedad == null) {
			throw new IllegalArgumentException("Nivel de gravedad desconocido: " + nivelGravedad);
		}
		return nivelGravedad;
	}
	
	
	public String getRut() {return rut;}
	public void setRut(String rut) {this.rut = rut;}
	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	
	public int getNivelGravedad() {return nivelGravedad;}
	public void setNivelGravedad(String gradoGravedad) {this.nivelGravedad = parseGradoGravedad(gradoGravedad);}
	
	public String getIdCamaAsignada() {return this.idCamaAsignada;}
	public void setIdCamaAsignada(String idCamaAsignada) {this.idCamaAsignada = idCamaAsignada;}
	
	public LocalDateTime getFechaIngreso() {return fechaIngreso;}
	public void setFechaIngreso(LocalDateTime fechaIngreso) {this.fechaIngreso = fechaIngreso;}
	
	public LocalDateTime getFechaEgreso() {return fechaEgreso;}
	public void setFechaEgreso(LocalDateTime fechaEgreso) {this.fechaEgreso = fechaEgreso;}
	
	
}