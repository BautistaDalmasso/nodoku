package interfaz;

import nodoku.Nodoku;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class InterfazNodoku {
	private Nodoku juego;
	private JFrame ventana_principal;
	private JTextField casilleros[][];
	private Label sumas_filas[];
	private Label sumas_columnas[];
//	private int grilla[][];
	private enum NivelJuego {Fácil, Medio, Difícil, Personalizado};
	private NivelJuego nivel;
	private final int TAMANIO_FACIL = 4;
	private final int TAMANIO_MEDIO = 6;
	private final int TAMANIO_DIFICIL = 8;
	private int tamanio_personalizado;
	private final int ALTO_VENTANA_FACIL = 310;
	private final int ANCHO_VENTANA_FACIL = 260;
	private final int ALTO_VENTANA_MEDIO = 410;
	private final int ANCHO_VENTANA_MEDIO = 360;
	private final int ALTO_VENTANA_DIFICIL = 510;
	private final int ANCHO_VENTANA_DIFICIL = 460;

	/**
		Lanza la aplicación
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					InterfazNodoku window = new InterfazNodoku();
					window.ventana_principal.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
		Crea la aplicación
	 */
	public InterfazNodoku()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {		
			e.printStackTrace();
		}
		initialize();
	}

	/*
	 	Inicializar contenido de la ventana
	 */
	private void initialize()
	{	
		// Setea la ventana del juego
		
		ventana_principal = new JFrame();
		ventana_principal.getContentPane().setLayout(null);
		ventana_principal.setResizable(false); // cambio de tamaño no permitido
		ventana_principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Al arrancar por primera vez, lo hace en modo fácil ************	
		nivel = NivelJuego.Fácil;
		nuevo_juego(nivel);
		// ***************************************************************
			
		JMenuBar barraMenu = new JMenuBar();
		ventana_principal.setJMenuBar(barraMenu);
		
		JMenu mnNuevo = new JMenu("Nuevo juego");
		barraMenu.add(mnNuevo);
		
		JMenuItem mnNuevoItemFacil = new JMenuItem("Fácil");
		mnNuevoItemFacil.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				limpiarVentana();
				nivel = NivelJuego.Fácil;
				nuevo_juego(nivel);
			}
		});
		mnNuevo.add(mnNuevoItemFacil);
		
		JMenuItem mnNuevoItemMedio = new JMenuItem("Medio");
		mnNuevoItemMedio.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limpiarVentana();
				nivel = NivelJuego.Medio;
				nuevo_juego(nivel);
			}
		});
		mnNuevo.add(mnNuevoItemMedio);
		
		JMenuItem mnNuevoItemDificil = new JMenuItem("Difícil");
		mnNuevoItemDificil.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limpiarVentana();
				nivel = NivelJuego.Difícil;
				nuevo_juego(nivel);
			}
		});
		mnNuevo.add(mnNuevoItemDificil);
		
		JMenuItem mnNuevoItemPersonalizado = new JMenuItem("Personalizado");
		mnNuevoItemPersonalizado.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limpiarVentana();
				nivel = NivelJuego.Personalizado;
				nuevo_juego(nivel);
			}
		});
		mnNuevo.add(mnNuevoItemPersonalizado);
		
		JMenuItem mnNuevoItemSalir = new JMenuItem("Salir");
		mnNuevoItemSalir.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}	
		});
		mnNuevo.add(mnNuevoItemSalir);	
	}
	
	private void nuevo_juego(NivelJuego nivel)
	{	
		int tamanio;
		switch (nivel)
		{
		case Fácil:
			tamanio = TAMANIO_FACIL;
			break;
			
		case Medio:
			tamanio = TAMANIO_MEDIO;
			break;
		
		case Difícil:
			tamanio = TAMANIO_DIFICIL;
			break;
			
		case Personalizado:
			tamanio = get_tamanio_personalizado();
			break;
			
		default: throw new RuntimeException("Error de tamaño de ventana");
		}
		
		juego = new Nodoku(tamanio);
		setear_ventana();
		crear_casilleros(tamanio);
		mostrar_consigna();
	}
	
	// *************************************************
	
	private void setear_ventana()
	{
		// Lee la resolución de la pantalla del dispositivo
		Dimension resolucion_pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho_pantalla = resolucion_pantalla.width;
		int alto_pantalla  = resolucion_pantalla.height;
		// ************************************************
		
		int x, y, ancho = 0, alto = 0; // coordenadas y tamaño del Frame

		switch (nivel) // la ventana se crea según su tamaño y centrada
		{
		case Fácil:
			ancho = ANCHO_VENTANA_FACIL;
			alto = ALTO_VENTANA_FACIL;
			break;
			
		case Medio:
			ancho = ANCHO_VENTANA_MEDIO;
			alto = ALTO_VENTANA_MEDIO;
			break;
			
		case Difícil:
			ancho = ANCHO_VENTANA_DIFICIL;
			alto = ALTO_VENTANA_DIFICIL;
			break;
			
		case Personalizado:
			tamanio_personalizado = get_tamanio_personalizado();
			ancho = tamanio_personalizado * 50 + 60;
			alto = tamanio_personalizado * 50 + 110;
		}
		
		x = (ancho_pantalla - ancho)/2;
		y = (alto_pantalla - alto)/2;
		ventana_principal.setBounds(x, y, ancho, alto);	
	}
	
	private void crear_casilleros(int tamanio_grilla)
	{		
		casilleros = new JTextField[tamanio_grilla][tamanio_grilla];		
		for (int x = 0; x < tamanio_grilla; x++)
		{
			for (int y = 0; y < tamanio_grilla; y++)
			{
				casilleros[x][y] =  new JTextField();
				casilleros[x][y].setHorizontalAlignment(JTextField.CENTER);
			//	casilleros[x][y].setText("0");
				casilleros[x][y].setBounds(x*50, y*50, 47, 47);
				ventana_principal.getContentPane().add(casilleros[x][y]);
			}
		}
	}
	
	public void mostrar_consigna()
	{
		int columnas[] = juego.getSumasEsperadasPorColumna();
		int filas[] = juego.getSumasEsperadasPorFila();
		sumas_columnas = new Label[columnas.length];
		sumas_filas = new Label[filas.length];
		
		for (int c = 0; c < columnas.length; c ++)
		{
			sumas_columnas[c] = new Label(Integer.toString(filas[c]));
			sumas_columnas[c].setBounds(filas.length * 50, c * 50, 50, 50);
			sumas_columnas[c].setAlignment(1);
			ventana_principal.getContentPane().add(sumas_columnas[c]);
		}
		
		for (int f = 0; f < filas.length; f ++)
		{
			sumas_filas[f] = new Label(Integer.toString(columnas[f]));
			sumas_filas[f].setBounds(f * 50, columnas.length * 50, 50, 50);
			sumas_filas[f].setAlignment(1);
			ventana_principal.getContentPane().add(sumas_filas[f]);
		}
	}
	
	private int get_tamanio_personalizado() // A futuro debe preguntar al usuario el tamaño
	{
		return 7;
	}
	
	private void limpiarVentana() // Borra pantalla del juego anterior
	{
		borrar_casilleros();
		borrar_consigna();
	}
	
	private void borrar_casilleros()
	{
		for (int x = 0; x < casilleros.length; x++)
		{
			for (int y = 0; y < casilleros.length; y++)
			{
				ventana_principal.getContentPane().remove(casilleros[x][y]);
			}
		}	
	}
	
	public void borrar_consigna()
	{
		for (int c = 0; c < sumas_columnas.length; c ++)
		{
			ventana_principal.getContentPane().remove(sumas_columnas[c]);
		}
		
		for (int f = 0; f < sumas_filas.length; f ++)
		{
			ventana_principal.getContentPane().remove(sumas_filas[f]);
		}
		
	}
}