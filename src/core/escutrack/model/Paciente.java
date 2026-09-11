package core.escutrack.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

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
public class Paciente {

    /**
     * Diccionario compacto que traduce el nivel de gravedad numérico
     * interno (usado como índice) a su representación textual.
     */
	public static final String[] TRADUCTOR_GRAVEDAD = {
		"indefinido", // índice 0
		"estable",
		"moderado",
		"urgente",
		"severo",
		"critico"
	};

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
	 * @param gradoGravedad nivel de gravedad en formato textual (ej. "estable"); ver {@link #TRADUCTOR_GRAVEDAD}
	 * @param fechaIngreso  fecha y hora en que el paciente ingresó al establecimiento
	 * @throws IllegalArgumentException si {@code gradoGravedad} no corresponde a un valor reconocido
	 */
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

	/**
	 * Tabla hash inmutable que asigna cada etiqueta textual de gravedad
	 * (en minúsculas) a su valor entero interno.
	 */
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

	/**
	 * Convierte una etiqueta textual de gravedad (ej. "Estable") a su
	 * valor entero interno, sin distinguir mayúsculas/minúsculas ni
	 * espacios en los extremos.
	 *
	 * @param gradoGravedad etiqueta textual de gravedad, o {@code null}
	 * @return el nivel de gravedad numérico correspondiente ({@code 0} si {@code gradoGravedad} es {@code null})
	 * @throws IllegalArgumentException si la etiqueta no corresponde a ningún valor reconocido
	 */
	private static int parseGradoGravedad(String gradoGravedad) {
		if(gradoGravedad == null) return 0;

		Integer nivelGravedad = ESTADO_CLINICO.get(gradoGravedad.trim().toLowerCase());
		if(nivelGravedad == null) {
			throw new IllegalArgumentException("Nivel de gravedad desconocido: " + nivelGravedad);
		}
		return nivelGravedad;
	}

	/**
	 * Indica si el nivel de gravedad actual del paciente coincide con la
	 * etiqueta textual buscada. Se usa para el filtrado de pacientes por
	 * gravedad (SIA-9).
	 *
	 * @param gravedadBuscada etiqueta textual de gravedad a comparar
	 * @return {@code true} si coincide; {@code false} si no coincide o si {@code gravedadBuscada} no es una etiqueta válida
	 */
	public boolean coincideGravedad(String gravedadBuscada) {
	    try {
	        // Usa su propio método privado internamente
	        return this.nivelGravedad == parseGradoGravedad(gravedadBuscada);
	    } catch (IllegalArgumentException e) {
	        // Si el usuario busca un término inválido (ej. "hola"), simplemente no coincide
	        return false;
	    }
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
	 * @return cadena con el detalle del paciente
	 */
	@Override
	public String toString() { //SIA 6
	    return "Nombre: " + nombre + "\n" +
	           "Estado: " + TRADUCTOR_GRAVEDAD[nivelGravedad] + "\n" +
	           "RUT: " + rut + "\n" +
	           "Cama asignada: " + idCamaAsignada + "\n" +
	           "Fecha de ingreso: " + fechaIngreso + "\n" + 
	           "Fecha de egreso: " + fechaEgreso;
	}

	/** @return el RUT del paciente */
	public String getRut() {return rut;}
	/** @param rut nuevo RUT del paciente */
	public void setRut(String rut) {this.rut = rut;}

	/** @return el nombre del paciente */
	public String getNombre() {return nombre;}
	/** @param nombre nuevo nombre del paciente */
	public void setNombre(String nombre) {this.nombre = nombre;}

	/** @return el nivel de gravedad del paciente, como valor entero interno */
	public int getNivelGravedad() {return nivelGravedad;}
	/**
	 * @param gradoGravedad nuevo nivel de gravedad en formato textual (ej. "critico")
	 * @throws IllegalArgumentException si la etiqueta no corresponde a ningún valor reconocido
	 */
	public void setNivelGravedad(String gradoGravedad) {this.nivelGravedad = parseGradoGravedad(gradoGravedad);}

	/** @return el id de la cama asignada al paciente, o {@code null} si no tiene */
	public String getIdCamaAsignada() {return this.idCamaAsignada;}
	/** @param idCamaAsignada nuevo id de cama asignada al paciente */
	public void setIdCamaAsignada(String idCamaAsignada) {this.idCamaAsignada = idCamaAsignada;}

	/** @return la fecha y hora de ingreso del paciente */
	public LocalDateTime getFechaIngreso() {return fechaIngreso;}
	/** @param fechaIngreso nueva fecha y hora de ingreso del paciente */
	public void setFechaIngreso(LocalDateTime fechaIngreso) {this.fechaIngreso = fechaIngreso;}

	/** @return la fecha y hora de egreso del paciente, o {@code null} si aún no ha sido dado de alta */
	public LocalDateTime getFechaEgreso() {return fechaEgreso;}
	/** @param fechaEgreso nueva fecha y hora de egreso del paciente */
	public void setFechaEgreso(LocalDateTime fechaEgreso) {this.fechaEgreso = fechaEgreso;}


}