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
	private JFrame ventanaPrincipal;
	private JTextField casilleros[][];
	private Label sumasEsperadasPorFila[];
	private Label sumasEsperadasPorColumna[];
//	private int grilla[][];
	private enum NivelJuego {Fácil, Medio, Difícil, Personalizado};
	private NivelJuego nivel;
	private final int TAMANIO_FACIL = 4;
	private final int TAMANIO_MEDIO = 6;
	private final int TAMANIO_DIFICIL = 8;
	private int tamanioPersonalizado;
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
					window.ventanaPrincipal.setVisible(true);
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
		
		ventanaPrincipal = new JFrame();
		ventanaPrincipal.getContentPane().setLayout(null);
		ventanaPrincipal.setResizable(false); // cambio de tamaño no permitido
		ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Al arrancar por primera vez, lo hace en modo fácil ************	
		nivel = NivelJuego.Fácil;
		nuevoJuego(nivel);
		// ***************************************************************
			
		JMenuBar barraMenu = new JMenuBar();
		ventanaPrincipal.setJMenuBar(barraMenu);
		
		JMenu mnNuevo = new JMenu("Nuevo juego");
		barraMenu.add(mnNuevo);
		
		JMenuItem mnNuevoItemFacil = new JMenuItem("Fácil");
		mnNuevoItemFacil.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				limpiarVentana();
				nivel = NivelJuego.Fácil;
				nuevoJuego(nivel);
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
				nuevoJuego(nivel);
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
				nuevoJuego(nivel);
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
				nuevoJuego(nivel);
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
	
	private void nuevoJuego(NivelJuego nivel)
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
			tamanio = getTamanioPersonalisado();
			break;
			
		default: throw new RuntimeException("Error de tamaño de ventana");
		}
		
		juego = new Nodoku(tamanio);
		setearVentana();
		crearCasilleros(tamanio);
		mostrarValoresEsperados();
	}
	
	// *************************************************
	
	private void setearVentana()
	{
		// Lee la resolución de la pantalla del dispositivo
		Dimension resolucionPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int anchoPantalla = resolucionPantalla.width;
		int altoPantalla  = resolucionPantalla.height;
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
			tamanioPersonalizado = getTamanioPersonalisado();
			ancho = tamanioPersonalizado * 50 + 60;
			alto = tamanioPersonalizado * 50 + 110;
		}
		
		x = (anchoPantalla - ancho)/2;
		y = (altoPantalla - alto)/2;
		ventanaPrincipal.setBounds(x, y, ancho, alto);	
	}
	
	private void crearCasilleros(int tamanio_grilla)
	{		
		casilleros = new JTextField[tamanio_grilla][tamanio_grilla];		
		for (int x = 0; x < tamanio_grilla; x++)
		{
			for (int y = 0; y < tamanio_grilla; y++)
			{
				casilleros[x][y] = new CeldaTextField(x, y);
				
				ventanaPrincipal.getContentPane().add(casilleros[x][y]);
			}
		}
	}
	
	public void mostrarValoresEsperados()
	{
		int columnas[] = juego.getSumasEsperadasPorColumna();
		int filas[] = juego.getSumasEsperadasPorFila();
		sumasEsperadasPorColumna = new Label[columnas.length];
		sumasEsperadasPorFila = new Label[filas.length];
		
		for (int c = 0; c < columnas.length; c ++)
		{
			sumasEsperadasPorColumna[c] = new Label(Integer.toString(filas[c]));
			sumasEsperadasPorColumna[c].setBounds(filas.length * 50, c * 50, 50, 50);
			sumasEsperadasPorColumna[c].setAlignment(1);
			ventanaPrincipal.getContentPane().add(sumasEsperadasPorColumna[c]);
		}
		
		for (int f = 0; f < filas.length; f ++)
		{
			sumasEsperadasPorFila[f] = new Label(Integer.toString(columnas[f]));
			sumasEsperadasPorFila[f].setBounds(f * 50, columnas.length * 50, 50, 50);
			sumasEsperadasPorFila[f].setAlignment(1);
			ventanaPrincipal.getContentPane().add(sumasEsperadasPorFila[f]);
		}
	}
	
	private int getTamanioPersonalisado() // A futuro debe preguntar al usuario el tamaño
	{
		return 7;
	}
	
	private void limpiarVentana() // Borra pantalla del juego anterior
	{
		borrarCasilleros();
		borrarConsigna();
	}
	
	private void borrarCasilleros()
	{
		for (int x = 0; x < casilleros.length; x++)
		{
			for (int y = 0; y < casilleros.length; y++)
			{
				ventanaPrincipal.getContentPane().remove(casilleros[x][y]);
			}
		}	
	}
	
	public void borrarConsigna()
	{
		for (int c = 0; c < sumasEsperadasPorColumna.length; c ++)
		{
			ventanaPrincipal.getContentPane().remove(sumasEsperadasPorColumna[c]);
		}
		
		for (int f = 0; f < sumasEsperadasPorFila.length; f ++)
		{
			ventanaPrincipal.getContentPane().remove(sumasEsperadasPorFila[f]);
		}
		
	}
}