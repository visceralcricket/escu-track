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
		
		/*
		 * while(sistemaActivo) {
		 * 		resto del código...
		 * }
		 */
		
	}
	
}
