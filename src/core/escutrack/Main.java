package core.escutrack;

import core.escutrack.controller.ControladorHospital;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

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
			System.out.println("3. Salir");
			System.out.print("Seleccione una opción: ");
			
			String opcion = lector.readLine();
			
			switch(opcion) {
				case "1":
					// Por seguridad de mantener el buffer limpio, utilizaremos System.out.print
					System.out.println("\n| -- REGISTRO DE PACIENTE -- |");
					
					System.out.print("RUT: ");
					String rut = lector.readLine();
					
					System.out.print("Nombre: ");
					String nombre = lector.readLine();
					
					System.out.print("Gravedad (estable, moderado, urgente, severo, critico): ");
					String gravedad = lector.readLine();
					
					System.out.print("Departamento (ej. UCI): ");
					String depto = lector.readLine();
					
					System.out.print("ID Cama (ej. C-01): ");
					String cama = lector.readLine();
					
					// invocar la sobrecarga sin fecha (SIA-5)
					controlador.registrarPaciente(rut, nombre, gravedad, depto, cama);
					System.out.println("-> Paciente registrado exitosamente.");
					
					break;
					
				case "2":
					System.out.println("\n--- BÚSQUEDA DE PACIENTE ---");
					System.out.print("Departamento: ");
					String deptoBusqueda = lector.readLine();
					
					System.out.print("ID Cama: ");
					String camaBusqueda = lector.readLine();
					
					controlador.mostrarPaciente(camaBusqueda, deptoBusqueda);
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
}
