package core.escutrack;

import core.escutrack.controller.ControladorHospital;
// Validador principal de parámetros
import core.escutrack.utils.ValidadorCamposUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
// Constructor de scripts -> necesario para ejecutar archivos batch
import java.lang.ProcessBuilder;
// Necesario para abrir programa en ventanas
import javax.swing.JOptionPane;
// Importaciones para mejorar visuales del modo ventana
import javax.swing.UIManager;
import java.awt.Font;

public class Main {
	
	private enum EscutrackMode {
		WINDOW_MODE,
		CONSOLE_MODE
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
		
		boolean sistemaActivo = true;
		
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
		
		mostrarSalida(mensajeIntroduccion);
		
		while(sistemaActivo) {
			String mainMenu = "\t$ -- MENÚ PRINCIPAL -- $\n" +
			"\t1. Registrar nuevo Paciente\n" +
			"\t2. Mostrar Paciente específico/a\n" +
			"\t3. Salir\n\n" +
			"Seleccione una opción:";
			
			opcion = solicitarEntrada(mainMenu, lector);
			// Si cancela el menú, sale del programa
			if(opcion == null) opcion = "3";
			
			switch(opcion) {
				case "1":
				    mostrarSalida("\n\t| -- REGISTRO DE PACIENTE -- |");
				    try {
				        String rut = solicitarEntrada("\tRUT: ", lector);
				        ValidadorCamposUtils.validarRut(rut);
				        
				        String nombre = solicitarEntrada("\tNombre: ", lector);
				        ValidadorCamposUtils.validarNombre(nombre);
				        
				        String gravedad = solicitarEntrada("\tGravedad: ", lector);
				        ValidadorCamposUtils.validarGravedad(gravedad);
				        
				        String depto = solicitarEntrada("\tDepartamento: ", lector);
				        ValidadorCamposUtils.validarDepartamento(depto);
				        
				        String cama = solicitarEntrada("\tID Cama: ", lector);
				        ValidadorCamposUtils.validarIdCama(cama);
				        
				        controlador.registrarPaciente(rut, nombre, gravedad, depto, cama);
				        mostrarSalida("\t-> Paciente registrado exitosamente.");
				        
				    }
				    catch (Exception e) {
				        mostrarSalida("\n\t[ERROR DE REGISTRO]: " + e.getMessage());
				    }
				    break;
					
				case "2":
					mostrarSalida("\n\t--- BÚSQUEDA DE PACIENTE ---");
					try { 
						String deptoBusqueda = solicitarEntrada("\tDepartamento: ", lector);
						ValidadorCamposUtils.validarDepartamento(deptoBusqueda);
						
						String camaBusqueda = solicitarEntrada("\tID Cama: ", lector);
						ValidadorCamposUtils.validarIdCama(camaBusqueda);
						
						String datosPaciente = controlador.mostrarPaciente(camaBusqueda, deptoBusqueda);
						
						mostrarSalida("\tDatos del Paciente:\n" + datosPaciente);
					}
					catch(Exception e) {
						// SIA-12: Captura polimórfica con custom exceptions
						mostrarSalida("\n\t[ERROR DE BÚSQUEDA]: " + e.getMessage());
					}
					break;
					
				case "3":
					mostrarSalida("Cerrando sistema EscuTrack...");
					try {
						// Ejecutar método en Controlador para guardar CSV 
						controlador.apagarSistema();
					}
					catch (Exception e) {
						mostrarSalida("\n\t[ERROR CRÍTICO AL GUARDAR BASE DE DATOS]: " + e.getMessage());
					}
					sistemaActivo = false;
					break;
					
				default:
					mostrarSalida("\tOpción no válida. Intente nuevamente.");
					break;
			}
		}	
	}
	
	private static String solicitarEntrada(String msg, BufferedReader lector) throws IOException {
		if(currentMode == EscutrackMode.WINDOW_MODE) {
			return JOptionPane.showInputDialog(null, msg, "EscuTrack", JOptionPane.QUESTION_MESSAGE);
		}
		else {
			System.out.print(msg + " ");
			return lector.readLine();
		}
	}
	
	private static void mostrarSalida(String msg) {
		if(currentMode == EscutrackMode.WINDOW_MODE) {
			JOptionPane.showMessageDialog(null, msg, "EscuTrack", JOptionPane.INFORMATION_MESSAGE);
		}
		else System.out.println(msg);
	}
	
}