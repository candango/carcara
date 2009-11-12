/* $Id$
 * 
 * This is a new project wizard dialog. Apears when new project is fired in
 * application menu.
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
package org.candango.carcara.ui.core.wizard;


import java.awt.Dimension;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.candango.carcara.ui.wizard.AbstractWizardDialog;

import net.miginfocom.swing.MigLayout;

/**
 * This is a new project wizard dialog. Apears when new project is fired in
 * application menu.
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Revision$
 * @since 95
 */
public class NewProjectWizardDialog extends AbstractWizardDialog {
	
	/**
	 * Tree root node that store all project nodes 
	 */
	protected DefaultMutableTreeNode rootNode;
	
	/**
	 * Tree model used to reload tree data
	 */
    protected DefaultTreeModel treeModel;
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -8450906280661697033L;
	
	private static String title = "New Project";
	
	private static String instruction = "Create a New Project";
	
	private JTextField txtProjectName;
	
	/**
	 * Default constructor
	 */
	public NewProjectWizardDialog() {
		super( title, instruction );
		
		Dimension d = getSize();
		
		d.setSize( getSize().getWidth() , 250);
		
		setSize( d );
		
		setLocation( getCenteredCorner() );
		
		setHint( "Create a new Carcara Project" );
		
		createCenterPane();
	}
	
    /**
     * Create all cente pane components
     */
    private void createCenterPane() {
    	
    	JPanel centerPanel = new JPanel();
    	
    	centerPanel.setLayout( new MigLayout( "fillx", "[right]rel[grow,fill]" ) );
    	
    	centerPanel.add( new JLabel( "Project name" ) );
    	
    	rootNode = new DefaultMutableTreeNode( "Wizards" );
    	
    	txtProjectName = new JTextField( "" );
    	
    	txtProjectName.addFocusListener( new FocusListener(){

			public void focusGained(FocusEvent e) {
				setHint( "Enter a project name" );
			}

			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    	
    	txtProjectName.addKeyListener( new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				if( txtProjectName.getText().trim().equals( "" ) ) {
					getFinishButton().setEnabled( false );
				}
				else {
					
					getFinishButton().setEnabled( true );
				}
			}

			public void keyTyped(KeyEvent e) {
			}
    		
    	});
    	
    	centerPanel.add( txtProjectName, "grow" );
    	
    	addCenterComponent( centerPanel );
    }
    
    /**
     * Update the tree lading project data form MainApp. 
     */
    public void updateTree() {
    	rootNode.removeAllChildren();
    		
    	DefaultMutableTreeNode node = new DefaultMutableTreeNode( "General" );
    	
    	DefaultMutableTreeNode projectNode = new DefaultMutableTreeNode( "Project" );
    		
    	rootNode.add( node );
    	
    	node.add( projectNode );
    	
    	treeModel.reload();
    }
    
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */