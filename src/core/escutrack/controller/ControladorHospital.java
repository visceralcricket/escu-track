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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// Necesario para cargar base de datos de archivo CSV
import core.escutrack.utils.GestorPersistencia;

/* +++
 * @apiNote
 * 
 * Este módulo busca enlazar la entidad Paciente con una metodología de
 * front-end para que sea más fácil para el usuario interactuar con el programa y
 * hacer distintas operaciones, haciendo uso de dos Mapas para llevar registro
 * de todos los pacientes de acuerdo al departamento en el que se encuentran
 * presentes dentro del hospital.
 * 
 * @since v0.1.3
 * @author Felipe T.S * 
 --- */

public class ControladorHospital {
	
	private Map<String, Map<String, Cama>> mapaDepartamentos;
	
	public ControladorHospital() {
		this.mapaDepartamentos = new HashMap<>(); // TDA inicializado
		GestorPersistencia.cargarDatos(this.mapaDepartamentos);
	}
	
	/* +++
	 * @deprecated
	 * método inicializarDatos, encargado de hard-codear departamentos y camas iniciales
	 * fue eliminado en favor de agregar departamentos y camas de forma manual por
	 * el staff médico.
	 * 
	 * @since 0.1.6
	 * @author Felipe T.S.
	 --- */
	
	public void agregarDepartamento(String nombreDepto) throws IllegalArgumentException {
		if(this.mapaDepartamentos.containsKey(nombreDepto)) {
			throw new IllegalArgumentException("[!] El departamento ya existe.");
		}
		this.mapaDepartamentos.put(nombreDepto, new HashMap<>());
	}
	
	public void agregarCama(String nombreDepartamento, String idCama, int prioridad) throws EntidadNoEncontradaException, IllegalArgumentException {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(nombreDepartamento);
		
		if(camasDelDepartamento == null) {
			throw new EntidadNoEncontradaException("[!] Departamento inexistente.");
		}
		if(camasDelDepartamento.containsKey(idCama)) {
			throw new IllegalArgumentException("[!] El ID de Cama ya está registrado en este departamento.");
		}
		
		Cama nuevaCama = new Cama(idCama, prioridad);
		camasDelDepartamento.put(idCama, nuevaCama);
	}
	
	public void eliminarDepartamento(String nombreDepto) throws EntidadNoEncontradaException, IllegalStateException {
		Map<String, Cama> camasDelDepto = this.mapaDepartamentos.get(nombreDepto);
		if(camasDelDepto == null) {
			throw new EntidadNoEncontradaException("[!] Departamento inexistente.");
		}
		if(!camasDelDepto.isEmpty()) {
			throw new IllegalStateException("[!] No se puede eliminar el departamento porque aún contiene camas registradas.");
		}
		this.mapaDepartamentos.remove(nombreDepto);
	}
	
	public String mostrarCamasPorDepartamento(String nombreDepto) throws EntidadNoEncontradaException {
		Map<String, Cama> camasDelDepto = this.mapaDepartamentos.get(nombreDepto);
		if(camasDelDepto == null) {
			throw new EntidadNoEncontradaException("[!] Departamento inexistente.");
		}
		
		if(camasDelDepto.isEmpty()) {
			return "\tEl departamento " + nombreDepto + " no tiene camas registradas.";
		}
		
		StringBuilder cadena = new StringBuilder("\t--- CAMAS EN " + nombreDepto.toUpperCase() + "---\n");
		List<Cama> listaCamas = new ArrayList<>(camasDelDepto.values()); 
		
		for(int i=0; i<listaCamas.size(); i++) {
			Cama camaActual = listaCamas.get(i);
			cadena.append("\t").append(i + 1).append(". ID: ").append(camaActual.getIdCama())
		      .append(" | Prioridad: ").append(camaActual.getPrioridad())
		      .append(" | Estado: ").append(camaActual.isDisponible() ? "Libre" : "Ocupada")
		      .append("\n");
		}
		return cadena.toString();
	}
	
	public void eliminarCama(String nombreDepto, String idCama) throws EntidadNoEncontradaException, IllegalStateException {
		Map<String, Cama> camasDelDepto = this.mapaDepartamentos.get(nombreDepto);
		if(camasDelDepto == null) {
			throw new EntidadNoEncontradaException("[!] Departamento inexistente.");
		}
		
		Cama camaActual = camasDelDepto.get(idCama);
		if(camaActual == null) {
			throw new EntidadNoEncontradaException("[!] La cama no existe en este departamento.");
		}
		
		if(!camaActual.isDisponible()) {
			throw new IllegalStateException("[!] No se puede eliminar una cama ocupada por un paciente.");
		}
		
		camasDelDepto.remove(idCama);
	}
	
	public String mostrarDepartamentos() {
		if(this.mapaDepartamentos.isEmpty()) return "No hay departamentos registrados en el hospital.";
		
		StringBuilder cadena = new StringBuilder("--- LISTA DE DEPARTAMENTOS ---\n");
		List<String> nombreDeptos = new ArrayList<>(this.mapaDepartamentos.keySet());
		
		for(int i=0; i<nombreDeptos.size(); i++) {
			String nombre = nombreDeptos.get(i);
			int totalCamas = this.mapaDepartamentos.get(nombre).size();
			cadena.append((i + 1)).append(". ").append(nombre)
			.append(" | Total Camas: ").append(totalCamas).append("\n");
		}
		return cadena.toString();
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
	
	public String filtrarPorGravedad(String gravedad) throws EntidadNoEncontradaException {
		StringBuilder cadena = new StringBuilder();
		List<String> nombresDptos = new ArrayList<>(mapaDepartamentos.keySet());
		
		for (int i = 0; i < nombresDptos.size(); i++) {
			String nombreDpto = nombresDptos.get(i);
			Map<String, Cama> camasDelDepartamento = mapaDepartamentos.get(nombreDpto);
			if (camasDelDepartamento == null) throw new EntidadNoEncontradaException("Departamento inexistente.");
			List<String> listaIdCamas = new ArrayList<>(camasDelDepartamento.keySet());
			
			for (int j = 0; j < listaIdCamas.size(); j++) {
				String idCama = listaIdCamas.get(j);
				Cama cama = camasDelDepartamento.get(idCama);
				
				if (cama != null && !cama.isDisponible() && cama.getPaciente() != null) {
					Paciente paciente = cama.getPaciente();
					if (paciente.coincideGravedad(gravedad)) {
						cadena.append("Departamento: ").append(nombreDpto).append("\n");
						cadena.append(paciente.toString()).append("\n\n");
					}
				}
			}
		}
		if (cadena.length() == 0) {
			return "No se encontraron pacientes con la gravedad: " + gravedad;
		}
		return (cadena.toString());
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