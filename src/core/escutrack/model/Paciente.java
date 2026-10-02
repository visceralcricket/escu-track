package core.escutrack.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
// Realizar verificaciones de parámetros recibidos
import core.escutrack.utils.ValidadorCamposUtils;

/**
 * Representa a un paciente ingresado en el hospital.
 * <p>
 * Encapsula la información clínica y administrativa del paciente: su RUT,
 * nombre, nivel de gravedad (almacenado internamente como un entero para
 * facilitar comparaciones y ordenamientos), la cama que tiene asignada y
 * sus fechas de ingreso y egreso.
 *
 * @author Felipe T.S.
 */
public class Paciente extends EntidadHospitalaria {

	private String rut;
	private String nombre;
	private int nivelGravedad;
	private String idCamaAsignada;

	private LocalDateTime fechaIngreso;
	private LocalDateTime fechaEgreso;


	/**
	 * Construye un nuevo paciente sin cama asignada ni fecha de egreso.
	 *
	 * @param rut           RUT del paciente
	 * @param nombre        nombre completo del paciente
	 * @param nivelGravedad nivel de gravedad en formato entero traducido de la forma textual ingresada por el usuario (ej. "estable" -> 1); ver {@link #TRADUCTOR_GRAVEDAD}
	 * @param fechaIngreso  fecha y hora en que el paciente ingresó al establecimiento
	 * @throws IllegalArgumentException si {@code gradoGravedad} no corresponde a un valor reconocido
	 */
	public Paciente (String rut, String nombre, int nivelGravedad,LocalDateTime fechaIngreso){
		ValidadorCamposUtils.validarRut(rut);
		ValidadorCamposUtils.validarNombre(nombre);
		ValidadorCamposUtils.validarGravedad(nivelGravedad);
		
		this.rut = rut;
		this.nombre= nombre;
		this.nivelGravedad= nivelGravedad;
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
	
	@Override
	public String evaluarEstadoOperativo() {
		if(this.nivelGravedad >= 3) {
			return "[CRÍTICO]: Requiere monitoreo activo. Gravedad: " + ValidadorCamposUtils.traducirGravedad(this.nivelGravedad);
		}
		else {
			return "[REGULAR]: Paciente estable en observación general.";
		}
	}

	/**
	 * Indica si el nivel de gravedad actual del paciente coincide con la
	 * etiqueta textual buscada. Se usa para el filtrado de pacientes por
	 * gravedad (SIA-9).
	 *
	 * @param gravedadBuscada valor numéricoa comparar
	 * @return {@code true} si coincide; {@code false} si no coincide o si {@code gravedadBuscada} no es un grado de gravedad válido
	 */
	public boolean coincideGravedad(int gravedadBuscada) {
		return (this.nivelGravedad == gravedadBuscada);
	}

	/* +++
	 * Renderizado de RUT modificado de tal forma que
	 * traduzca el valor numérico del nivel de gravedad
	 * a formato textual haciendo uso del nuevo diccionario
	 * estático.
	 *
	 * @author Felipe T.S.
	 --- */
	/**
	 * Devuelve una representación textual del paciente con su nombre,
	 * estado de gravedad (ya traducido a texto), RUT, cama asignada y
	 * fechas de ingreso/egreso.
	 *
	 * @return cadena con los detalles del paciente
	 */
	@Override
	public String toString() { //SIA 6
		
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		
		String ingresoStr = (fechaIngreso != null) ? fechaIngreso.format(formatter) : "Ninguna";
		String egresoStr = (fechaEgreso!= null) ? fechaEgreso.format(formatter) : "Ninguna (aún internado)";
		String camaStr = (idCamaAsignada != null) ? idCamaAsignada : "Ninguna";
		
	    return "Nombre: " + nombre + "\n" +
	           "Estado: " + ValidadorCamposUtils.traducirGravedad(nivelGravedad) + "\n" +
	           "RUT: " + rut + "\n" +
	           "Cama asignada: " + camaStr + "\n" +
	           "Fecha de ingreso: " + ingresoStr+ "\n" + 
	           "Fecha de egreso: " + egresoStr;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		
		Paciente otroPaciente = (Paciente) obj;
		return this.rut.equalsIgnoreCase(otroPaciente.rut);
	}
	
	@Override
	public int hashCode() {
		return rut != null ? rut.toLowerCase().hashCode() : 0;
	}
	
	/** @return el RUT del paciente */
	public String getRut() {return rut;}
	/** @param rut nuevo RUT del paciente */
	public void setRut(String rut) {
		ValidadorCamposUtils.validarRut(rut);
		this.rut = rut;
		}

	/** @return el nombre del paciente */
	public String getNombre() {return nombre;}
	/** @param nombre nuevo nombre del paciente */
	public void setNombre(String nombre) {
		ValidadorCamposUtils.validarNombre(nombre);
		this.nombre = nombre;
		}

	/** @return el nivel de gravedad del paciente, como valor entero interno */
	public int getNivelGravedad() {return nivelGravedad;}
	/**
	 * @param gradoGravedad nuevo nivel de gravedad en formato textual (ej. "critico")
	 * @throws IllegalArgumentException si la etiqueta no corresponde a ningún valor reconocido
	 */
	public void setNivelGravedad(int nivelGravedad) {
		ValidadorCamposUtils.validarGravedad(nivelGravedad);
		this.nivelGravedad = nivelGravedad;
		}

	/** @return el id de la cama asignada al paciente, o {@code null} si no tiene */
	public String getIdCamaAsignada() {return this.idCamaAsignada;}
	/** @param idCamaAsignada nuevo id de cama asignada al paciente */
	public void setIdCamaAsignada(String idCamaAsignada) {
		ValidadorCamposUtils.validarIdCama(idCamaAsignada);
		this.idCamaAsignada = idCamaAsignada;
		}

	/** @return la fecha y hora de ingreso del paciente */
	public LocalDateTime getFechaIngreso() {return fechaIngreso;}
	/** @param fechaIngreso nueva fecha y hora de ingreso del paciente */
	public void setFechaIngreso(LocalDateTime fechaIngreso) {this.fechaIngreso = fechaIngreso;}

	/** @return la fecha y hora de egreso del paciente, o {@code null} si aún no ha sido dado de alta */
	public LocalDateTime getFechaEgreso() {return fechaEgreso;}
	/** @param fechaEgreso nueva fecha y hora de egreso del paciente */
	public void setFechaEgreso(LocalDateTime fechaEgreso) {this.fechaEgreso = fechaEgreso;}


}