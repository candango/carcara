package org.candango.carcara.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

public class MainMenuBar extends JMenuBar implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3279898217783003530L;

	private JMenu menu, submenu;
	
	private JRadioButtonMenuItem rbMenuItem;
	
	private MainFrame frame;
	
	/**
	 * Main constructor
	 */
	public MainMenuBar( MainFrame frame ) {
		super();
		
		this.frame = frame;
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		
		menu.add( createMenuItem( "New...", "FILE_NEW", KeyEvent.VK_N, 
				KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK + 
						ActionEvent.ALT_MASK ) ) );
		
		menu.add( createMenuItem( "Open File...", "FILE_OPEN_FILE", 
				KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_O, 
						ActionEvent.SHIFT_MASK + ActionEvent.ALT_MASK ) ) );
		
		menu.add( new JSeparator() );
		
		menu.add( createMenuItem( "Save", "FILE_SAVE", 
				KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, 
						ActionEvent.SHIFT_MASK + ActionEvent.ALT_MASK ) ) );
		
		menu.add( createMenuItem( "Save As...", "FILE_SAVE_AS", 
				KeyEvent.VK_A, KeyStroke.getKeyStroke(KeyEvent.VK_A, 
						ActionEvent.SHIFT_MASK + ActionEvent.ALT_MASK ) ) );
		
		menu.add( new JSeparator() );
		
		menu.add( createMenuItem( "Exit", "FILE_EXIT", 
				KeyEvent.VK_X, KeyStroke.getKeyStroke(KeyEvent.VK_F4, 
						ActionEvent.ALT_MASK ) ) );
		
		add( menu );

	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		
		if( e.getActionCommand() == "FILE_NEW" ){
			JOptionPane.showMessageDialog( this, "[File -> New...] Menu pressed", 
					"Main Menu", JOptionPane.INFORMATION_MESSAGE );
		}
		
		if( e.getActionCommand() == "FILE_OPEN_FILE" ){
			JOptionPane.showMessageDialog( this, "[File -> Open File...] Menu pressed", 
					"Main Menu", JOptionPane.INFORMATION_MESSAGE );
		}
		
		if( e.getActionCommand() == "FILE_SAVE" ){
			JOptionPane.showMessageDialog( this, "[File -> Save] Menu pressed", 
					"Main Menu", JOptionPane.INFORMATION_MESSAGE );
		}
		
		if( e.getActionCommand() == "FILE_SAVE_AS" ){
			JOptionPane.showMessageDialog( this, "[File -> Save As...] Menu pressed", 
					"Main Menu", JOptionPane.INFORMATION_MESSAGE );
		}
		
		if( e.getActionCommand() == "FILE_EXIT" ){
			System.exit( 0 );
		}
		
	}
	
	/**
	 * Create a new menu item
	 * 
	 * @param caption
	 * @param actionCommand
	 * @return
	 */
	private JMenuItem createMenuItem( String caption, String actionCommand ) {
		
		JMenuItem menuItem = new JMenuItem( caption );
		menuItem.setActionCommand( actionCommand );
		menuItem.addActionListener( this );
		
		return menuItem;
		
	}
	
	/**
	 * Create a new menu item plus eventKey
	 * 
	 * @param caption
	 * @param eventKey
	 * @param actionCommand
	 * @return
	 */
	private JMenuItem createMenuItem( String caption, String actionCommand, 
			int mnemonic ) {
		JMenuItem menuItem = createMenuItem(caption, actionCommand);
		menuItem.setMnemonic( mnemonic );
		menuItem.setActionCommand( actionCommand );
		
		return menuItem;
	}
	
	/**
	 * Create new menu item plus eventKey and mnemonic
	 * 
	 * @param caption
	 * @param actionCommand
	 * @param mnemonic
	 * @param keystroke
	 * @return
	 */
	private JMenuItem createMenuItem( String caption, String actionCommand, 
			int mnemonic, KeyStroke keystroke ){
		
		JMenuItem menuItem = createMenuItem( caption, actionCommand, mnemonic );
		
		menuItem.setAccelerator( keystroke );
		
		return menuItem;
	}
	
	public void updateMenuState() {
		
		for( int i = 0; i < menu.getItemCount(); i ++ ) {
			try {
				String actionCommand = menu.getItem( i ).getActionCommand(); 
				
				if( this.getFrame().getFileState() == MainFrame.NO_FILES_OPENED ) {
					if( actionCommand == "FILE_OPEN_FILE" || 
							actionCommand == "FILE_SAVE" || 
							actionCommand == "FILE_SAVE_AS" ) {
						menu.getItem( i ).setEnabled( false );
					}
				}
				else {
					if( actionCommand == "FILE_OPEN_FILE" || 
							actionCommand == "FILE_SAVE" || 
							actionCommand == "FILE_SAVE_AS" ) {
						menu.getItem( i ).setEnabled( true );
					}
				}
				
				
			}
			catch (NullPointerException e) {
				// avoiding separators
			}
		}
		
	}
	
	public MainFrame getFrame() {
		return frame;
	}
	
}