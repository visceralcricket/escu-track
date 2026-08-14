package core.escutrack;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class VersionLoader {
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
