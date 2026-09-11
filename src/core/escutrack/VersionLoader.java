package core.escutrack;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Utilidad encargada de leer el número de versión actual del programa desde
 * el archivo {@code resources/version.txt}, incluido como recurso del
 * classpath (empaquetado junto a las clases compiladas).
 * <p>
 * Ese archivo es actualizado automáticamente en Windows por el script
 * {@code extract_version.bat} a partir del badge de versión del
 * {@code README.md}; en otros sistemas operativos se usa el último valor
 * guardado en el archivo.
 *
 * @author Felipe T.S.
 */
public class VersionLoader {
	/**
	 * Lee y devuelve el número de versión almacenado en
	 * {@code resources/version.txt}.
	 *
	 * @return el número de versión como texto, o un mensaje de error si el
	 *         archivo no existe o no pudo leerse correctamente
	 */
	public static String getVersion() {

		try (InputStream is = VersionLoader.class.getResourceAsStream("resources/version.txt")) {
			if (is == null) {
				return "Error: version.txt no fue encontrado.";
			}
			try (BufferedReader lectorTexto = new BufferedReader(new InputStreamReader(is))) {
				return lectorTexto.lines().collect(Collectors.joining()).trim();
			}
		}
		catch (Exception e) {
			return "Error: Lectura de version.txt incorrecta.";
		}

	}
}
