package core.escutrack.view;

import core.escutrack.controller.ControladorHospital;
import java.io.BufferedReader;
import java.io.IOException;
import core.escutrack.utils.ValidadorCamposUtils;

/**
 * Módulo de vista (patrón MVC) encargado de renderizar la interfaz de texto
 * del programa en Modo Consola: dibuja los menús, solicita la entrada del
 * usuario y delega toda la lógica de negocio al
 * {@link ControladorHospital}, sin acceder directamente a las estructuras
 * de datos del hospital.
 */
public class RenderizadorConsola {

	/**
	 * Imprime un mensaje de solicitud y lee la siguiente línea ingresada
	 * por el usuario desde la consola.
	 *
	 * @param msg    mensaje a mostrar antes de leer la entrada
	 * @param lector lector de entrada estándar ya abierto
	 * @return la línea ingresada por el usuario (puede ser {@code null} si se cierra el flujo de entrada)
	 * @throws IOException si ocurre un error al leer la entrada
	 */
	// Método utilizado para simplificar lectura de input + mensaje de solicitación del mismo
	public static String solicitarEntrada(String msg, BufferedReader lector) throws IOException {
		System.out.print(msg + " ");
		return lector.readLine();
	}

	/**
	 * Dibuja y controla el submenú de gestión de pacientes: registrar un
	 * nuevo paciente, mostrar un paciente específico o filtrar pacientes
	 * por gravedad. El menú se repite hasta que el usuario elige volver.
	 *
	 * @param lector      lector de entrada estándar ya abierto
	 * @param controlador controlador con la lógica de negocio del hospital
	 * @throws IOException si ocurre un error al leer la entrada del usuario
	 */
	public static void menuPacientes(BufferedReader lector, ControladorHospital controlador) throws IOException {

		boolean enMenu = true;
		while(enMenu) {
			String mainMenu = "\n\t$ -- MENÚ PACIENTES -- $\n" +
			"\t1. Registrar nuevo Paciente\n" +
			"\t2. Mostrar Paciente específico/a\n" +
			"\t3. Filtrar Pacientes por gravedad\n" +
			"\t4. Salir\n\n" +
			"Seleccione una opción:";

			String opcion = solicitarEntrada(mainMenu, lector);
			// Si cancela el menú, sale del programa
			if(opcion == null) opcion = "3";

			switch(opcion) {
				case "1":
					System.out.println("\n\t| -- REGISTRO DE PACIENTE -- |");
				    try {
				        String rut = solicitarEntrada("\tRUT: ", lector);
				        ValidadorCamposUtils.validarRut(rut);

				        String nombre = solicitarEntrada("\tNombre: ", lector);
				        ValidadorCamposUtils.validarNombre(nombre);

				        String gravedad = solicitarEntrada("\tGravedad (ej. estable): ", lector);
				        ValidadorCamposUtils.validarGravedad(gravedad);

				        String depto = solicitarEntrada("\tDepartamento: ", lector);
				        ValidadorCamposUtils.validarDepartamento(depto);

				        String cama = solicitarEntrada("\tID Cama: ", lector);
				        ValidadorCamposUtils.validarIdCama(cama);

				        controlador.registrarPaciente(rut, nombre, gravedad, depto, cama);
				        System.out.println("\t-> Paciente registrado exitosamente.");

				    }
				    catch (Exception e) {
				    	System.out.println("\n\t[ERROR DE REGISTRO]: " + e.getMessage());
				    }
				    break;

				case "2":
					System.out.println("\n\t--- BÚSQUEDA DE PACIENTE ---");
					try {
						String deptoBusqueda = solicitarEntrada("\tDepartamento: ", lector);
						ValidadorCamposUtils.validarDepartamento(deptoBusqueda);

						String camaBusqueda = solicitarEntrada("\tID Cama: ", lector);
						ValidadorCamposUtils.validarIdCama(camaBusqueda);

						String datosPaciente = controlador.mostrarPaciente(camaBusqueda, deptoBusqueda);

						System.out.println("\tDatos del Paciente:\n" + datosPaciente);
					}
					catch(Exception e) {
						// SIA-12: Captura polimórfica con custom exceptions
						System.out.println("\n\t[ERROR DE BÚSQUEDA]: " + e.getMessage());
					}
					break;

				case "3":
					try {
						String gravedad = solicitarEntrada("\tIngrese gravedad a filtrar: ", lector);
						String pacienteFiltrado = controlador.filtrarPorGravedad(gravedad);
						System.out.println(pacienteFiltrado);
					}
					catch(Exception e) {
						System.out.println("\n\t[ERROR DE FILTRADO]: " + e.getMessage());
					}
					break;

				case "4":
					System.out.println("Volviendo al menú principal...");
					enMenu = false;
					break;

				default:
					System.out.println("\tOpción no válida. Intente nuevamente.");
					break;
			}
		}
	}

