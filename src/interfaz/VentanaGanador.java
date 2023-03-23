package interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class VentanaGanador extends JFrame {

	private JPanel contentPane;
	private JLabel tiempoLabel;

	/**
	 * Create the frame.
	 */
	public VentanaGanador() {
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
		
		this.setVisible(false);
		this.setAlwaysOnTop(true);
	}

	private void mostrarTiempo(int ms) {
		int segundos = ms / 1000;
		int minutos = segundos / 60; segundos = segundos % 60;
		int horas = minutos / 60; minutos = minutos % 60;
		
		tiempoLabel.setText("" + horas + ":" + minutos + ":" + segundos);
	}
}
