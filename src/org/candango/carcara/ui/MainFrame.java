package org.candango.carcara.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MainFrame extends JFrame {

	/**
	 * This is a generated serial version UID
	 */
	private static final long serialVersionUID = 2571556063143479411L;
	
	/**
	 * Application title constant
	 */
	private static final String APP_TITLE = "Candango Caracara Wireframer Application";
	
	private CarcaraSplitPane splitPane;
	
	/**
	 * Main constructor
	 */
	public MainFrame() {
		
		super( APP_TITLE );
		
		setSize( new Dimension( 800, 600 ) );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		
        splitPane = new CarcaraSplitPane();
		
		
		add( splitPane );
		
		setVisible( true );
		
		
		
	}
	
}