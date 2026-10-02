package core.escutrack.utils;

/* +++
 * Módulo de utilidad cuyo propósito es validar
 * cada parámetro tratado por el programa de tal forma
 * que NO permita ingresar información que no tiene un
 * formato válido y a la vez garantice el cumplimiento
 * de la jerarquía de responsabilidades de cada Clase
 * mediante encapsulamiento.
 *
 * Hacemos uso de regular expressions (regex) para
 * formatear de forma estricta la forma que deben tener
 * los parámemtros recibidos para ser considerados válidos.
 *
 * @author Felipe T.S.
 --- */

/**
 * Conjunto de validaciones estáticas de campos de entrada del usuario,
 * basadas en expresiones regulares. Cada método lanza
 * {@link IllegalArgumentException} cuando el valor recibido no cumple el
 * formato esperado, de modo que el resto del programa pueda asumir que los
 * datos ya validados son correctos.
 */
public class ValidadorCamposUtils {

    /**
     * Diccionario compacto que traduce el nivel de gravedad numérico
     * interno (usado como índice) a su representación textual. 
     */
	public static final String[] TRADUCTOR_GRAVEDAD = {
		"indefinido", // índice 0
		"estable",
		"moderado",
		"urgente",
		"severo",
		"critico"
	};
	
	/**
	 * Valida que el RUT tenga el formato {@code "12345678-9"} (sin puntos,
	 * con guion y dígito verificador numérico o 'K'/'k').
	 *
	 * @param rut valor a validar
	 * @throws IllegalArgumentException si el RUT es nulo, está vacío o no cumple el formato esperado
	 */
	public static void validarRut(String rut) throws IllegalArgumentException {
		if(rut == null || rut.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] El RUT no puede estar vacío.");
		}

		if(!rut.matches("^[0-9]{7,8}-[0-9Kk]$")) {
			throw new IllegalArgumentException("\t[!] Formato de RUT no válido. Use formato '12345678-9' sin puntos.");
		}
	}

	/**
	 * Valida que el nombre contenga únicamente letras (incluyendo tildes y
	 * ñ) y espacios.
	 *
	 * @param nombre valor a validar
	 * @throws IllegalArgumentException si el nombre es nulo, está vacío o contiene caracteres no permitidos
	 */
	public static void validarNombre(String nombre) throws IllegalArgumentException {
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] El nombre no puede estar vacío.");
		}

		if(!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
			throw new IllegalArgumentException("\t[!] El nombre contiene carácteres no permitidos. Use solo letras.");
		}
	}

	/**
	 * Valida que la gravedad corresponda a una de las etiquetas reconocidas
	 * por el sistema (estable, moderado, urgente, severo o critico),
	 * ignorando mayúsculas/minúsculas.
	 *
	 * @param gravedad valor a validar
	 * @throws IllegalArgumentException si la gravedad es nula, está vacía o no es una etiqueta reconocida
	 */
	public static void validarGravedad(int gravedad) throws IllegalArgumentException {
	    if(gravedad < 0 || gravedad >= TRADUCTOR_GRAVEDAD.length) {
	        throw new IllegalArgumentException("\t[!] Gravedad inválida. Ingrese un valor numérico entre 0 (indefinido) y 5 (crítico).");
	    }
	}

	/**
	 * Valida que el nombre del departamento contenga únicamente letras
	 * (incluyendo tildes y ñ) y espacios.
	 *
	 * @param depto valor a validar
	 * @throws IllegalArgumentException si el departamento es nulo, está vacío o contiene caracteres no permitidos
	 */
	public static void validarDepartamento(String depto) throws IllegalArgumentException {
		if(depto == null || depto.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] El departamento no puede estar vacío.");
		}

		if(!depto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
			throw new IllegalArgumentException("\t[!] El departamento contiene carácteres no permitidos. Use solo letras.");
		}
	}

	/**
	 * Valida que el ID de cama tenga el formato {@code "Letra-Numero"}
	 * (ej. {@code "A-01"}).
	 *
	 * @param idCama valor a validar
	 * @throws IllegalArgumentException si el ID es nulo, está vacío o no cumple el formato esperado
	 */
	public static void validarIdCama(String idCama) throws IllegalArgumentException {
		if(idCama == null || idCama.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] El ID de la cama no puede estar vacío.");
		}

		if(!idCama.matches("^[A-Z]+-[0-9]{2,}$")) {
			throw new IllegalArgumentException("\t[!] Formato de ID de Cama no válido. Utilice el formato 'Letra-Numero' (ej. A-01).");
		}
	}

	/**
	 * Valida que la prioridad de la cama sea un número entero positivo
	 * mayor a cero, expresado como texto.
	 *
	 * @param prioridadCama valor a validar
	 * @throws IllegalArgumentException si la prioridad es nula, está vacía o no es un entero positivo válido
	 */
	public static void validarPrioridadCama(String prioridadCama) throws IllegalArgumentException {
		if(prioridadCama == null || prioridadCama.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] La prioridad de la cama no puede estar vacía.");
		}
		if(!prioridadCama.matches("^[1-9][0-9]*$")) {
			throw new IllegalArgumentException("\t[!] Formato de prioridad no válido. Debe ser un número entero positivo mayor a cero.");
		}
	}
	
	public static int traducirGravedad(String gravedadTexto) throws IllegalArgumentException {
		if(gravedadTexto == null || gravedadTexto.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] La gravedad no puede estar vacía.");
		}
		
		String textoLimpio = gravedadTexto.trim().toLowerCase();
		
		// Iterar sobre diccionario para encontrar el índice que coincide
		for (int i = 1; i < TRADUCTOR_GRAVEDAD.length; i++) {
			if (TRADUCTOR_GRAVEDAD[i].equals(textoLimpio)) {
				return i;
			}
		}
		
		throw new IllegalArgumentException("\t[!] La gravedad debe ser: estable, moderado, urgente, severo o critico.");
	}
	/*s
	 * SOBRECARGA (Salida al Usuario): Recibe el índice numérico interno, 
	 * lo valida, y retorna su formato textual desde el diccionario central.
	 */
	public static String traducirGravedad(int nivelGravedad) throws IllegalArgumentException {
		validarGravedad(nivelGravedad);
		return TRADUCTOR_GRAVEDAD[nivelGravedad];
	}
	
}
