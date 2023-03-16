package nodoku;

import java.util.Random;

public class Nodoku {
	int anchoGrilla;
	int largoGrilla;
	int valorMaximoCelda;
	int[] sumasEsperadasPorFila;
	int[] sumasEsperadasPorColumna;
	
	private void generarJuego()
	{
		/* Genera las listas de sumas esperadas de modo que el juego tenga solución. */
		Random rand = new Random();
		
		this.sumasEsperadasPorColumna = Nodoku.instanciarArregloConCeros(anchoGrilla);
		this.sumasEsperadasPorFila = Nodoku.instanciarArregloConCeros(largoGrilla);
		
		for(int columna=0; columna<anchoGrilla; columna++) {
			for(int fila=0; fila<largoGrilla; fila++) {
				int numeroCelda = rand.nextInt(1, valorMaximoCelda+1);
				
				sumasEsperadasPorColumna[columna] += numeroCelda;
				sumasEsperadasPorFila[fila] += numeroCelda;
			}
		}
	}
	
	blabla()
	
	private static int[] instanciarArregloConCeros(int tamanio) {
		int[] nuevoArreglo = new int[tamanio];
		
		for (int i = 0; i < tamanio; i++) {
			nuevoArreglo[i] = 0;
		}
		return nuevoArreglo;
	}
}