	/**
	 * Dibuja y controla el submenú de gestión de departamentos: agregar,
	 * mostrar o eliminar departamentos. El menú se repite hasta que el
	 * usuario elige volver.
	 *
	 * @param lector      lector de entrada estándar ya abierto
	 * @param controlador controlador con la lógica de negocio del hospital
	 * @throws IOException si ocurre un error al leer la entrada del usuario
	 */
	public static void menuDepartamentos(BufferedReader lector, ControladorHospital controlador) throws IOException {
		boolean enMenu = true;
		while(enMenu) {
			String menu = "\n\t--- MÓDULO DEPARTAMENTOS ---\n" +
			"\t1. Agregar Departamento\n" +
			"\t2. Mostrar Departamentos\n" +
			"\t3. Buscar Departamento\n" +
			"\t4. Editar Departamento\n" +
			"\t5. Eliminar Departamento\n" +
			"\t6. Volver al menú principal\n" +
			"Opción: ";

			String opcion = solicitarEntrada(menu, lector);

			switch(opcion) {
				case "1":
					try {
						String nombre = solicitarEntrada("Nombre del nuevo departamento:", lector);
						ValidadorCamposUtils.validarDepartamento(nombre);
						controlador.agregarDepartamento(nombre);
						System.out.println("\t-> Departamento creado con éxito.");
					}

					catch(Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
						}
					break;

				case "2":
					System.out.println(controlador.mostrarDepartamentos());
					break;
				
				case "3":
					try {
						String nombreDepto = solicitarEntrada("Nombre del departamento a buscar: ", lector);
						ValidadorCamposUtils.validarDepartamento(nombreDepto);
						System.out.println(controlador.buscarDepartamento(nombreDepto));
					}
					catch(Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
					}
					break;
					
				case "4":
					try {
						String nombreActual = solicitarEntrada("Nombre del departamento a editar: ", lector);
						ValidadorCamposUtils.validarDepartamento(nombreActual);

						String nuevoNombre = solicitarEntrada("Nuevo nombre del departamento: ", lector);
						ValidadorCamposUtils.validarDepartamento(nuevoNombre);

						controlador.editarDepartamento(nombreActual, nuevoNombre);
						System.out.println("\t-> Departamento editado con éxito.");
					}
					catch(Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
					}
					break;

				case "5":
					try {
						String nombreDepto = solicitarEntrada("Nombre del departamento a eliminar: ", lector);
						ValidadorCamposUtils.validarDepartamento(nombreDepto);
						controlador.eliminarDepartamento(nombreDepto);
						System.out.println("\t-> Departamento eliminado con éxito.");
					}
					catch(Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
					}
					break;

				case "6":
					enMenu = false;
					break;

				default:
					System.out.println("\tOpción no válida.");
			}
		}
	}

