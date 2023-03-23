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
	private JTextField textField;
	private InterfazNodoku interfaz;

	/**
	 * Create the dialog.
	 */
	public RegistroRanking(InterfazNodoku interfaz) {
		this.interfaz = interfaz;
		setResizable(false);
		setBounds(100, 100, 321, 120);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 305, 81);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		
		this.setVisible(false);
		
		contentPanel.setLayout(null);
		{
			JButton btnListo = new JButton("Listo");
			btnListo.setBounds(50, 45, 89, 23);
			contentPanel.add(btnListo);
		}
		
		JLabel lblIngreseSuNombre = new JLabel("Ingrese su nombre:");
		lblIngreseSuNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIngreseSuNombre.setBounds(10, 11, 141, 23);
		contentPanel.add(lblIngreseSuNombre);
		
		textField = new JTextField();
		textField.setBounds(148, 11, 141, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(158, 45, 89, 23);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfaz.ventanaGanador.setEnabled(true);
				interfaz.registroRanking.setVisible(false);
			}
		});
		contentPanel.add(btnCancelar);
	}
}
