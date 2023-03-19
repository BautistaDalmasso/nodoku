package nodoku;

import java.util.Random;

public class Nodoku {
	private int anchoGrilla;
	private int largoGrilla;
	private int valorMaximoCelda;
	private int[] sumasEsperadasPorFila;
	private int[] sumasEsperadasPorColumna;
	
	private final int VALOR_MAXIMO_DEFECTO = 4;
	
	public Nodoku(int tamanio) {
		/* Crea un tablero de Nodoku cuadrado de tamanio x tamanio. */
		anchoGrilla = tamanio;
		largoGrilla = tamanio;
		valorMaximoCelda = VALOR_MAXIMO_DEFECTO;
		
		generarJuego();
	}
	
	private void generarJuego()
	{
		/* Genera las listas de sumas esperadas de modo que el juego tenga solución. */
		Random rand = new Random();
		
		this.sumasEsperadasPorColumna = new int[anchoGrilla];
		this.sumasEsperadasPorFila = new int[largoGrilla];
		
		for(int columna=0; columna<anchoGrilla; columna++) {
			for(int fila=0; fila<largoGrilla; fila++) {
				int numeroCelda = rand.nextInt(1, valorMaximoCelda+1);
				
				sumasEsperadasPorColumna[columna] += numeroCelda;
				sumasEsperadasPorFila[fila] += numeroCelda;
			}
		}
	}

	public int[] getSumasEsperadasPorFila() {
		return sumasEsperadasPorFila;
	}

	public int[] getSumasEsperadasPorColumna() {
		return sumasEsperadasPorColumna;
	}
	
	
}
