package core.escutrack.model;

import java.util.Map;

public record Paciente(String rut, String nombre, int nivelGravedad, String idCamaAsignada) {
	/* +++
	 * Aquí hacemos uso de una tabla hash incluida en la librería de util.Map de Java
	 * para poder asignar los niveles de gravedad con la menor complejidad temporal
	 * posible haciendo uso de sus etiquetas cualitativas como keys.
	 * @author Felipe T.S.
	 --- */
	private static final Map<String, Integer> ESTADO_CLINICO = Map.of(
			"indefinido", 0,
			"estable", 1,
			"moderado", 2,
			"urgente", 3,
			"severo", 4,
			"critico", 5
		);
	
	private static int parseGradoGravedad(String gradoGravedad) {
		if(gradoGravedad == null) return 0;
		
		Integer nivelGravedad = ESTADO_CLINICO.get(gradoGravedad.trim().toLowerCase()); 
		if(nivelGravedad == null) {
			throw new IllegalArgumentException("Nivel de gravedad desconocido: " + nivelGravedad);
		}
		return nivelGravedad;
	}
	
	
	public Paciente {
		if(nivelGravedad < 0 || nivelGravedad > 5) {
			throw new IllegalArgumentException("El nivel de gravedad debe ser entre 0 y 5.");
		}
	}
	
	public static Paciente registrarPaciente (String rut, String nombre, String gradoGravedad){
		int nivelGravedadNumerica = parseGradoGravedad(gradoGravedad);
		return new Paciente(rut, nombre, nivelGravedadNumerica, null);
		
	}
}
