package nodoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Ranking {
	File archivoRankings;
	Map<Long, String> jugadoresEnElRankingPorTiempo;
	List<Long> mejoresTiempos;
	
	public Ranking() throws IOException {
		archivoRankings = new File("rankings.txt");
		
		boolean archivoCreado = archivoRankings.createNewFile();
		
		if (archivoCreado) {
			// El archivo no existia previamente.
			jugadoresEnElRankingPorTiempo = new HashMap<Long, String>();
			mejoresTiempos = new LinkedList<Long>();
		}
		else {
			// Ya existe un archivo con los rankings.
			leerRankings();
		}
	}

	private void leerRankings() throws IOException {
		/* Los rankings se guardan en un archivo donde cada línea sigue el 
		 * siguiente formato:
		 * <tiempo_de_completacion_en_milisegundos>,<nombre_jugador>*/
		Scanner s = new Scanner(archivoRankings);
		
		while (s.hasNextLine()) {
			Jugador j = new Jugador(s.nextLine().split(","));
			jugadoresEnElRankingPorTiempo.put(j.tiempoDeCompletación, j.nombre);
			mejoresTiempos.add(j.tiempoDeCompletación);
		}
	}
}

class Jugador {
	Long tiempoDeCompletación;
	String nombre;
	
	int TIEMPO = 0;
	int NOMBRE = 1;
	
	public Jugador(String[] tiempoYJugador) {
		tiempoDeCompletación = Long.parseLong(tiempoYJugador[TIEMPO]);
		nombre = tiempoYJugador[NOMBRE];
	}
}
