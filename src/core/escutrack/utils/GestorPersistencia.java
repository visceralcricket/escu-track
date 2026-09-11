package core.escutrack.utils;

import core.escutrack.model.Cama;
import core.escutrack.model.Paciente;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Módulo de utilidad encargado de la persistencia de datos del hospital
 * (SIA-11): guarda y carga el estado completo del sistema (departamentos,
 * camas y pacientes) en un archivo CSV plano, ubicado en una ruta relativa
 * al directorio de trabajo del programa.
 * <p>
 * <b>Importante:</b> como {@link #RUTA_ARCHIVO} es una ruta relativa, el
 * programa debe ejecutarse siempre desde la carpeta raíz del proyecto para
 * que la lectura y escritura de datos funcione correctamente.
 *
 * @author Felipe T.S.
 */
public class GestorPersistencia {

    /** Ruta relativa (desde la raíz del proyecto) del archivo CSV de persistencia. */
    private static final String RUTA_ARCHIVO = "src/core/escutrack/resources/datos_hospital.csv";
    /** Carácter usado para separar las columnas dentro de cada línea del CSV. */
    private static final String DELIMITADOR = ",";

    /**
     * Guarda el estado completo del hospital (todos los departamentos, sus
     * camas y, si corresponde, el paciente asignado a cada una) en el
     * archivo CSV de persistencia (SIA-11). El archivo se sobrescribe por
     * completo en cada llamada.
     *
     * @param mapaDepartamentos mapa anidado departamento -&gt; idCama -&gt; {@link Cama}
     *                          con el estado actual del hospital
     * @throws IOException si ocurre un error al escribir el archivo
     */
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
                             .append(DELIMITADOR).append(gravedadTextual)
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

    /**
     * Carga el estado del hospital desde el archivo CSV de persistencia y
     * reconstruye el mapa anidado de departamentos, camas y pacientes
     * recibido como parámetro. Si el archivo no existe (primera ejecución
     * del programa), el mapa se deja sin modificar.
     * <p>
     * Cualquier error de lectura o de formato en el archivo se captura
     * internamente y se reporta por consola, sin propagar la excepción.
     *
     * @param mapaDepartamentos mapa anidado departamento -&gt; idCama -&gt; {@link Cama}
     *                          que será poblado con los datos leídos
     */
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