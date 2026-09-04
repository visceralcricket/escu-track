package core.escutrack.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class Paciente {
	
    // Diccionario compacto
	public static final String[] TRADUCTOR_GRAVEDAD = {
		"indefinido", // índice 0
		"estable",
		"moderado",
		"urgente",
		"severo",
		"crítico"
	};
	
	private String rut;
	private String nombre;
	private int nivelGravedad;
	private String idCamaAsignada;
	
	private LocalDateTime fechaIngreso;
	private LocalDateTime fechaEgreso;
	 
	
	public Paciente (String rut, String nombre, String gradoGravedad,LocalDateTime fechaIngreso){
		this.rut = rut;
		this.nombre= nombre;
		this.nivelGravedad= parseGradoGravedad(gradoGravedad);
		this.idCamaAsignada= null;
		this.fechaIngreso = fechaIngreso;
		this.fechaEgreso = null;
	}
	
	/* +++
	 * @author Felipe T.S.
	 * Aquí hacemos uso de una tabla hash incluida en la librería de util.Map de Java
	 * para poder asignar los niveles de gravedad con la menor complejidad temporal
	 * posible haciendo uso de sus etiquetas cualitativas como keys.
	 --- */
	
	private static final Map<String, Integer> ESTADO_CLINICO;
	
	static {
		Map<String, Integer> map = new HashMap<>();
		map.put("indefinido",0);
		map.put("estable",1);
		map.put("moderado",2);
		map.put("urgente",3);
		map.put("severo",4);
		map.put("critico",5);
		ESTADO_CLINICO = Collections.unmodifiableMap(map);
	}
	
	private static int parseGradoGravedad(String gradoGravedad) {
		if(gradoGravedad == null) return 0;
		
		Integer nivelGravedad = ESTADO_CLINICO.get(gradoGravedad.trim().toLowerCase()); 
		if(nivelGravedad == null) {
			throw new IllegalArgumentException("Nivel de gravedad desconocido: " + nivelGravedad);
		}
		return nivelGravedad;
	}
	
	/* +++
	 * Renderizado de RUT modificado de tal forma que
	 * traduzca el valor numérico del nivel de gravedad
	 * a formato textual haciendo uso del nuevo diccionario
	 * estático.
	 * 
	 * @author Felipe T.S.
	 --- */
	@Override
	public String toString() { //SIA 6	
	    return "Nombre: " + nombre + "\n" +
	           "Estado: " + TRADUCTOR_GRAVEDAD[nivelGravedad] + "\n" +
	           "RUT: " + rut + "\n" +
	           "Cama asignada: " + idCamaAsignada + "\n" +
	           "Fecha de ingreso" + fechaIngreso + "\n" + 
	           "Fecha de egreso" + fechaEgreso;
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