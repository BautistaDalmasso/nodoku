package interfaz;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class RegistroRanking extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nombreField;
	private InterfazNodoku interfaz;
	
	/**
	 * Create the dialog.
	 */
	public RegistroRanking(InterfazNodoku interfaz) {
		inicializarVentana(interfaz);
		
		crearLabelIngreseSuNombre();
		inicializarFieldParaNombre();
		
		crearBotonListo();
		crearBotonCancelar();
	}

	private void inicializarFieldParaNombre() {
		nombreField = new JTextField();
		nombreField.setBounds(148, 11, 141, 20);
		contentPanel.add(nombreField);
		nombreField.setColumns(10);
	}

	private void crearBotonCancelar() {
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(158, 45, 89, 23);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ocultarVentana();
			}
		});
		contentPanel.add(btnCancelar);
	}

	private void crearBotonListo() {
		JButton btnListo = new JButton("Listo");
		btnListo.setBounds(50, 45, 89, 23);
		btnListo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manejarRegistro();
			}
		});
		contentPanel.add(btnListo);
	}

	private void crearLabelIngreseSuNombre() {
		JLabel lblIngreseSuNombre = new JLabel("Ingrese su nombre:");
		lblIngreseSuNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIngreseSuNombre.setBounds(10, 11, 141, 23);
		contentPanel.add(lblIngreseSuNombre);
	}

	private void inicializarVentana(InterfazNodoku interfaz) {
		this.interfaz = interfaz;
		setResizable(false);
		this.setAlwaysOnTop(true);
	
		setBounds((interfaz.getAnchoPantalla() - 321) / 2, 
				(interfaz.getAltoPantalla() - 120) / 2, 321, 120);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 305, 81);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		
		this.setVisible(false);
		
		contentPanel.setLayout(null);
	}

	protected void manejarRegistro() {
		String nombre = nombreField.getText();
		
		if (!nombre.equals("")) {
			interfaz.juego.agregarAlRanking(nombre);
			
			interfaz.ventanaGanador.registroExitoso();
			ocultarVentana();
		}
	}
	
	protected void ocultarVentana() {
		interfaz.ventanaGanador.setEnabled(true);
		interfaz.registroRanking.setVisible(false);
	}
}
