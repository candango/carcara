package org.candango.carcara.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class MainMenuBar extends JMenuBar implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3279898217783003530L;

	private JMenu menu, submenu;
	
	private JRadioButtonMenuItem rbMenuItem;

	public MainMenuBar() {
		super();

		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		
		menu.add( createMenuItem( "New..." , KeyEvent.VK_N, "FILE_NEW", 
				KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK + 
						ActionEvent.ALT_MASK ) ) );

		add(menu);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand() == "FILE_NEW" ){
			JOptionPane.showMessageDialog( this, "[File -> New ...] Menu pressed", 
					"Main Menu", JOptionPane.INFORMATION_MESSAGE );
		}
		
		
	}
	
	private JMenuItem createMenuItem( String caption, int eventKey, 
			String actionCommand ) {
		JMenuItem menuItem = new JMenuItem( caption, eventKey );
		menuItem.setActionCommand( actionCommand );
		menuItem.addActionListener( this );
		
		return menuItem;
	}
	
	private JMenuItem createMenuItem( String caption, int eventKey, 
			String actionCommand, KeyStroke keystroke ){
		JMenuItem menuItem = createMenuItem( caption, eventKey, 
				actionCommand );
		
		menuItem.setAccelerator( keystroke );
		
		return menuItem;
	}
	
}