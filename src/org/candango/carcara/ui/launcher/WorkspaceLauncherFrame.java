/* WorkspaceLauncherFrame - WorkspaceLauncherFrame.java
 * 
 * This screen handles the workspace creating and selection and launches the 
 * selected in the workbench.
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
 * The Original Code is Carcara "a Candango Modeling tool to create 
 * myFuses/iflux applications" part .
 * 
 * The Initial Developer of the Original Code is Flavio Goncalves Garcia.
 * Portions created by Flavio Goncalves Garcia are Copyright (C) 2008 - 2009.
 * All Rights Reserved.
 * 
 * Contributor(s): Flavio Goncalves Garcia.
 */
package org.candango.carcara.ui.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import org.candango.carcara.MainApp;
import org.candango.carcara.model.environment.WorkspaceReference;

/**
 * This screen handles the workspace creating and selection and launches the 
 * selected in the workbench.
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 */
public class WorkspaceLauncherFrame extends JFrame implements ActionListener {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 917990959744139156L;
	
	/**
	 * Frame title constant
	 */
	public static final String FRAME_TITLE = "Workspace Launcher"; 
	
	private JComboBox workspacePathComboBox;
	
	private JButton browserButton;
	
	/**
	 * Default constructor
	 */
	public WorkspaceLauncherFrame() {
		
		super( FRAME_TITLE );
		
		setResizable( false );
		
		setSize( new Dimension( 528, 322 ) );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		setLocation( getCenteredCorner() );
		
		// building north pane
		createNorthPane();
		
		// creating center pane
		createCenterPane();
		
		updateWorkspacePathComboBox();
		
		// creating south pane
		createSouthPane();
		
	}
	
	private Point getCenteredCorner() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point(
            (int)((dim.getWidth() - getWidth()) / 2),
            (int)((dim.getHeight() - getHeight()) / 2)
        );
    }
	
	private void createNorthPane() {
		JPanel pane = new JPanel();
		
		pane.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		
		// Creating workspace command label
		JLabel selectWorkspaceLabel = new JLabel( "  Select a Workspace" );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 15;
		c.ipadx = 390;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		
		pane.add( selectWorkspaceLabel, c );
		
		// creating workspace info text area
		JTextArea workspaceInfoTextArea = new JTextArea( 
				"   Carcara Modeling Tool stores your " + 
				"projects in a folder called a workspace.\n   " + 
				"Choose a workspace folder to use for this session." );
		
		workspaceInfoTextArea.setOpaque( false );
		
		workspaceInfoTextArea.setEditable( false );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 5;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		
		pane.add( workspaceInfoTextArea, c );
		
		// creating separator
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 3;
		
		JSeparator separator = new JSeparator();
		
		separator.setForeground( Color.GRAY );
		
		pane.add( separator, c );
		
		pane.setBackground( Color.WHITE );
		
		add( pane, BorderLayout.NORTH );
	}
	
	
	private void createCenterPane() {
		JPanel pane = new JPanel();
		
		pane.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(2, 2, 2, 2);
		
		JLabel workspaceLabel = new JLabel( "Workspace:" );
		
		Font font = new Font( workspaceLabel.getFont().getName(), 
				Font.PLAIN, workspaceLabel.getFont().getSize() );
		
		workspaceLabel.setFont( font );
		
		c.fill = GridBagConstraints.HORIZONTAL;
//		c.ipady = 20;
		c.gridx = 0;
		c.gridy = 0;
		
		pane.add( workspaceLabel, c );
		
		workspacePathComboBox = new JComboBox();
		
		workspacePathComboBox.setEditable( true );
		
		// Fixing combo box size
		workspacePathComboBox.setMinimumSize( new Dimension( 1, 26) );
		
		workspacePathComboBox.setPreferredSize( new Dimension( 
				workspacePathComboBox.getPreferredSize().width,
				workspacePathComboBox.getPreferredSize().height ) );
		//END Fixing combo box size
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 300;
		c.gridx = 1;
		c.gridy = 0;
		
		pane.add( workspacePathComboBox, c );
		
		browserButton = new JButton( "Browse..." );
		
		browserButton.setActionCommand( "BROWSE" );
		
		browserButton.addActionListener( this );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 5;
		c.gridx = 2;
		c.gridy = 0;
		
		pane.add( browserButton, c );
		
		JLabel spacerLabel = new JLabel();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 70;
		c.gridx = 0;
		c.gridy = 1;
		
		pane.add( spacerLabel, c );
		
		
		
		add( pane, BorderLayout.CENTER );
	}
	
	private void createSouthPane() {
		JPanel pane = new JPanel();
		
		pane.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(2, 2, 14, 2);
		
		JCheckBox defaultWorkspaceCheckBox = new JCheckBox( 
				"Use this as the default and do not ask again" );
		
		Font font1 = new Font( defaultWorkspaceCheckBox.getFont().getName(), 
				Font.PLAIN, defaultWorkspaceCheckBox.getFont().getSize() );
		
		defaultWorkspaceCheckBox.setFont( font1 );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		
		pane.add( defaultWorkspaceCheckBox, c );
		
		JLabel spacerLabel = new JLabel( );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 60;
		c.gridx = 1;
		c.gridy = 1;
		
		pane.add( spacerLabel, c );
		
		JButton okButton = new JButton( "Ok" );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 30;
		c.gridx = 2;
		c.gridy = 1;
		
		pane.add( okButton, c );
		
		JButton cancelButton = new JButton( "Cancel" );
		
		cancelButton.setActionCommand( "CANCEL" );
		
		cancelButton.addActionListener( this );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 8;
		c.gridx = 3;
		c.gridy = 1;
		
		pane.add( cancelButton, c );
		
		add( pane, BorderLayout.SOUTH );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		
		// do browse action
		if( e.getActionCommand().equals( "BROWSE" ) ){
			JFileChooser fileChooser = new JFileChooser();
			
			fileChooser.setCurrentDirectory( new File( 
					MainApp.getUserHomePath() ) );
			
			fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
			
			int returnVal = fileChooser.showOpenDialog( this );
			
			updateWorkspacePathComboBox();
			
			if( returnVal == JFileChooser.APPROVE_OPTION ) {
				workspacePathComboBox.addItem( 
						fileChooser.getSelectedFile().getAbsolutePath() );
				
				workspacePathComboBox.setSelectedItem( 
						fileChooser.getSelectedFile().getAbsolutePath() );
			}
		}
		else if( e.getActionCommand().equals( "CANCEL" )  ){
			System.exit( 0 );
		}
		
	}
	
	private void updateWorkspacePathComboBox() {
		workspacePathComboBox.removeAllItems();
		for( WorkspaceReference reference : 
			MainApp.getEnvironment().getReferences() ){
			workspacePathComboBox.addItem( reference.getPath() );
		}
	}
	
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */