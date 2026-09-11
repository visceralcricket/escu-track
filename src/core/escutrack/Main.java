package core.escutrack;

import core.escutrack.controller.ControladorHospital;

// Módulo encargado del output por medio de la consola del programa
import core.escutrack.view.RenderizadorConsola;

// Validador principal de parámetros
import core.escutrack.utils.ValidadorCamposUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// Constructor de scripts -> necesario para ejecutar archivos batch
import java.lang.ProcessBuilder;

// Necesario para abrir programa en ventanas
import javax.swing.JOptionPane;

// Necesarios para lanzar la ventana principal con WindowBuilder
import core.escutrack.view.VentanaPrincipal;
import java.awt.EventQueue;

// Importaciones para mejorar visuales del modo ventana
import javax.swing.UIManager;
import java.awt.Font;

/**
 * Clase principal del programa EscuTrack: punto de entrada de la aplicación.
 * <p>
 * Al iniciar, intenta actualizar automáticamente el número de versión
 * ejecutando {@code extract_version.bat} (solo funciona en Windows; en otros
 * sistemas se conserva el último valor guardado en {@code version.txt}),
 * pregunta al usuario qué modo de visualización desea usar y luego arranca
 * el ciclo principal correspondiente: el Modo Ventana ({@link VentanaPrincipal},
 * basado en Swing) o el Modo Consola (basado en {@link RenderizadorConsola}).
 */
public class Main {

	/**
	 * Modos de visualización disponibles para el programa: interfaz
	 * gráfica (Swing) o interfaz de texto por consola.
	 */
	private enum EscutrackMode {
		WINDOW_MODE,
		CONSOLE_MODE
	}

	private enum EscutrackMenu {

	}

	/** Modo de visualización actualmente activo (por defecto, consola). */
	// Instanciar modo de visualización por defecto a consola (o ventana)
	public static EscutrackMode currentMode = EscutrackMode.CONSOLE_MODE;

	/**
	 * Punto de entrada del programa. Actualiza el número de versión,
	 * solicita al usuario el modo de visualización deseado y ejecuta el
	 * ciclo principal correspondiente (Modo Ventana o Modo Consola).
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 * @throws IOException si ocurre un error de entrada/salida en el flujo de consola
	 */
	public static void main(String[] args) throws IOException {

		BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
		// Estilizadores para modo ventana
		UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.PLAIN, 16));
		UIManager.put("OptionPane.buttonFont", new Font("Consolas", Font.BOLD, 14));
		UIManager.put("TextField.font", new Font("Consolas", Font.PLAIN, 16));

		try {
			/* +++
			 *  el parámetro /c ejecuta el script que se le indica a continuación
			 *  y luego procede a cerrarlo de inmediato. Esto evita que la terminal
			 *  se mantenga abierta tras haber ya ejecutado el script en sí, lo cual
			 *  sería innecesario en este contexto.
			 *
			 *  @author Felipe T.S.
			 --- */
			ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "extract_version.bat");
			pb.directory(new java.io.File("."));
			Process proceso = pb.start();
			// El programa Java se detiene hasta que el .bat se termine de ejecutar
			proceso.waitFor();
		}
		catch(InterruptedException | IOException e) {
			System.err.println("[!] Advertencia: No se pudo auto-ejecutar el script de versión.\nPor favor verifique la integridad de los archivos del programa.");
		}

		String currentVersion = VersionLoader.getVersion();
		ControladorHospital controlador = new ControladorHospital();
		String opcion = "";

		System.out.println("¿Qué modo de visualización desea ejecutar el programa?\n1) Modo Consola\n2) Modo Ventana");

		while(true) {
			opcion = solicitarEntrada("Ingrese 1 o 2:", lector);
			if(opcion.equals("1")) {
				currentMode = EscutrackMode.CONSOLE_MODE;
				break;
			}
			if(opcion.equals("2")) {
				currentMode = EscutrackMode.WINDOW_MODE;
				break;
			}
			System.out.println("Opción no válida. Intente de nuevo.");
		}

		String mensajeIntroduccion = "\n\n\t| -- INICIO DE SISTEMA - ESCUTRACK " +
		currentVersion +" -- |\n";

		if(currentMode == EscutrackMode.WINDOW_MODE) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						VentanaPrincipal frame = new VentanaPrincipal(controlador);
						frame.setVisible(true);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		else {
			System.out.println(mensajeIntroduccion);
			boolean sistemaActivo = true;

			while(sistemaActivo) {
				String mainMenu = "\t$ -- MENÚ PRINCIPAL -- $\n" +
				"\t1. Gestión de Departamentos\n" +
				"\t2. Gestión de Camas\n" +
				"\t3. Gestión de Pacientes\n" +
				"\t4. Guardar y Salir\n\n" +
				"Seleccione un módulo:";

				opcion = solicitarEntrada(mainMenu, lector);
				if(opcion == null) opcion = "4";

				switch(opcion) {
					case "1":
						RenderizadorConsola.menuDepartamentos(lector, controlador);
						break;

					case "2":
						RenderizadorConsola.menuCamas(lector, controlador);
						break;

					case "3":
						RenderizadorConsola.menuPacientes(lector, controlador);
						break;

					case "4":
						System.out.println("Guardando y cerrando sistema...");
						try {
							controlador.apagarSistema();
							}
						catch (Exception e) {
							System.out.println("\n\t[ERROR]: " + e.getMessage());
							}
						sistemaActivo = false;
						break;

					default:
						System.out.println("\tOpción no válida.");
						break;
				}
			} // bucle principal consola
		}
	} // main

	/**
	 * Imprime un mensaje de solicitud y lee la siguiente línea ingresada
	 * por el usuario desde la consola.
	 *
	 * @param msg    mensaje a mostrar antes de leer la entrada
	 * @param lector lector de entrada estándar ya abierto
	 * @return la línea ingresada por el usuario (puede ser {@code null} si se cierra el flujo de entrada)
	 * @throws IOException si ocurre un error al leer la entrada
	 */
	private static String solicitarEntrada(String msg, BufferedReader lector) throws IOException {
		System.out.print(msg + " ");
		return lector.readLine();
	}

}