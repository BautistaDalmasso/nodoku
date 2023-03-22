package nodoku;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	private void leerRankings() {
		// TODO Auto-generated method stub
		
	}
}
