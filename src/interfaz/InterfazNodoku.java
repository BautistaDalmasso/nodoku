package interfaz;

import nodoku.Nodoku;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class InterfazNodoku {
	private Nodoku juego;
	private JFrame ventanaPrincipal;
	private JTextField casilleros[][];
	private String cadenaDigitosValidos;
	private Label sumasEsperadasPorFila[];
	private Label sumasEsperadasPorColumna[];
	private boolean filasResueltas[];
	private boolean columnasResueltas[];
	
	private final Color COLOR_CORRECTO = Color.green;
	private final Color COLOR_DEFAULT = Color.white;
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
		nuevoJuego(TAMANIO_FACIL, ANCHO_VENTANA_FACIL, ALTO_VENTANA_FACIL);
		cadenaDigitosValidos = digitosValidos();
		
		// Crea barra menú ***********************************************
		JMenuBar barraMenu = new JMenuBar();
		ventanaPrincipal.setJMenuBar(barraMenu);
		// Crea  menú desplegable Nuevo **********************************
		barraMenu.add(crearMenuDesplegable());
	}
	
	private JMenu crearMenuDesplegable() {
		/* Crea un menu deplegable con opciones. */
		
		JMenu mnNuevo = new JMenu("Nuevo juego");
		JMenuItem mnNuevoItemFacil = new JMenuItem("Fácil");
		mnNuevoItemFacil.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				limpiarVentana();
				nuevoJuego(TAMANIO_FACIL, ANCHO_VENTANA_FACIL, ALTO_VENTANA_FACIL);
			}
		});
		mnNuevo.add(mnNuevoItemFacil);
		
		JMenuItem mnNuevoItemMedio = new JMenuItem("Medio");
		mnNuevoItemMedio.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limpiarVentana();
				nuevoJuego(TAMANIO_MEDIO, ANCHO_VENTANA_MEDIO, ALTO_VENTANA_MEDIO);
			}
		});
		mnNuevo.add(mnNuevoItemMedio);
		
		JMenuItem mnNuevoItemDificil = new JMenuItem("Difícil");
		mnNuevoItemDificil.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limpiarVentana();
				nuevoJuego(TAMANIO_DIFICIL, ANCHO_VENTANA_DIFICIL, ALTO_VENTANA_DIFICIL);
			}
		});
		mnNuevo.add(mnNuevoItemDificil);
		
		JMenuItem mnNuevoItemPersonalizado = new JMenuItem("Personalizado");
		mnNuevoItemPersonalizado.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limpiarVentana();
				// TODO: reemplazar.
				int tamanio = getTamanioPersonalizado();
				nuevoJuego(tamanio, tamanio*50+60, tamanio*50+110);
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
		
		return mnNuevo;
	}
	
	private void nuevoJuego(int tamanio, int ancho, int alto)
	{	
		juego = new Nodoku(tamanio);
		filasResueltas = new boolean[tamanio];
		columnasResueltas = new boolean[tamanio];
		setearVentana(ancho, alto);
		crearCasilleros(tamanio);
		mostrarValoresEsperados();
	}

	private void setearVentana(int ancho, int alto)
	{
		centrar_ventana(ventanaPrincipal, ancho, alto);
	}
	
	private void centrar_ventana(JFrame ventana, int ancho, int alto)
	{
		int ancho_pantalla = getAnchoPantalla();
		int alto_pantalla  = getAltoPantalla();
		int x = (ancho_pantalla - ancho)/2;
		int y = (alto_pantalla - alto)/2;
		ventana.setBounds(x, y, ancho, alto);
	}
	
	private void crearCasilleros(int tamanio_grilla)
	{	
		try {
			MaskFormatter formato;
			formato = new MaskFormatter("#"); // sólo un dígito
			String numerosValidos = digitosValidos();
			formato.setValidCharacters(numerosValidos);	
			casilleros = new JFormattedTextField[tamanio_grilla][tamanio_grilla];
	
			for (int x = 0; x < tamanio_grilla; x++)
			{
				for (int y = 0; y < tamanio_grilla; y++)
				{
					casilleros[y][x] = crearCelda(x, y, formato);
					ventanaPrincipal.getContentPane().add(casilleros[y][x]);
				}
			}
		}
		catch (Exception e){}
	}
	
	private void cambiarValorGrilla(int valor, int x, int y) {
		juego.cambiarValorGrilla(valor, x, y);
		
		if (juego.filaEstaResuelta(y)) {
			System.out.println("Fila resuelta.");
			filasResueltas[y] = true;
			setColorFila(y, true);
		} else {
			filasResueltas[y] = false;
			setColorFila(y, false);
		}
		if (juego.columnaEstaResuelta(x)) {
			System.out.println("Columna resuelta.");
			columnasResueltas[x] = true;
			setColorColumna(x, true);
		} else {
			columnasResueltas[x] = false;
			setColorColumna(x, false);
		}
		
		if (juego.getEstaResuelto())
		{
			System.out.println("GANASTE!!!!");
		}
	}
	
	private void setColorFila(int y, boolean sumaCorrecta)
	{
		for (int x = 0; x < casilleros[y].length; x++)
		{
			if (sumaCorrecta) {
				casilleros[y][x].setBackground(COLOR_CORRECTO);
			} else {
				if (!columnasResueltas[x])
				{
					casilleros[y][x].setBackground(COLOR_DEFAULT);
				}
			}
		}
		
	}
	
	private void setColorColumna(int x, boolean sumaCorrecta)
	{
		 	for (int y = 0; y < casilleros.length; y++)
			{
				if (sumaCorrecta)
				{
					casilleros[y][x].setBackground(COLOR_CORRECTO);
				} else {
					if (!filasResueltas[y])
					{
						casilleros[y][x].setBackground(COLOR_DEFAULT);
					}
				}
			}
	}


	private JFormattedTextField crearCelda(int x, int y, MaskFormatter formato) {
		JFormattedTextField celda = new JFormattedTextField(formato);
		
		celda.setHorizontalAlignment(JTextField.CENTER);
		celda.setBounds(x*50, y*50, 47, 47);
		celda.setFont(new Font("Arial", Font.PLAIN, 17));
		agregarDocumentListener(celda, x, y);
		
		return celda;
	}
	
	private void agregarDocumentListener(JTextField celda, int x, int y) {
		celda.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// Llamado cada vez que el contenido de una celda cambia.
				if (!(celda.getText().equals(" ") || (celda.getText().equals(""))))
				{
					int valor = Integer.parseInt(celda.getText());
					cambiarValorGrilla(valor, x, y);
				}
				
//				System.out.println("TAMBIEN IMPLEMENTA ALGO PARA CHEQUEAR VALORES VALIDOS.");
			}
		});
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
			sumasEsperadasPorColumna[c].setFont(new Font("Arial", Font.PLAIN, 17));
			ventanaPrincipal.getContentPane().add(sumasEsperadasPorColumna[c]);
		}
		
		for (int f = 0; f < filas.length; f ++)
		{
			sumasEsperadasPorFila[f] = new Label(Integer.toString(columnas[f]));
			sumasEsperadasPorFila[f].setBounds(f * 50, columnas.length * 50, 50, 50);
			sumasEsperadasPorFila[f].setAlignment(1);
			sumasEsperadasPorFila[f].setFont(new Font("Arial", Font.PLAIN, 17));
			ventanaPrincipal.getContentPane().add(sumasEsperadasPorFila[f]);
		}
	}
	
	private int getTamanioPersonalizado() // A futuro debe preguntar al usuario el tamaño
	{
		return 7;
	}
	
	private String digitosValidos()
	{
		StringBuilder cadena = new StringBuilder();
		for (int i = 1; i <= juego.getValorMaximoCelda(); i++)
		{
			cadena.append(i);
		}
		return cadena.toString();
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
				ventanaPrincipal.getContentPane().remove(casilleros[y][x]);
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
	
	private int getAnchoPantalla()
	{
		// Lee el ancho de la resolución de la pantalla del dispositivo
		Dimension resolucionPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		return resolucionPantalla.width;
	}	
	
	private int getAltoPantalla()
	{
		// Lee el ancho de la resolución de la pantalla del dispositivo
		Dimension resolucionPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		return resolucionPantalla.height;
	}	
}
