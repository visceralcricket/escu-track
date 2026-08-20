package core.escutrack.controller;

import core.escutrack.model.Cama;
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

	private Map<String, Map<String, Cama>> mapaDepartamentos;

	public ControladorHospital() {
		this.mapaDepartamentos = new HashMap<>();
		// inicializarDatos();
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

		if (camasDelDepartamento == null) {
			System.out.println("Ingrese un departamento válido");
			return;
		}

		Cama cama = camasDelDepartamento.get(idCama);

		if (cama == null) {
			System.out.println("Ingrese un idCama válido");
			return;
		}

		cama.setPaciente(); // versión sin argumentos: limpia paciente y marca disponible = true
	}


	public void mostrarPaciente(String idCama, String departamento) {
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

		Paciente paciente = cama.getPaciente();

		if (paciente != null) {
			System.out.println(paciente);
		} else {
			System.out.println("Esa cama no tiene un paciente asignado");
		}
	}
	
	
	public void mostrarPaciente(String departamento) {
	    Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);

	    if (camasDelDepartamento == null) {
	        System.out.println("Ingrese un departamento válido");
	        return;
	    }

	    for (Cama cama : camasDelDepartamento.values()) {
	        if (!cama.isDisponible()) {
	            System.out.println(cama.getPaciente());
	        }
	    }
	}
	
	public void insertarDepartamento(String departamento)
	{
		if(!this.mapaDepartamentos.containsKey(departamento))
		{
			this.mapaDepartamentos.put(departamento, new HashMap<>());
		}
		else
		{
			System.out.println("El departamento ya existe");
		}
	}
	
	public void mostrarDepartamentos()
	{
		for (String departamento : this.mapaDepartamentos.keySet()) {
			System.out.println("[" + departamento + "");
		}
		System.out.println("]");
	}
	
	public void editarDepartamento(String departamento, String nuevoDepartamento)
	{
		if(this.mapaDepartamentos.containsKey(departamento))
		{
			Map<String, Cama> camas = this.mapaDepartamentos.remove(departamento);
			this.mapaDepartamentos.put(nuevoDepartamento, camas);
		}else
		{
			System.out.println("El departamento a editar no existe");
		}
	}
	
	
	public void eliminarDepartamento(String departamento)
	{
		if(this.mapaDepartamentos.containsKey(departamento))
		{
			this.mapaDepartamentos.remove(departamento);
		}else
		{
			System.out.println("El departamento a editar no existe");
		}
	}
	
	public void buscarDepartamento(String departamento) 
	{
		if (this.mapaDepartamentos.containsKey(departamento)) 
		{
			System.out.println("El departamento '" + departamento + "' existe.");
			Map<String, Cama> camas = this.mapaDepartamentos.get(departamento);
			System.out.println("Tiene " + camas.size() + " cama(s) registrada(s).");
			
		}else 
		{
			System.out.println("El departamento '" + departamento + "' no existe.");
		}
	}
	
	public void insertarCama(String cama)
	{
		if(!this.mapaDepartamentos.containsKey(cama))
		{
			this.mapaDepartamentos.put(cama, new HashMap<>());
		}
		else
		{
			System.out.println("El departamento ya existe");
		}
	}
	
	
	/*
	 * FALTA LO SIGUIENTE
	 * 
	 *  6. Insertar cama
		7. Mostrar listado de camas
		8. Editar cama
		9. Eliminar cama
		10. Buscar cama
	 */
	 
	
}