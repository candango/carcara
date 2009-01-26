package org.candango.carcara.ui.wizard;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;




public class NewProjectWizardDialog extends AbstractWizardDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8450906280661697033L;
	
	private static String title = "New Project";
	
	private static String instruction = "Select a  Wizard";
	
	private JTree tree;
	
	public NewProjectWizardDialog() {
		super( title, instruction );
		createCenterPane();
		
	}
	
    private void createCenterPane() {
    	
    	JPanel pane = new JPanel();
    	
    	tree = new JTree();
        tree.setEditable( true );
        tree.getSelectionModel().setSelectionMode
                ( TreeSelectionModel.SINGLE_TREE_SELECTION );
        
        tree.setRootVisible( false );
        
        tree.setShowsRootHandles( true );
        
        //tree.setCellRenderer( new ProjectCellRender() );
        
        JScrollPane scrollPane = new JScrollPane( tree );
        
        pane.add( scrollPane );
        
        addCenterComponent( scrollPane );
    	
    }
}