package core.escutrack.controller;

import core.escutrack.model.Paciente;
import core.escutrack.model.Cama;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
		this.mapaDepartamentos = new HashMap<>();
		inicializarDatos();
	}
	
	private void inicializarDatos() {
		Map<String, Cama> camasUci = new HashMap<>();
		
		Cama cama1 = new Cama("A-01", 1);
		Cama cama2 = new Cama("A-02", 2);
		
		camasUci.put(cama1.getIdCama(), cama1);
		camasUci.put(cama2.getIdCama(), cama2);
		
		this.mapaDepartamentos.put("UCI", camasUci);
	}
	
	public void registrarPaciente(String rut, String nombre, String gradoGravedad, String departamento, String idCama, LocalDateTime fechaIngreso)
	{
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);

		if (camasDelDepartamento == null) {
			System.out.println("Ingrese un departamento válido");
			return;
		}

		Cama cama = camasDelDepartamento.get(idCama);

		if (cama == null) {
			System.out.println("Ingrese un idCama válido");
			return;
		}

		if (!cama.isDisponible()) {
			System.out.println("La cama ya está ocupada");
			return;
		}

		Paciente nuevoPaciente = new Paciente(rut, nombre, gradoGravedad, fechaIngreso);
		nuevoPaciente.setIdCamaAsignada(idCama);

		cama.setPaciente(nuevoPaciente); // ya deja disponible = false internamente
	}
	
	public void registrarPaciente(String rut, String nombre, String gradoGravedad, String departamento, String idCama) { // SIA 5
	    registrarPaciente(rut, nombre, gradoGravedad, departamento, idCama, LocalDateTime.now());
	}
		
	public void eliminarPaciente(String idCama, String departamento) {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		// Notificar y delegar responsabilidad del error al front-end
		if(camasDelDepartamento == null) throw new IllegalArgumentException("Departamento inexistente.");
		
		Cama cama = camasDelDepartamento.get(idCama);
		if(cama == null) throw new IllegalArgumentException("ID de Cama no válido.");
		if(cama.isDisponible()) throw new IllegalStateException("La cama ya está vacía.");

		cama.setPaciente(); // versión sin argumentos: limpia paciente y marca disponible = true
	}
	
	/* +++
	 * mostrarPaciente modificado para que retorne un String en vez de simplemente
	 * imprimir Paciente: delegar tarea de renderizado y visualización al front-end.
	 * 
	 * @author Felipe T.S.
	 --- */

	public String mostrarPaciente(String idCama, String departamento) {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		if(camasDelDepartamento == null) throw new IllegalArgumentException("Departamento inexistente.");
		
		Cama cama = camasDelDepartamento.get(idCama);
		if(cama == null) throw new IllegalArgumentException("ID de Cama no válido.");
	
		Paciente paciente = cama.getPaciente();
		if(paciente == null) throw new IllegalStateException("Cama sin paciente asignado.");
		
		return paciente.toString(); // Retornar String
	}
	
	public void modificarGravedadPaciente(String idCama, String departamento, String nuevaGravedad) {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		if(camasDelDepartamento == null) throw new IllegalArgumentException("Departamento inexistente.");
		
		Cama cama = camasDelDepartamento.get(idCama);
		if(cama == null) throw new IllegalArgumentException("ID de Cama no válido.");
		if(cama.isDisponible()) throw new IllegalStateException("La cama está vacía, no hay paciente que modificar.");
		
		cama.getPaciente().setNivelGravedad(nuevaGravedad);
	}
	
}