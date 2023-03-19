package interfaz;

import javax.swing.JTextField;

public class CeldaTextField extends JTextField{
	/* JTextField utilizado para las celdas de un tablero de Nodoku. */

	private static final long serialVersionUID = 1L;
	
	public CeldaTextField(int x, int y) {
		super();
		super.setHorizontalAlignment(JTextField.CENTER);
		super.setBounds(x*50, y*50, 47, 47);
	}
	
}
