package org.candango.carcara.ui.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		
		JPanel pane = new JPanel();
		
		pane.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel selectWorkspaceLabel = new JLabel( "  Select a Workspace" );
		
		JTextArea workspaceInfoTextArea = new JTextArea( "   Carcara Modelling Tool stores your " + 
				"projects in a folder called a workspace.\n   " + 
				"Choose a workspace folder to use for this session." );
		
		workspaceInfoTextArea.setOpaque( false );
		
		workspaceInfoTextArea.setEditable( false );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 15;
		c.ipadx = 390;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		
		pane.add( selectWorkspaceLabel, c );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 5;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		
		pane.add( workspaceInfoTextArea, c );
		
		pane.setBackground( Color.WHITE );
		
		add( pane, BorderLayout.NORTH );
		
	}
	
	public Point getCenteredCorner() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point(
            (int)((dim.getWidth() - getWidth()) / 2),
            (int)((dim.getHeight() - getHeight()) / 2)
        );
    }
	
}