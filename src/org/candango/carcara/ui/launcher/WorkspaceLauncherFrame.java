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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class WorkspaceLauncherFrame extends JFrame {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 917990959744139156L;
	
	/**
	 * Frame title constant
	 */
	public static final String FRAME_TITLE = "Workspace Launcher"; 
	
	/**
	 * Default constructor
	 */
	public WorkspaceLauncherFrame() {
		
		super( FRAME_TITLE );
		
		setSize( new Dimension( 528, 322 ) );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		setLocation( getCenteredCorner() );
		
		// building north pane
		createNorthPane();
		
		// creating center pane
		createCenterPane();
		
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
		JTextArea workspaceInfoTextArea = new JTextArea( "   Carcara Modelling Tool stores your " + 
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
		
		c.gridx = 0;
		c.gridy = 0;
		
		pane.add( workspaceLabel, c );
		
		JComboBox workspacePathComboBox = new JComboBox();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 300;
		c.gridx = 1;
		c.gridy = 0;
		
		pane.add( workspacePathComboBox, c );
		
		JButton browserButton = new JButton( "Browse..." );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 5;
		c.gridx = 2;
		c.gridy = 0;
		
		pane.add( browserButton, c );
		
		add( pane, BorderLayout.CENTER );
	}
	
	private void createSouthPane() {
		JPanel pane = new JPanel();
		
		pane.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(10, 2, 10, 2);
		
		JLabel spacerLabel = new JLabel( );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 346;
		c.gridx = 0;
		c.gridy = 0;
		
		pane.add( spacerLabel, c );
		
		JButton okButton = new JButton( "Ok" );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 21;
		c.gridx = 1;
		c.gridy = 0;
		
		pane.add( okButton, c );
		
		JButton cancelButton = new JButton( "Cancel" );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 4;
		c.gridx = 2;
		c.gridy = 0;
		
		pane.add( cancelButton, c );
		
		add( pane, BorderLayout.SOUTH );
	}
	
}