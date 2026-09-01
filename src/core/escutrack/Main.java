package core.escutrack;

import core.escutrack.controller.ControladorHospital;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
// Necesario para abrir programa en ventanas
import javax.swing.JOptionPane;

public class Main {

	// Alternador del modo ventana del programa 
	public static boolean usarVentanas = true;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
		String currentVersion = VersionLoader.getVersion();
		ControladorHospital controlador = new ControladorHospital();
		
		boolean sistemaActivo = true;
		
		System.out.println("==========================================");
		System.out.printf("| -- INICIO DE SISTEMA - ESCUTRACK %s -- |\n", currentVersion);
		System.out.printf("------------------------------------------");
		
		while(sistemaActivo) {
			System.out.println("\n--- MENÚ PRINCIPAL ---");
			System.out.println("1. Registrar nuevo Paciente");
			System.out.println("2. Mostrar Paciente específico");
			// System.out.println("3. Modificar Paciente particular");
			System.out.println("3. Salir");
			
			String opcion = solicitarEntrada("Seleccione una opción: ", lector);
			
			switch(opcion) {
				case "1":
					// Por seguridad de mantener el buffer limpio, utilizaremos System.out.print
					System.out.println("\n| -- REGISTRO DE PACIENTE -- |");
					
					String rut = solicitarEntrada("RUT: ", lector);
					
					String nombre = solicitarEntrada("Nombre: ", lector);
					
					String gravedad = solicitarEntrada("Gravedad (estable, moderado, urgente, severo, critico): ", lector);
					
					String depto = solicitarEntrada("Departamento (ej. UCI): ", lector);
					
					String cama = solicitarEntrada("ID Cama (ej. C-01): ", lector);
					
					try {
						// invocar la sobrecarga sin fecha (SIA-5)
						controlador.registrarPaciente(rut, nombre, gravedad, depto, cama);
						System.out.println("-> Paciente registrado exitosamente.");
					}
					
					catch (Exception e) {
						System.out.println("\n[ERROR DE REGISTRO]: " + e.getMessage());
					}
					break;
					
				case "2":
					mostrarSalida("\n--- BÚSQUEDA DE PACIENTE ---");
					try { 
						String deptoBusqueda = solicitarEntrada("Departamento: ", lector);
						String camaBusqueda = solicitarEntrada("ID Cama: ", lector);
						String datosPaciente = controlador.mostrarPaciente(camaBusqueda, deptoBusqueda);
						
						mostrarSalida("Datos del Paciente:\n" + datosPaciente);
					}
					catch(Exception e) {
						// SIA-12: Captura polimórfica con custom exceptions
						mostrarSalida("[ERROR DE BÚSQUEDA]: " + e.getMessage());
					}
					break;
					
				case "3":
					System.out.println("Cerrando sistema EscuTrack...");
					sistemaActivo = false;
					
					break;
					
				default:
					System.out.println("Opción no válida. Intente nuevamente.");
					break;
			}
		}	
	}
	
	private static String solicitarEntrada(String msg, BufferedReader lector) throws IOException {
		if(msg == null) return "";
		if(usarVentanas) {
			return JOptionPane.showInputDialog(null, msg, "EscuTrack", JOptionPane.QUESTION_MESSAGE);
		}
		else {
			System.out.print(msg + " ");
			return lector.readLine();
		}
	}
	
	private static void mostrarSalida(String msg) {
		if(usarVentanas) {
			JOptionPane.showMessageDialog(null, msg, "EscuTrack", JOptionPane.INFORMATION_MESSAGE);
		}
		else System.out.println(msg);
	}
}
