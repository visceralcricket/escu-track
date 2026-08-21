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
		/* +++
		 * Aquí debería ir registrado uno / algunos pacientes para cumplir
		 * el SIA de datos de prueba incluidos.
		 --- */
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

	/* +++
	 * FIXME: Toda esta sección de código hacia abajo viola el patrón de MVC
	 * > modelo, vista, controlador.
	 * Este módulo/archivo únicamente se encarga del modelado, no de tratar con
	 * los posibles errores y casos extremos/inválidos, todo lo mencionado debería
	 * ser manejado en el front-end (Hospital.java), no aquí.
	 * 
	 * TODO: Remover TODAS las operaciones de I/O de texto como System.out.println y 
	 * delegar todas esas responsabilidades de UX (experiencia de usuario) a Hospital.java
	 * o a algún otro archivo externo nuevo de renderizado.
	 * 
	 * @note Una manera de arreglar esto es utilizando throw new IllegalArgumentException
	 * en cada error detectado: esto detendrá la ejecución del método de inmediato y luego
	 * finalizaríamos con try-catch en el archivo Main. esto detectará el error arrojado
	 * por el método y ya desde la función main podremos gestionar y trabajar el error. 
	 * 
	 *  @author Felipe T.S
	 --- */

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
        
        // ingresar resto del código aquí..
	}
}