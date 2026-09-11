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

public class Main {
	
	private enum EscutrackMode {
		WINDOW_MODE,
		CONSOLE_MODE
	}
	
	private enum EscutrackMenu {
		
	}
	
	// Instanciar modo de visualización por defecto a consola (o ventana)
	public static EscutrackMode currentMode = EscutrackMode.CONSOLE_MODE;
	
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
	
	private static String solicitarEntrada(String msg, BufferedReader lector) throws IOException {
		System.out.print(msg + " ");
		return lector.readLine();
	}
	
}