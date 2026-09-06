package core.escutrack.utils;

/* +++
 * Archivo de utilidad cuyo único propósito es validar
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

public class ValidadorCamposUtils {
	
	public static void validarRut(String rut) throws IllegalArgumentException {
		if(rut == null || rut.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] El RUT no puede estar vacío.");
		}
		
		if(!rut.matches("^[0-9]{7,8}-[0-9Kk]$")) {
			throw new IllegalArgumentException("\t[!] Formato de RUT no válido. Use formato '12345678-9' sin puntos.");
		}
	}
	
	public static void validarNombre(String nombre) throws IllegalArgumentException {
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] El nombre no puede estar vacío.");
		}
		
		if(!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
			throw new IllegalArgumentException("\t[!] El nombre contiene carácteres no permitidos. Use solo letras.");
		}
	}
	
	public static void validarGravedad(String gravedad) throws IllegalArgumentException {
		if(gravedad == null || gravedad.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] La gravedad no puede estar vacía.");
		}
		
		if(!gravedad.matches("^[1-5]$")) {
			throw new IllegalArgumentException("\t[!] La gravedad debe ser un número entero entre 1 y 5.");
		}
	}
	
	public static void validarDepartamento(String depto) throws IllegalArgumentException {
		if(depto == null || depto.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] El departamento no puede estar vacío.");
		}
		
		if(!depto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\\\s]+$")) {
			throw new IllegalArgumentException("\t[!] El departamento contiene carácteres no permitidos. Use solo letras.");
		}
	}
	
	public static void validarIdCama(String idCama) throws IllegalArgumentException {
		if(idCama == null || idCama.trim().isEmpty()) {
			throw new IllegalArgumentException("\t[!] El ID de la cama no puede estar vacío.");
		}
		
		if(!idCama.matches("^[A-Z]+-[0-9]{2,}$")) {
			throw new IllegalArgumentException("\t[!] Formato de ID de Cama no válido. Utilice el formato 'Letra-Numero' (ej. A-01).");
		}
	}
}
