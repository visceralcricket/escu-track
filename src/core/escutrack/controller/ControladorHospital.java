package core.escutrack.controller;

// Entidades
import core.escutrack.model.Paciente;
import core.escutrack.utils.GestorPersistencia;
import core.escutrack.model.Cama;
// Custom exceptions
import core.escutrack.exceptions.CamaOcupadaException;
import core.escutrack.exceptions.EntidadNoEncontradaException;

import java.io.IOException;
// Utilidades
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
// Necesario para cargar base de datos de archivo CSV
import core.escutrack.utils.GestorPersistencia;

/* +++
 * Este código de aquí busca enlazar la entidad Paciente con una metodología de
 * front-end para que sea más fácil para el usuario interactuar con el programa y
 * hacer distintas operaciones, haciendo uso de dos Mapas para llevar registro
 * de todos los pacientes de acuerdo al departamento en el que se encuentran
 * presentes dentro del hospital.
 * 
 * Seguidamente, hay que incluir 3 datos iniciales en el código, los cuales
 * deben permitir la ejecución de cualquiera de las funcionalidades implementadas,
 * es decir, casos de prueba incluidos.
 * 
 * @author Felipe T.S * 
 --- */

public class ControladorHospital {
	
	private Map<String, Map<String, Cama>> mapaDepartamentos;
	
	public ControladorHospital() {
		this.mapaDepartamentos = new HashMap<>(); // TDA inicializado
		GestorPersistencia.cargarDatos(this.mapaDepartamentos);
		if(this.mapaDepartamentos.isEmpty()) {
			inicializarDatos(); // Crea mapa por defecto si NO existe archivo CSV
		}
	}
	
	private void inicializarDatos() {
		Map<String, Cama> camasUci = new HashMap<>();
		
		Cama cama1 = new Cama("A-01", 1);
		Cama cama2 = new Cama("A-02", 2);
		
		camasUci.put(cama1.getIdCama(), cama1);
		camasUci.put(cama2.getIdCama(), cama2);
		
		this.mapaDepartamentos.put("UCI", camasUci);
	}
	
	public void registrarPaciente(String rut, String nombre, String gradoGravedad, String departamento, String idCama, LocalDateTime fechaIngreso) throws EntidadNoEncontradaException, CamaOcupadaException
	{
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		if (camasDelDepartamento == null) throw new EntidadNoEncontradaException("Departamento inexistente.");

		Cama cama = camasDelDepartamento.get(idCama);
		if (cama == null) throw new EntidadNoEncontradaException("ID de cama no válido.");

		if (!cama.isDisponible()) throw new CamaOcupadaException("La cama '" + idCama + "' ya está ocupada.");
		

		Paciente nuevoPaciente = new Paciente(rut, nombre, gradoGravedad, fechaIngreso);
		nuevoPaciente.setIdCamaAsignada(idCama);

		cama.setPaciente(nuevoPaciente); // ya deja disponible = false internamente
	}
	
	public void registrarPaciente(String rut, String nombre, String gradoGravedad, String departamento, String idCama) throws EntidadNoEncontradaException, CamaOcupadaException { // SIA 5
	    registrarPaciente(rut, nombre, gradoGravedad, departamento, idCama, LocalDateTime.now());
	}
		
	public void eliminarPaciente(String idCama, String departamento) throws EntidadNoEncontradaException, CamaOcupadaException {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		if(camasDelDepartamento == null) throw new EntidadNoEncontradaException("Departamento inexistente.");
		
		Cama cama = camasDelDepartamento.get(idCama);
		if(cama == null) throw new EntidadNoEncontradaException("ID de Cama no válido.");
		// No se puede eliminar paciente de cama que está vacía
		if(cama.isDisponible()) throw new EntidadNoEncontradaException("La cama ya está vacía.");

		cama.setPaciente(); // versión sin argumentos: limpia paciente y marca disponible = true
	}
	
	/* +++
	 * mostrarPaciente modificado para que retorne un String en vez de simplemente
	 * imprimir Paciente: delegar tarea de renderizado y visualización al front-end.
	 * 
	 * @author Felipe T.S.
	 --- */

	public String mostrarPaciente(String idCama, String departamento) throws EntidadNoEncontradaException, CamaOcupadaException {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		if(camasDelDepartamento == null) throw new EntidadNoEncontradaException("Departamento inexistente.");
		
		Cama cama = camasDelDepartamento.get(idCama);
		if(cama == null) throw new EntidadNoEncontradaException("ID de Cama no válido.");
	
		Paciente paciente = cama.getPaciente();
		if(paciente == null) throw new EntidadNoEncontradaException("Cama sin paciente asignado.");
		
		return paciente.toString(); // Retornar String
	}
	
	public void modificarGravedadPaciente(String idCama, String departamento, String nuevaGravedad) throws EntidadNoEncontradaException, CamaOcupadaException {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		if(camasDelDepartamento == null) throw new EntidadNoEncontradaException("Departamento inexistente.");
		
		Cama cama = camasDelDepartamento.get(idCama);
		if(cama == null) throw new IllegalArgumentException("ID de Cama no válido.");
		if(cama.isDisponible()) throw new IllegalStateException("La cama está vacía, no hay paciente que modificar.");
		
		cama.getPaciente().setNivelGravedad(nuevaGravedad);
	}
	
	/* +++
	 * Método enfocado en captar posible error de guardado a CSV y
	 * dirigido principalmente a ser utilizado en el flujo principal
	 * del programa en Main.
	 * 
	 * @author Felipe T.S.
	 --- */
	public void apagarSistema() throws IOException {
		GestorPersistencia.guardarDatos(this.mapaDepartamentos);
	}
}