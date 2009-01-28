/* NewProjectWizardDialog - NewProjectWizardDialog.java
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
package org.candango.carcara.ui.wizard;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

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
	 * Generated serial version UID
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
    	
    	JPanel centerPanel = new JPanel();
    	
    	centerPanel.setLayout( new BorderLayout() );
    	
    	JPanel labelPane = new JPanel();
    	
    	labelPane.setBorder( BorderFactory.createEmptyBorder( 10, 10, 0, 10) );
    	
    	labelPane.setLayout( new BorderLayout() );
    	
    	labelPane.add( new JLabel( "Wizards:" ) );
    	
    	centerPanel.add( labelPane, BorderLayout.PAGE_START );
    	
    	JPanel treePane = new JPanel();
    	
    	tree = new JTree();
        tree.setEditable( true );
        tree.getSelectionModel().setSelectionMode
                ( TreeSelectionModel.SINGLE_TREE_SELECTION );
        
        tree.setRootVisible( false );
        
        tree.setShowsRootHandles( true );
        
        JScrollPane scrollPane = new JScrollPane( tree );
        
        treePane.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10) );
        
        treePane.setLayout( new BorderLayout() );
        
        treePane.add( scrollPane, BorderLayout.CENTER );
        
        centerPanel.add( treePane, BorderLayout.CENTER );
        
        addCenterComponent( centerPanel );
    	
    }
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */