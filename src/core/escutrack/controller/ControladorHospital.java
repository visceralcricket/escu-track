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

/**
 * Controlador principal del sistema (patrón MVC): concentra toda la lógica
 * de negocio del hospital y expone las operaciones de gestión de
 * departamentos, camas y pacientes al front-end (consola o ventana).
 * <p>
 * Internamente mantiene un mapa anidado {@code departamento -> idCama ->
 * Cama} que permite ubicar espacialmente cada cama y, a través de ella, al
 * paciente que tenga asignado. Al construirse, carga automáticamente el
 * estado guardado en el archivo CSV de persistencia mediante
 * {@link GestorPersistencia#cargarDatos(Map)}.
 */
public class ControladorHospital {

	/** Mapa anidado departamento -&gt; idCama -&gt; {@link Cama} con el estado completo del hospital. */
	private Map<String, Map<String, Cama>> mapaDepartamentos;

	/**
	 * Crea el controlador e inicializa el mapa de departamentos cargando
	 * el estado previamente guardado (si existe) desde el archivo CSV de
	 * persistencia.
	 */
	public ControladorHospital() {
		this.mapaDepartamentos = new HashMap<>(); // TDA inicializado
		GestorPersistencia.cargarDatos(this.mapaDepartamentos);
		/* +++
		 * En caso de que no exista archivo CSV tras intentar cargarlo, por defecto
		 * creamos una cantidad finita de datos de ejemplo.
		 --- */
		if(this.mapaDepartamentos == null || this.mapaDepartamentos.isEmpty()) {
			inicializarDatos();
		}
	}

	/**
	 * Registra un nuevo departamento vacío (sin camas) en el hospital.
	 *
	 * @param nombreDepto nombre del nuevo departamento
	 * @throws IllegalArgumentException si ya existe un departamento con ese nombre
	 */
	public void agregarDepartamento(String nombreDepto) throws IllegalArgumentException {
		if(this.mapaDepartamentos.containsKey(nombreDepto)) {
			throw new IllegalArgumentException("[!] El departamento ya existe.");
		}
		this.mapaDepartamentos.put(nombreDepto, new HashMap<>());
	}

	/* Inyección y creación de data por defecto.
	 * 
	 * @param cama1 y cama2 -> ejemplos hard-codeados en caso de que, por algún
	 * motivo, no exista ningún archivo CSV en el directorio de recursos / resources.
	 */
	private void inicializarDatos() {
		Map<String, Cama> camasUci = new HashMap<>();
		
		Cama cama1 = new Cama("A-01", 1);
		Cama cama2 = new Cama("A-02", 2);
		
		camasUci.put(cama1.getIdCama(), cama1);
		camasUci.put(cama2.getIdCama(), cama2);
		
		this.mapaDepartamentos.put("UCI", camasUci);
	} 
	
	/**
	 * Registra una nueva cama, inicialmente libre, dentro de un
	 * departamento existente.
	 *
	 * @param nombreDepartamento nombre del departamento donde se agregará la cama
	 * @param idCama             identificador único de la cama dentro del departamento
	 * @param prioridad          nivel de prioridad de la cama
	 * @throws EntidadNoEncontradaException si el departamento no existe
	 * @throws IllegalArgumentException     si ya existe una cama con ese ID en el departamento
	 */
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

	/**
	 * Elimina un departamento, siempre que no contenga camas registradas.
	 *
	 * @param nombreDepto nombre del departamento a eliminar
	 * @throws EntidadNoEncontradaException si el departamento no existe
	 * @throws IllegalStateException        si el departamento todavía tiene camas registradas
	 */
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

	/**
	 * Edita (renombra) un departamento existente, conservando todas sus
	 * camas registradas bajo el nuevo nombre.
	 *
	 * @param nombreActual nombre actual del departamento a editar
	 * @param nuevoNombre  nuevo nombre para el departamento
	 * @throws EntidadNoEncontradaException si el departamento actual no existe
	 * @throws IllegalArgumentException     si ya existe otro departamento con el nuevo nombre
	 */
	public void editarDepartamento(String nombreActual, String nuevoNombre) throws EntidadNoEncontradaException, IllegalArgumentException {
		Map<String, Cama> camasDelDepto = this.mapaDepartamentos.get(nombreActual);
		if(camasDelDepto == null) {
			throw new EntidadNoEncontradaException("[!] Departamento inexistente.");
		}

		if(nuevoNombre.equals(nombreActual)) {
			return; // Sin cambios que aplicar
		}

		if(this.mapaDepartamentos.containsKey(nuevoNombre)) {
			throw new IllegalArgumentException("[!] Ya existe un departamento con ese nombre.");
		}

		this.mapaDepartamentos.remove(nombreActual);
		this.mapaDepartamentos.put(nuevoNombre, camasDelDepto);
	}

