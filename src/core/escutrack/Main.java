package core.escutrack;

import core.escutrack.controller.ControladorHospital;

// Módulo encargado del output por medio de la consola del programa
import core.escutrack.view.RenderizadorConsola;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// Constructor de scripts -> necesario para ejecutar archivos batch
import java.lang.ProcessBuilder;

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
			opcion = RenderizadorConsola.solicitarEntrada("Ingrese 1 o 2:", lector);
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

		String mensajeIntroduccion = "\n\n\t| -- INICIO DE SISTEMA - ESCUTRACK v" +
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
			RenderizadorConsola renderizador = new RenderizadorConsola();
			renderizador.iniciar(controlador, lector, mensajeIntroduccion);
		}
	} // main
}