package core.escutrack.controller;

import core.escutrack.model.Paciente;

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
	
	private Map<String, Map<String, Paciente>> mapaDepartamentos;
	
	public ControladorHospital() {
		this.mapaDepartamentos = new HashMap<>();
		// inicializarDatos();
	}
	
	public void registrarPaciente(String rut, String nombre, String gradoGravedad, String departamento, String idCama, LocalDateTime fechaIngreso) 
	{ 
		Paciente nuevoPaciente = new Paciente(rut, nombre, gradoGravedad, fechaIngreso);
		nuevoPaciente.setIdCamaAsignada(idCama);
		
		// Si el departamento no existe, lo creamos
		if(!this.mapaDepartamentos.containsKey(departamento)) {
			this.mapaDepartamentos.put(departamento, new HashMap<>()); 
		}
		
		// Insertamos el nuevo paciente en el mapa interno correspondiente al departamento
		this.mapaDepartamentos.get(departamento).put(idCama, nuevoPaciente);
	}
	
	
	
	
	public void eliminarPaciente(String idCama, String departamento) {  
	    Map<String, Paciente> pacientesDelDepartamento = this.mapaDepartamentos.get(departamento);
	    
	    if (pacientesDelDepartamento != null) {
	    	
	        if (pacientesDelDepartamento.get(idCama) != null) {
	        	
	            pacientesDelDepartamento.remove(idCama);
	            
	        } else {
	            System.out.println("Ingrese un idCama válido");
	        }
	    } else {
	    	
	        System.out.println("Ingrese un departamento válido");
	    }
	     
	}
	
	 public void mostrarPaciente(String idCama, String departamento) 
	 {
		 Map<String, Paciente> pacientesDelDepartamento  = this.mapaDepartamentos.get(departamento);
		 if (pacientesDelDepartamento  != null) {
		    	Paciente paciente = pacientesDelDepartamento.get(idCama);
		        if (paciente != null) {
		        	System.out.println(paciente);
		        	
		        } else {
		            System.out.println("Ingrese un idCama válido");
		        }
		    } else {
		    	
		        System.out.println("Ingrese un departamento válido");
		    }
	 	}
	 
	 // METODO PARA MOSTRAR TODOS LOS PACIENTES DE UNA CAMA
	 //...

	 }
	 
	