	/**
	 * Dibuja y controla el submenú de gestión de camas: agregar una cama a
	 * un departamento, mostrar las camas de un departamento o eliminar una
	 * cama por su ID. El menú se repite hasta que el usuario elige volver.
	 *
	 * @param lector      lector de entrada estándar ya abierto
	 * @param controlador controlador con la lógica de negocio del hospital
	 * @throws IOException si ocurre un error al leer la entrada del usuario
	 */
	public static void menuCamas(BufferedReader lector, ControladorHospital controlador) throws IOException {
		boolean enMenu = true;
		while(enMenu) {
			String menu = "\n\t--- MÓDULO CAMAS ---\n" +
			"\t1. Agregar Cama a Departamento\n" +
			"\t2. Mostrar Camas de Departamento particular\n" +
			"\t3. Buscar Cama.\n" +
			"\t4. Editar Cama\n" +
			"\t5. Eliminar Cama por ID\n" +
			"\t6. Regresar al menú principal.\n" +
			"Opción: ";

			String opcion = solicitarEntrada(menu, lector);

			switch(opcion) {
				case "1":
					try {
						String depto = solicitarEntrada("Departamento destino:", lector);
						ValidadorCamposUtils.validarDepartamento(depto);

						String id = solicitarEntrada("ID de la nueva Cama:", lector);
						ValidadorCamposUtils.validarIdCama(id);

						String prioridad = solicitarEntrada("Prioridad (entero):", lector);
						//@since 0.1.6: método validarPrioridadCama
						ValidadorCamposUtils.validarPrioridadCama(prioridad);

						controlador.agregarCama(depto, id, Integer.parseInt(prioridad));
						System.out.println("\t-> Cama registrada con éxito.");
					}
					catch(Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
						}
					break;

				case "2":
					try {
						String depto = solicitarEntrada("Nombre del departamento:", lector);
						ValidadorCamposUtils.validarDepartamento(depto);

						System.out.println(controlador.mostrarCamasPorDepartamento(depto));
					}
					catch (Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
					}
					break;
				
				case "3":
					try {
						String depto = solicitarEntrada("Departamento de la cama a buscar: ", lector);
						ValidadorCamposUtils.validarDepartamento(depto);
						String id = solicitarEntrada("ID de la Cama: ", lector);
						ValidadorCamposUtils.validarIdCama(id);
						
						System.out.println(controlador.buscarCama(depto, id));
					}
					catch(Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
					}
					break;
					
				case "4":
					try {
						String depto = solicitarEntrada("Departamento de la cama a editar:", lector);
						ValidadorCamposUtils.validarDepartamento(depto);

						String idActual = solicitarEntrada("ID actual de la Cama:", lector);
						ValidadorCamposUtils.validarIdCama(idActual);

						String nuevoId = solicitarEntrada("Nuevo ID de la Cama (ingrese el mismo si no desea cambiarlo):", lector);
						ValidadorCamposUtils.validarIdCama(nuevoId);

						String prioridad = solicitarEntrada("Nueva prioridad (entero):", lector);
						ValidadorCamposUtils.validarPrioridadCama(prioridad);

						controlador.editarCama(depto, idActual, nuevoId, Integer.parseInt(prioridad));
						System.out.println("\t-> Cama editada con éxito.");
					}
					catch (Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
					}
					break;

				case "5":
					try {
						String depto = solicitarEntrada("Departamento de la cama a eliminar:", lector);
						ValidadorCamposUtils.validarDepartamento(depto);

						String id = solicitarEntrada("ID de la Cama a eliminar:", lector);
						ValidadorCamposUtils.validarIdCama(id);

						controlador.eliminarCama(depto, id);
						System.out.println("\t-> Cama eliminada con éxito.");
					}
					catch (Exception e) {
						System.out.println("\t[ERROR]: " + e.getMessage());
					}
					break;

				case "6":
					enMenu = false;
					break;

				default:
					System.out.println("\tOpción no válida.");
			}
		}
	}

}
