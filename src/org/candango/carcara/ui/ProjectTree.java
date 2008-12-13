package org.candango.carcara.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.candango.carcara.MainApp;
import org.candango.carcara.model.project.Project;

public class ProjectTree extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    
    public ProjectTree() {

    	super( new GridLayout(1,0) );
        
        rootNode = new DefaultMutableTreeNode( "Workspace" );
        treeModel = new DefaultTreeModel( rootNode );
        
        tree = new JTree( treeModel );
        tree.setEditable( true );
        tree.getSelectionModel().setSelectionMode
                ( TreeSelectionModel.SINGLE_TREE_SELECTION );
        
        tree.setRootVisible( false );
        
        tree.setShowsRootHandles( true );

        JScrollPane scrollPane = new JScrollPane( tree );
        
        add(scrollPane);

    	
	}
    
    public void selectProject( String key ) {
    	
    	for( int i = 0; i < rootNode.getChildCount(); i++ ){
    		System.out.println( key + " - " + rootNode.getChildAt( i ).toString() );
    		
    		if( key.equals( rootNode.getChildAt( i ).toString() ) ){
    			System.out.println( tree.getPathForRow( i ) );
    		}
    	}
    	
    }
    
    public void updateTree() {
    	
    	rootNode.removeAllChildren();
    	
    	for( Project project : MainApp.getProjects() ){
    		DefaultMutableTreeNode node = new DefaultMutableTreeNode( project.getName() );
    		rootNode.add( node );
    	}
    	
    	treeModel.reload();
    }
    
}