	/**
	 * Genera un listado textual de todas las camas de un departamento,
	 * incluyendo su ID, prioridad y estado (libre/ocupada).
	 *
	 * @param nombreDepto nombre del departamento a consultar
	 * @return listado formateado de las camas del departamento, o un mensaje indicando que no tiene camas
	 * @throws EntidadNoEncontradaException si el departamento no existe
	 */
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

	/**
	 * Elimina una cama de un departamento, siempre que esté libre.
	 *
	 * @param nombreDepto nombre del departamento dueño de la cama
	 * @param idCama      identificador de la cama a eliminar
	 * @throws EntidadNoEncontradaException si el departamento o la cama no existen
	 * @throws IllegalStateException        si la cama está ocupada por un paciente
	 */
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

	/**
	 * Edita una cama existente de un departamento: actualiza su prioridad
	 * y, opcionalmente, su ID (si el nuevo ID recibido es distinto al
	 * actual). No se restringe la edición de camas ocupadas.
	 *
	 * @param nombreDepto   nombre del departamento dueño de la cama
	 * @param idCamaActual  identificador actual de la cama a editar
	 * @param nuevoIdCama   nuevo identificador para la cama (puede ser igual al actual si no se desea cambiarlo)
	 * @param nuevaPrioridad nueva prioridad para la cama
	 * @throws EntidadNoEncontradaException si el departamento o la cama no existen
	 * @throws IllegalArgumentException     si ya existe otra cama con el nuevo ID en el mismo departamento
	 */
	public void editarCama(String nombreDepto, String idCamaActual, String nuevoIdCama, int nuevaPrioridad) throws EntidadNoEncontradaException, IllegalArgumentException {
		Map<String, Cama> camasDelDepto = this.mapaDepartamentos.get(nombreDepto);
		if(camasDelDepto == null) {
			throw new EntidadNoEncontradaException("[!] Departamento inexistente.");
		}

		Cama cama = camasDelDepto.get(idCamaActual);
		if(cama == null) {
			throw new EntidadNoEncontradaException("[!] La cama no existe en este departamento.");
		}

		boolean cambiaId = !nuevoIdCama.equals(idCamaActual);
		if(cambiaId && camasDelDepto.containsKey(nuevoIdCama)) {
			throw new IllegalArgumentException("[!] Ya existe una cama con ese ID en este departamento.");
		}

		cama.setPrioridad(nuevaPrioridad);

		if(cambiaId) {
			camasDelDepto.remove(idCamaActual);
			cama.setIdCama(nuevoIdCama);
			camasDelDepto.put(nuevoIdCama, cama);
		}
	}

	/**
	 * Genera un listado textual de todos los departamentos registrados,
	 * junto con la cantidad total de camas de cada uno.
	 *
	 * @return listado formateado de departamentos, o un mensaje si no hay ninguno registrado
	 */
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

	/**
	 * Registra un nuevo paciente y lo asigna a una cama libre de un
	 * departamento, indicando explícitamente la fecha de ingreso.
	 *
	 * @param rut           RUT del paciente
	 * @param nombre        nombre completo del paciente
	 * @param gradoGravedad nivel de gravedad en formato textual (ej. "estable")
	 * @param departamento  nombre del departamento donde se encuentra la cama
	 * @param idCama        identificador de la cama a asignar
	 * @param fechaIngreso  fecha y hora de ingreso del paciente
	 * @throws EntidadNoEncontradaException si el departamento o la cama no existen
	 * @throws CamaOcupadaException         si la cama ya está ocupada por otro paciente
	 */
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

	/**
	 * Sobrecarga (SIA-5) de {@link #registrarPaciente(String, String, String, String, String, LocalDateTime)}
	 * que asume como fecha de ingreso el instante actual.
	 *
	 * @param rut           RUT del paciente
	 * @param nombre        nombre completo del paciente
	 * @param gradoGravedad nivel de gravedad en formato textual (ej. "estable")
	 * @param departamento  nombre del departamento donde se encuentra la cama
	 * @param idCama        identificador de la cama a asignar
	 * @throws EntidadNoEncontradaException si el departamento o la cama no existen
	 * @throws CamaOcupadaException         si la cama ya está ocupada por otro paciente
	 */
	public void registrarPaciente(String rut, String nombre, String gradoGravedad, String departamento, String idCama) throws EntidadNoEncontradaException, CamaOcupadaException { // SIA 5
	    registrarPaciente(rut, nombre, gradoGravedad, departamento, idCama, LocalDateTime.now());
	}

	/**
	 * Da de alta al paciente asignado a una cama: limpia el paciente y deja
	 * la cama disponible nuevamente.
	 *
	 * @param idCama      identificador de la cama a liberar
	 * @param departamento nombre del departamento dueño de la cama
	 * @throws EntidadNoEncontradaException si el departamento o la cama no existen, o si la cama ya está vacía
	 * @throws CamaOcupadaException         declarada por firma; no se lanza en la implementación actual
	 */
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

	/**
	 * Obtiene la representación textual del paciente asignado a una cama.
	 *
	 * @param idCama       identificador de la cama a consultar
	 * @param departamento nombre del departamento dueño de la cama
	 * @return el detalle del paciente, generado por {@link Paciente#toString()}
	 * @throws EntidadNoEncontradaException si el departamento o la cama no existen, o si la cama no tiene paciente asignado
	 * @throws CamaOcupadaException         declarada por firma; no se lanza en la implementación actual
	 */
	public String mostrarPaciente(String idCama, String departamento) throws EntidadNoEncontradaException, CamaOcupadaException {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		if(camasDelDepartamento == null) throw new EntidadNoEncontradaException("Departamento inexistente.");

		Cama cama = camasDelDepartamento.get(idCama);
		if(cama == null) throw new EntidadNoEncontradaException("ID de Cama no válido.");

		Paciente paciente = cama.getPaciente();
		if(paciente == null) throw new EntidadNoEncontradaException("Cama sin paciente asignado.");

		return paciente.toString(); // Retornar String
	}
	// Búsqueda específica de un departamento (SIA-8)
	public String buscarDepartamento(String nombreDepto) throws EntidadNoEncontradaException {
		Map<String, Cama> camas = this.mapaDepartamentos.get(nombreDepto);
		if(camas == null) throw new EntidadNoEncontradaException("Departamento inexistente.");
		
		return "Departamento: " + nombreDepto + "\nTotal camas registradas: " + camas.size();
	}
	
	// Búsqueda específica de una Cama (SIA-8)
	public String buscarCama(String nombreDepto, String idCama) throws EntidadNoEncontradaException {
		Map<String, Cama> camas = this.mapaDepartamentos.get(nombreDepto);
		if(camas == null) throw new EntidadNoEncontradaException("Departamento inexistente.");
		
		Cama cama = camas.get(idCama);
		if(cama == null) throw new EntidadNoEncontradaException("La cama no existe en ese departamento.");
		
		return cama.toString();
	}
	
	// Editar los datos de un paciente ya registrado (SIA-8)
	public void editarPaciente(String depto, String idCama, String nuevoNombre, String nuevaGravedad) throws EntidadNoEncontradaException {
		Map<String, Cama> camas = this.mapaDepartamentos.get(depto);
		if(camas == null) throw new EntidadNoEncontradaException("Departamento inexistente.");
		
		Cama cama = camas.get(idCama);
		if(cama == null) throw new EntidadNoEncontradaException("Cama no válida.");
		if(cama.isDisponible() || cama.getPaciente() == null) throw new EntidadNoEncontradaException("La cama no tiene un paciente asignado.");
		
		cama.getPaciente().setNombre(nuevoNombre);
		cama.getPaciente().setNivelGravedad(nuevaGravedad);
	}
	
	/**
	 * Modifica el nivel de gravedad del paciente asignado a una cama.
	 *
	 * @param idCama        identificador de la cama del paciente
	 * @param departamento  nombre del departamento dueño de la cama
	 * @param nuevaGravedad nueva gravedad en formato textual (ej. "urgente")
	 * @throws EntidadNoEncontradaException si el departamento no existe
	 * @throws CamaOcupadaException         declarada por firma; no se lanza en la implementación actual
	 * @throws IllegalArgumentException     si el ID de cama no es válido dentro del departamento
	 * @throws IllegalStateException        si la cama está vacía (sin paciente que modificar)
	 */
	public void modificarGravedadPaciente(String idCama, String departamento, String nuevaGravedad) throws EntidadNoEncontradaException, CamaOcupadaException {
		Map<String, Cama> camasDelDepartamento = this.mapaDepartamentos.get(departamento);
		if(camasDelDepartamento == null) throw new EntidadNoEncontradaException("Departamento inexistente.");

		Cama cama = camasDelDepartamento.get(idCama);
		if(cama == null) throw new IllegalArgumentException("ID de Cama no válido.");
		if(cama.isDisponible()) throw new IllegalStateException("La cama está vacía, no hay paciente que modificar.");

		cama.getPaciente().setNivelGravedad(nuevaGravedad);
	}

	/**
	 * Funcionalidad de negocio propia (SIA-9): recorre todos los
	 * departamentos y camas ocupadas del hospital, devolviendo el detalle
	 * de todos los pacientes cuyo nivel de gravedad coincida con el
	 * indicado.
	 *
	 * @param gravedad etiqueta textual de gravedad a filtrar (ej. "critico")
	 * @return listado con el departamento y el detalle de cada paciente que coincide, o un mensaje si no se encontró ninguno
	 * @throws EntidadNoEncontradaException si algún departamento del mapa interno no pudo resolverse (caso excepcional)
	 */
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
	/**
	 * Persiste el estado completo del hospital en el archivo CSV mediante
	 * {@link GestorPersistencia#guardarDatos(Map)}. Pensado para invocarse
	 * al cerrar el programa desde el flujo principal en {@code Main}.
	 *
	 * @throws IOException si ocurre un error al escribir el archivo de persistencia
	 */
	public void apagarSistema() throws IOException {
		GestorPersistencia.guardarDatos(this.mapaDepartamentos);
	}
}