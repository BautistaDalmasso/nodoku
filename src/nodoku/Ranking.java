package nodoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
	
	public Ranking() {
		archivoRankings = new File("rankings.txt");
		
		boolean archivoCreado;
		try {
			archivoCreado = archivoRankings.createNewFile();
			if (archivoCreado) {
				// El archivo no existia previamente.
				jugadoresEnElRankingPorTiempo = new HashMap<Long, String>();
				mejoresTiempos = new LinkedList<Long>();
			}
			else {
				// Ya existe un archivo con los rankings.
				leerRankings();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void agregarJugador(Long tiempo, String nombre) {
		jugadoresEnElRankingPorTiempo.put(tiempo, nombre);
		mejoresTiempos.add(tiempo);
	}
	
	public void guardarRanking() {
		try {
			FileWriter fw = new FileWriter("rankings.txt");
			
			mejoresTiempos.sort(null);
			StringBuilder rankingAGuardar = new StringBuilder();
			for (int i=0; i<10 && i<mejoresTiempos.size(); i++) {
				// Solo guardamos los mejores 10 tiempos.
				Long tiempoDeCompletacion = mejoresTiempos.get(i);
				rankingAGuardar.append(tiempoDeCompletacion.toString() 
						+ "," + jugadoresEnElRankingPorTiempo.get(tiempoDeCompletacion) 
						+ "\n");
			}
			fw.write(rankingAGuardar.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private void leerRankings() throws IOException {
		/* Los rankings se guardan en un archivo donde cada línea sigue el 
		 * siguiente formato:
		 * <tiempo_de_completacion_en_milisegundos>,<nombre_jugador>*/
		Scanner s = new Scanner(archivoRankings);
		
		while (s.hasNextLine()) {
			Jugador j = new Jugador(s.nextLine().split(","));
			agregarJugador(j.tiempoDeCompletación, j.nombre);
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
