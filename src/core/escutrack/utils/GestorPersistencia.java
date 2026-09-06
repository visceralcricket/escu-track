package core.escutrack.utils;

import core.escutrack.model.Cama;
import core.escutrack.model.Paciente;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class GestorPersistencia {
	
    private static final String RUTA_ARCHIVO = "src/core/escutrack/resources/datos_hospital.csv";
    private static final String DELIMITADOR = ",";

    // Guardar datos (SIA-11)
    public static void guardarDatos(Map<String, Map<String, Cama>> mapaDepartamentos) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
        	
            bw.write("Departamento,IdCama,Prioridad,Ocupada,Rut,Nombre,Gravedad,FechaIngreso");
            bw.newLine();
            
            List<String> llavesDeptos = new ArrayList<>(mapaDepartamentos.keySet());
            
            for(int i = 0; i < llavesDeptos.size(); i++) {
                String depto = llavesDeptos.get(i);
                
                Map<String, Cama> camasDelDepto = mapaDepartamentos.get(depto);
                List<String> llavesCamas = new ArrayList<>(camasDelDepto.keySet());
                
                
                for(int j = 0; j < llavesCamas.size(); j++) {
                    String idCama = llavesCamas.get(j);
                    Cama cama = camasDelDepto.get(idCama);
                    
                    StringBuilder linea = new StringBuilder();
                    linea.append(depto).append(DELIMITADOR)
                         .append(cama.getIdCama()).append(DELIMITADOR)
                         .append(cama.getPrioridad()).append(DELIMITADOR)
                         .append(!cama.isDisponible());

                    if(!cama.isDisponible() && cama.getPaciente() != null) {
                        Paciente p = cama.getPaciente();
                        // parsear nivel de gravedad numérico a textual
                        String gravedadTextual = Paciente.TRADUCTOR_GRAVEDAD[p.getNivelGravedad()];
                        
                        linea.append(DELIMITADOR).append(p.getRut())
                             .append(DELIMITADOR).append(p.getNombre())
                             .append(DELIMITADOR).append(p.getNivelGravedad())
                             .append(DELIMITADOR).append(p.getFechaIngreso().toString());
                    }
                    else {
                        // Cama vacía: dejar las columnas del paciente en blanco
                        linea.append(DELIMITADOR).append(DELIMITADOR).append(DELIMITADOR).append(DELIMITADOR);
                    }
                    
                    bw.write(linea.toString());
                    bw.newLine();
                }
            }
        }
    }
    
    public static void cargarDatos(Map<String, Map<String, Cama>> mapaDepartamentos) {
    	java.io.File archivo = new java.io.File(RUTA_ARCHIVO);
    	
    	if(!archivo.exists()) return;
    	
    	try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo))) {
    		String linea = br.readLine();
    		
    		while((linea = br.readLine()) != null) {
    			String[] datos = linea.split(DELIMITADOR, -1);
    			
    			String depto = datos[0];
    			String idCama = datos[1];
    			int prioridad = Integer.parseInt(datos[2]);
    			boolean ocupada = Boolean.parseBoolean(datos[3]);
    			
    			Cama cama = new Cama(idCama, prioridad);
    			cama.setDisponible(!ocupada);
    			
    			if(ocupada && datos.length > 4 && !datos[4].isEmpty()) {
    				String rut = datos[4];
    				String nombre = datos[5];
    				String gravedad = datos[6];
    				java.time.LocalDateTime fecha = java.time.LocalDateTime.parse(datos[7]);
    				
    				Paciente paciente = new Paciente(rut, nombre, gravedad, fecha);
    				paciente.setIdCamaAsignada(idCama);
    				cama.setPaciente(paciente);
    			}
    			mapaDepartamentos.putIfAbsent(depto, new java.util.HashMap<>());
    			mapaDepartamentos.get(depto).put(idCama, cama);
    		}
    	}
    	catch(Exception e) {
    		System.err.println("[!] Error crítico al cargar base de datos: " + e.getMessage());
    	}
    }
    
}