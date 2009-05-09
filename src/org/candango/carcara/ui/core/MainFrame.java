package org.candango.carcara.ui.core;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	public static final int NO_FILES_OPENED = 0;
	
	public static final int FILE_OPENED = 1;
	
	private int fileState = 0;
	
	/**
	 * This is a generated serial version UID
	 */
	private static final long serialVersionUID = 2571556063143479411L;
	
	/**
	 * Application title constant
	 */
	private static final String APP_TITLE = "Candango Caracara Wireframer Application";
	
	private MainSplitPane splitPane;
	
	private MainMenuBar menuBar;
	
	/**
	 * Main constructor
	 */
	public MainFrame() {
		
		super( APP_TITLE );
		
		setSize( new Dimension( 800, 600 ) );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		
        splitPane = new MainSplitPane( this );
		menuBar = new MainMenuBar( this );
		
		menuBar.updateMenuState();
		
		add( splitPane );
		
		setJMenuBar( menuBar );
		
		setVisible( true );
		
		update();
		
	}

	public int getFileState() {
		return fileState;
	}

	public void setFileState(int fileState) {
		this.fileState = fileState;
	}
	
	public void update() {
		splitPane.updateTreeState();
		menuBar.updateMenuState();
	}
	
	public ProjectTree getProjectTree() {
		return splitPane.getProjectTree();
	}
	
}