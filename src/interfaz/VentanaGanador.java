package interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
//import interfaz.InterfazNodoku;

public class VentanaGanador extends JFrame {
	private static final long serialVersionUID = 4351125225212137569L;
	private JPanel contentPane;
	private JLabel tiempoLabel;
	private JTable tablaRanking;
	private DefaultTableModel modeloRanking;
	private InterfazNodoku interfaz;
	private boolean registrado;

	private final int NOMBRE_JUGADOR = 0;
	private final int TIEMPO_MS = 1;
	
	/**
	 * Create the frame.
	 */
	public VentanaGanador(InterfazNodoku interfaz) {		
		this.interfaz = interfaz;
		setResizable(false);
		registrado = false;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(centroHorizontalSegunAncho(450) , 
				centroVerticalSegunAlto(400) , 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel ganasteLabel = new JLabel("GANASTE!");
		ganasteLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		ganasteLabel.setBounds(134, 11, 147, 46);
		contentPane.add(ganasteLabel);
		
		JLabel tuTiempoLabel = new JLabel("Tu tiempo:");
		tuTiempoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tuTiempoLabel.setBounds(10, 69, 79, 20);
		contentPane.add(tuTiempoLabel);
		
		tiempoLabel = new JLabel("00:00:00");
		tiempoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tiempoLabel.setBounds(85, 69, 58, 20);
		contentPane.add(tiempoLabel);
		
		JScrollPane scrollRanking = new JScrollPane();
		scrollRanking.setBounds(10, 105, 400, 186);
		contentPane.add(scrollRanking);
		
		inicializarTablaRanking();
		
		scrollRanking.setViewportView(tablaRanking);
		
		JLabel rankingLabel = new JLabel("Ranking");
		rankingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rankingLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rankingLabel.setBounds(163, 81, 74, 19);
		contentPane.add(rankingLabel);
		
		JButton btnNuevoJuego = new JButton("Nuevo Juego");
		btnNuevoJuego.setBounds(85, 300, 114, 23);
		btnNuevoJuego.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfaz.ventanaPrincipal.setEnabled(true);
				interfaz.nuevoJuego();
			}
		});
		contentPane.add(btnNuevoJuego);
		
		JButton btnTerminarJuego = new JButton("Terminar Juego");
		btnTerminarJuego.setBounds(209, 300, 114, 23);
		btnTerminarJuego.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfaz.salir();
			}
		});
		contentPane.add(btnTerminarJuego);
		
		JButton btnRegistrarseAlRanking = new JButton("Registrarse al Ranking");
		btnRegistrarseAlRanking.setBounds(263, 69, 147, 23);
		btnRegistrarseAlRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!registrado) {					
					interfaz.ventanaGanador.setEnabled(false);
					interfaz.registroRanking.setVisible(true);
				}
			}
		});
		contentPane.add(btnRegistrarseAlRanking);
		
		this.setVisible(false);
		this.setAlwaysOnTop(true);
	}

	int centroHorizontalSegunAncho(int ancho)
	{
		return (interfaz.getAnchoPantalla() - ancho) / 2;
	}
	
	int centroVerticalSegunAlto(int alto)
	{
		return (interfaz.getAltoPantalla() - alto) / 2;
	}

	public void setTiempo(Long ms) {
		tiempoLabel.setText(milisegundosAhms(ms));
	}

	public void registroExitoso() {
		registrado = true;
	}
	
	private void inicializarTablaRanking() {
		tablaRanking = new JTable();
		tablaRanking.setBounds(10, 105, 400, 120);
		
		modeloRanking = new DefaultTableModel();
		modeloRanking.addColumn("Jugador");
		modeloRanking.addColumn("Tiempo");
		
		int posicion = 1;
		for (String[] parJugadorTiempo : interfaz.juego.getTop10Ranking()) {
			modeloRanking.addRow(new String[] 
					{String.format("%dº. %s", posicion, parJugadorTiempo[NOMBRE_JUGADOR]), 
							milisegundosAhms(Long.parseLong(parJugadorTiempo[TIEMPO_MS]))});
			posicion++;
		}
		
		tablaRanking.setModel(modeloRanking);
	}
	
	private static String milisegundosAhms(Long ms) {
		Long segundos = ms / 1000;
		Long minutos = segundos / 60; segundos = segundos % 60;
		Long horas = minutos / 60; minutos = minutos % 60;
		
		return String.format("%02d:%02d:%02d", horas, minutos, segundos);
	}
}
