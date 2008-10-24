package org.candango.carcara.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JWindow;
import javax.swing.KeyStroke;

public class MainMenuBar extends JMenuBar implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3279898217783003530L;

	private JMenu menu, submenu;
	private JMenuItem menuItem;
	private JRadioButtonMenuItem rbMenuItem;

	public MainMenuBar() {
		super();

		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");

		menuItem = new JMenuItem( "New...", KeyEvent.VK_N );
		menuItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, 
				ActionEvent.SHIFT_MASK + ActionEvent.ALT_MASK ) );
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything" );
		
		menuItem.addActionListener(this );
		
		menu.add(menuItem);

		add(menu);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JOptionPane.showMessageDialog( this, e.getID(), "Apertando o new!!!", JOptionPane.INFORMATION_MESSAGE );
		
		
	}

}
