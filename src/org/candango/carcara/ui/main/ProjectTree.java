/* ProjectTree - ProjectTree.java
 * 
 * Tree that show all workspace projects and his elements.
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

import java.awt.Component;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.candango.carcara.MainApp;
import org.candango.carcara.model.project.Project;

/**
 * Tree that show all workspace projects and his elements.
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 */
public class ProjectTree extends JPanel {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tree root node that store all project nodes 
	 */
	protected ProjectElementNode rootNode;
	
	/**
	 * Tree model used to reload tree data
	 */
    protected DefaultTreeModel treeModel;
    
    /**
     * JTree object
     */
    protected JTree tree;
    
    /**
     * Default constructor.
     */
    public ProjectTree() {

    	super( new GridLayout(1,0) );
        
        rootNode = new ProjectElementNode( "Workspace" );
        treeModel = new DefaultTreeModel( rootNode );
        
        tree = new JTree( treeModel );
        tree.setEditable( true );
        tree.getSelectionModel().setSelectionMode
                ( TreeSelectionModel.SINGLE_TREE_SELECTION );
        
        tree.setRootVisible( false );
        
        tree.setShowsRootHandles( true );
        
        tree.setCellRenderer( new ProjectCellRender() );
        
        JScrollPane scrollPane = new JScrollPane( tree );
        
        add(scrollPane);
	}
    
    public void selectProject( String key ) {
    	
    	/*for( int i = 0; i < rootNode.getChildCount(); i++ ){
    		System.out.println( key + " - " + rootNode.getChildAt( i ).toString() );
    		
    		if( key.equals( rootNode.getChildAt( i ).toString() ) ){
    			System.out.println( tree.getPathForRow( i ) );
    		}
    	}*/
    	
    }
    
    /**
     * Update the tree lading project data form MainApp. 
     */
    public void updateTree() {
    	
    	rootNode.removeAllChildren();
    	
    	//ImageIcon projectIcon = new ImageIcon( "img/project.png" );
    	
    	for( Project project : 
    		MainApp.getEnvironment().getWorkspace().getProjects() ){
    		
    		
    		ProjectElementNode node = new ProjectElementNode( 
    				project.getName() );
    		
    		node.setType( ProjectElementNode.PROJECT_ELEMENT_TYPE );
    		
    		rootNode.add( node );
    		
    	}
    	
    	treeModel.reload();
    }
    
    /**
     * Cell handler that sets the node images in project tree.
	 *
	 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
	 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
	 * @link       http://www.candango.org/myfuses
	 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
	 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
	 */
    private class ProjectCellRender extends DefaultTreeCellRenderer { 
    	
    	/**
    	 * Generated serial version UID
    	 */
    	private static final long serialVersionUID = 1648364485810404508L;

    	@Override
    	public Component getTreeCellRendererComponent(JTree tree, Object value,
    			boolean selected, boolean expanded, boolean leaf, int row,
    			boolean hasFocus) {
    		super.getTreeCellRendererComponent( tree,
                    value,
                    selected,
                    expanded,
                    leaf,
                    row,
                    hasFocus );
    		
    		ProjectElementNode node = ( ProjectElementNode ) value;
    		
    		if( node.getParent() != null ) {
    			if( node.getType() == ProjectElementNode.PROJECT_ELEMENT_TYPE ) {
    				URL url = MainApp.class.getResource( "img/folder.png" ); 
    				setIcon( new ImageIcon( url ) );
    			}
    		}
    		return this;
    	}
    	
    }
    
    /**
     * Project element node is an rich TreeNode class that will store project
     * details and help some action to be done over the tree.
	 *
	 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
	 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
	 * @link       http://www.candango.org/myfuses
	 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
	 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
	 */
    private class ProjectElementNode extends DefaultMutableTreeNode {
    	
    	/**
		 * Generated serial version UID 
		 */
		private static final long serialVersionUID = -6927028384202838316L;

		/**
		 * Root element type constant.<br>Value is <code>1</code>
		 */
		public static final int ROOT_ELEMENT_TYPE = 1;
    	
		/**
		 * Project element type constant.<br>Value is <code>2</code>
		 */
    	public static final int PROJECT_ELEMENT_TYPE = 2;
    	
    	/**
    	 * Node type property
    	 */
    	private int type;
    	
    	/**
    	 * Default constructor
    	 * 
    	 * @param name Name of the node
    	 */
    	public ProjectElementNode( String name ) {
    		super( name );
    	}
    	
    	/**
    	 * Set node type
    	 * 
    	 * @return
    	 */
    	public int getType() {
    		return type;
    	}
    	
    	/**
    	 * Set node type
    	 * 
    	 * @param type Type of node. Use ProjectElementNode type constants.
    	 */
    	public void setType(int type) {
    		this.type = type;
    	}
    }
}