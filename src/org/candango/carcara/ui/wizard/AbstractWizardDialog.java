/* AbstractWizardFrame - AbstractWizardFrame.java
 * 
 * Base class to create wizard frames in carcara.
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import org.candango.carcara.MainApp;

/**
 * Base class to create wizard frames in carcara.
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 * @since 0.0.1
 */
public class AbstractWizardDialog extends JDialog {

	/**
	 * Generated serial version UID 
	 */
	private static final long serialVersionUID = 5771792647335637156L;
	
	private String instruction;
	
	private JButton backButton;
	
	private JButton nextButton;
	
	private JButton finishButton;
	
	private JButton cancelButton;
	
	public AbstractWizardDialog( String title, String instruction ) {
		
		super( MainApp.getMainFrame(), true );
		
		setInstruction( instruction );
		
		setTitle( title );
		
		setDefaultSize();
		
		createNorthPane();
		
		createSouthPane();
		
		defineCancelAction();
		
		setLocation( getCenteredCorner() );
	}
	
	private void createNorthPane() {
		JPanel pane = new JPanel();
		
		pane.setLayout( new BorderLayout() );
		
		// Creating workspace command label
		JLabel selectWorkspaceLabel = new JLabel( "  " + instruction );
		
		selectWorkspaceLabel.setPreferredSize( new Dimension( 40, 25 ) );
		
		pane.add( selectWorkspaceLabel, BorderLayout.PAGE_START );
		
		// creating workspace info text area
		JTextArea workspaceInfoTextArea = new JTextArea( "   " );
		
		workspaceInfoTextArea.setPreferredSize( new Dimension( 40, 40 ) );
		
		workspaceInfoTextArea.setOpaque( false );
		
		workspaceInfoTextArea.setEditable( false );
		
		pane.add( workspaceInfoTextArea, BorderLayout.CENTER );
		
		JSeparator separator = new JSeparator();
		
		separator.setForeground( Color.GRAY );
		
		pane.add( separator, BorderLayout.PAGE_END );
		
		pane.setBackground( Color.WHITE );
		
		add( pane, BorderLayout.NORTH );
		
	}
	
	private void createSouthPane() {
		
		JPanel pane = new JPanel();
		
		pane.setLayout( new BorderLayout() );
		
		// creating workspace info text area
		
		JSeparator separator = new JSeparator();
		
		separator.setForeground( Color.GRAY );
		
		pane.add( separator, BorderLayout.PAGE_START );
		
		pane.setBackground( Color.WHITE );
		
		// creating buttons
		
		JPanel buttonPane = new JPanel();
		
		JPanel innerButtonPane1 = new JPanel();
		
		JPanel innerButtonPane2 = new JPanel();
		
		buttonPane.setLayout( new GridLayout( 0, 2, 10, 0 ) );
		
		innerButtonPane1.setLayout( new GridLayout( 0, 2, 2, 0 ) );
		
		innerButtonPane2.setLayout( new GridLayout( 0, 2, 10, 0 ) );
		
		backButton = new JButton( "< Back" );
		
		backButton.setEnabled( false );
		
		nextButton = new JButton( "Next >" );
		
		nextButton.setEnabled( false );
		
		finishButton = new JButton( "Finish" );
		
		finishButton.setEnabled( false );
		
		cancelButton = new JButton( "Cancel" );
		
		innerButtonPane1.add( backButton );
		
		innerButtonPane1.add( nextButton );
		
		innerButtonPane2.add( finishButton );
		
		innerButtonPane2.add( cancelButton );
		
		buttonPane.add( innerButtonPane1 );
		
		buttonPane.add( innerButtonPane2 );
		
		buttonPane.setBorder( 
				BorderFactory.createEmptyBorder( 15, 100, 15, 15) );
		
		pane.add( buttonPane, BorderLayout.CENTER );
		
		add( pane, BorderLayout.SOUTH );
		
	}
	
	private void defineCancelAction() {
		
		getCancelButton().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				setVisible( false );
				dispose();
			}
			
		}
		);
		
	}
	
	public void addCenterComponent( Component component ) {
		add( component );
	}
	
	private void setDefaultSize() {
		setSize( 525, 500 );
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getInstruction() {
		return instruction;
	}
	
	private Point getCenteredCorner() {
        Dimension dim = new Dimension( getParent().getWidth(), getParent().getHeight() );
        return new Point(
            (int)((dim.getWidth() - getWidth()) / 2) + getParent().getX(),
            (int)((dim.getHeight() - getHeight()) / 2) + getParent().getY()
        );
    }

	public JButton getBackButton() {
		return backButton;
	}

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public void setNextButton(JButton nextButton) {
		this.nextButton = nextButton;
	}

	public JButton getFinishButton() {
		return finishButton;
	}

	public void setFinishButton(JButton finishButton) {
		this.finishButton = finishButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}
	
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */