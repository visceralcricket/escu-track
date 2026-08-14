package core.escutrack.controller;

import core.escutrack.model.Paciente;
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
	
	private Map<String, Map<String, Paciente>> mapaDepartamentos;
	
	public ControladorHospital() {
		this.mapaDepartamentos = new HashMap<>();
		// inicializarDatos();
	}
	
	public void registrarPaciente(String rut, String nombre, String gradoGravedad, String departamento, String idCama) {
		Paciente nuevoPaciente = new Paciente(rut, nombre, gradoGravedad);
		nuevoPaciente.setIdCamaAsignada(idCama);
		
		// Si el departamento no existe, lo creamos
		if(!this.mapaDepartamentos.containsKey(departamento)) {
			this.mapaDepartamentos.put(departamento, new HashMap<>()); 
		}
		
		// Insertamos el nuevo paciente en el mapa interno correspondiente al departamento
		this.mapaDepartamentos.get(departamento).put(idCama, nuevoPaciente);
	}
	
}
