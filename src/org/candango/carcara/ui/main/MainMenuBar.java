/* MainMenuBar - MainMenuBar.java
 * 
 * Carcara application main menu bar. Contains all commands of application.
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * This product includes software developed by the Fusebox Corporation 
 * (http://www.fusebox.org/).
 * 
 * The Original Code is Carcara "a Candango Modelling tool to create 
 * myFuses/iflux applications" part .
 * 
 * The Initial Developer of the Original Code is Flavio Goncalves Garcia.
 * Portions created by Flavio Goncalves Garcia are Copyright (C) 2008 - 2009.
 * All Rights Reserved.
 * 
 * Contributor(s): Flavio Goncalves Garcia.
 */
package org.candango.carcara.ui.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.candango.carcara.MainApp;
import org.candango.carcara.engine.ProjectHandler;
import org.candango.carcara.model.environment.exception.ProjectAlreadyExistsException;
import org.candango.carcara.model.project.Project;
import org.candango.carcara.ui.wizard.NewProjectWizardDialog;

/**
 * Carcara application main menu bar. Contains all commands of application.
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 */
public class MainMenuBar extends JMenuBar implements ActionListener {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -3279898217783003530L;
	
	/**
	 * Menu and submenu references
	 */
	private JMenu menu, submenu;
	
	/**
	 * Main frame reference
	 */
	private MainFrame mainFrame;
	
	/**
	 * Main constructor
	 */
	public MainMenuBar( MainFrame mainFrame ) {
		super();
		
		this.mainFrame = mainFrame;
		
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

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed( ActionEvent e ) {
		
		if( e.getActionCommand() == "FILE_NEW" ) {
			
			NewProjectWizardDialog newFrame = new NewProjectWizardDialog();
			
			newFrame.setVisible( true );
			
			/*String projectName = JOptionPane.showInputDialog( "Project Name:" );
			
			if( projectName != null ) {
				
				Project project = ProjectHandler.createProject( projectName , 
						"org.candango.carcara.php.Project" );
				
				try {
					MainApp.getEnvironment().getWorkspace().addProject( 
							project );
				} 
				catch (ProjectAlreadyExistsException paee ) {
					JOptionPane.showMessageDialog( getMainFrame(), 
							"A project with name \"" + projectName + 
							"\" already exists in the workspace.", 
							"Creating Project", JOptionPane.ERROR_MESSAGE );
				}
				
				getMainFrame().update();
			}*/
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
			if( MainApp.getEnvironment().getWorkspace().getProjectCount() 
					> 0 ) {
				int wantExit = JOptionPane.showConfirmDialog( mainFrame , 
						"You have non saved projets. Do you really want " + 
						"to close the application?" );
				if( wantExit == 0 ) {
					System.exit( 0 );
				}
			}
			else {
				System.exit( 0 );
			}
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
				
				
				
				if( MainApp.getEnvironment().getWorkspace().getProjectCount() 
						== 0 ) {
					if( actionCommand == "FILE_SAVE" || 
							actionCommand == "FILE_SAVE_AS" ) {
						menu.getItem( i ).setEnabled( false );
					}
				}
				else {
					if( actionCommand == "FILE_SAVE" || 
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
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */