package interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import nodoku.Nodoku;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class VentanaGanador extends JFrame {

	private JPanel contentPane;
	private JLabel tiempoLabel;
	private JTable tablaRanking;
	private DefaultTableModel modeloRanking;
	private Nodoku juego;

	/**
	 * Create the frame.
	 */
	public VentanaGanador(Nodoku juego) {		
		this.juego = juego;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel ganasteLabel = new JLabel("GANASTE!");
		ganasteLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		ganasteLabel.setBounds(134, 11, 147, 46);
		contentPane.add(ganasteLabel);
		
		JLabel tuTiempoLabel = new JLabel("Tu tiempo:");
		tuTiempoLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tuTiempoLabel.setBounds(10, 63, 79, 31);
		contentPane.add(tuTiempoLabel);
		
		tiempoLabel = new JLabel("00:00:00");
		tiempoLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tiempoLabel.setBounds(85, 69, 58, 19);
		contentPane.add(tiempoLabel);
		
		JScrollPane scrollRanking = new JScrollPane();
		scrollRanking.setBounds(10, 105, 400, 120);
		contentPane.add(scrollRanking);
		
		inicializarTablaRanking();
		
		scrollRanking.setViewportView(tablaRanking);
		
		JLabel rankingLabel = new JLabel("Ranking");
		rankingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rankingLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rankingLabel.setBounds(163, 81, 74, 19);
		contentPane.add(rankingLabel);
		
		this.setVisible(false);
		this.setAlwaysOnTop(true);
	}

	public void setTiempo(Long ms) {
		Long segundos = ms / 1000;
		Long minutos = segundos / 60; segundos = segundos % 60;
		Long horas = minutos / 60; minutos = minutos % 60;
		
		tiempoLabel.setText(String.format("%02d:%02d:%02d", 
											horas, minutos, segundos));
	}

	private void inicializarTablaRanking() {
		tablaRanking = new JTable();
		tablaRanking.setBounds(10, 105, 400, 120);
		
		modeloRanking = new DefaultTableModel();
		modeloRanking.addColumn("Jugador");
		modeloRanking.addColumn("Tiempo");
		
		for (String[] parJugadorTiempo : juego.getTop10Ranking()) {
			modeloRanking.addRow(parJugadorTiempo);
		}
		
		tablaRanking.setModel(modeloRanking);
	}
}
