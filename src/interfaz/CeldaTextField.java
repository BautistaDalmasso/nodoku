package interfaz;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CeldaTextField extends JTextField{
	/* JTextField utilizado para las celdas de un tablero de Nodoku. */

	private int x;
	private int y;
	
	private static final long serialVersionUID = 1L;
	
	public CeldaTextField(int x, int y) {
		super();
		
		this.x = x;
		this.y = y;
		super.setHorizontalAlignment(JTextField.CENTER);
		super.setBounds(x*50, y*50, 47, 47);
		
		agregarDocumentListeners();
	}

	private void agregarDocumentListeners() {
		CeldaTextField textField = this;
		super.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
			  // Llamado cada vez que el contenido de la celda cambia.
			  int valor = Integer.parseInt(textField.getText());
			  System.out.println("IMPLEMENTAME.");
			  System.out.println("TAMBIEN IMPLEMENTA ALGO PARA CHEQUEAR VALORES VALIDOS.");
		  }
		  public void removeUpdate(DocumentEvent e) {
			  changedUpdate(e);
		  }
		  public void insertUpdate(DocumentEvent e) {
			  changedUpdate(e);
		  }
		});
	}
}
