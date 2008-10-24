package org.candango.carcara.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MainSplitPane extends JSplitPane {
	
	/**
	 * This is a generated serial version UID
	 */
	private static final long serialVersionUID = -4144942237901872630L;

	private JPanel panelLeft;
	
	private JPanel panelRight;
	
	public MainSplitPane() {
		
		super( JSplitPane.HORIZONTAL_SPLIT, true );
		
		panelLeft = new JPanel();
		panelRight = new JPanel();
		
		JLabel j1 = new JLabel("Area 1");
        JLabel j2 = new JLabel("Area 2");
        
        panelLeft.add(j1);
        panelRight.add(j2);
		
        setLeftComponent( panelLeft );
        
        setRightComponent( panelRight );
        
        setOneTouchExpandable( true );
        
        setDividerLocation( 200 );
        
	}
	
}
