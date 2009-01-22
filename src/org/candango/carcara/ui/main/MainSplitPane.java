package org.candango.carcara.ui.main;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.candango.carcara.MainApp;

import org.candango.carcara.model.project.Project;

public class MainSplitPane extends JSplitPane {
	
	/**
	 * This is a generated serial version UID
	 */
	private static final long serialVersionUID = -4144942237901872630L;

	private JPanel panelLeft;
	
	private JPanel panelRight;
	
	private ProjectTree projectTree;
	
	private MainFrame mainFrame;
	
	public MainSplitPane( MainFrame mainFrame ) {
		
		super( JSplitPane.HORIZONTAL_SPLIT, true );
		
		projectTree = new ProjectTree( );
		
		
		panelLeft = new JPanel();
		panelRight = new JPanel();
		
		JLabel j1 = new JLabel("Area 1");
        JLabel j2 = new JLabel("Area 2");
        
        
        panelLeft.setLayout( new BorderLayout() );
        
        panelRight.setLayout( new BorderLayout() );
        
        panelLeft.add( projectTree );
        //panelRight.add(j2);
		
        setLeftComponent( projectTree );
        
        setRightComponent( panelRight );
        
        setOneTouchExpandable( true );
        
        setDividerLocation( 200 );
        
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	
	public void updateTreeState(){
		projectTree.updateTree();
	}
	
	public ProjectTree getProjectTree() {
		return projectTree;
	}
	
}