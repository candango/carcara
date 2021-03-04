/* $Id$
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
import java.awt.Toolkit;
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
 * @version    SVN: $Revision$
 * @since 93
 */
/**
 * @author fpiraz
 *
 */
public class AbstractWizardDialog extends JDialog {

	/**
	 * Generated serial version UID 
	 */
	private static final long serialVersionUID = 5771792647335637156L;
	
	/**
	 * Wizard back dialog
	 */
	private AbstractWizardDialog backDialog;
	
	/**
	 * Wizard next dialog
	 */
	private AbstractWizardDialog nextDialog;
	
	/**
	 * Wizard instruction
	 */
	private String instruction;
	
	/**
	 * Wizard hint
	 */
	private String hint;

	/**
	 * Wizard back button
	 */
	private JButton backButton;
	
	/**
	 * Wizard next button
	 */
	private JButton nextButton;
	
	/**
	 * Wizard finish button
	 */
	private JButton finishButton;
	
	/**
	 * Wizard cancel button
	 */
	private JButton cancelButton;
	
	/**
	 * Wizard instuction label
	 */
	private JLabel instructionLabel;
	
	/**
	 * Wizard hint label
	 */
	private JTextArea hintTextArea;
	
	/**
	 * Default constructor
	 * 
	 * @param title
	 * @param instruction
	 */
	public AbstractWizardDialog( String title, String instruction ) {
		super( MainApp.getMainFrame(), true );
		
		setTitle( title );
		
		createNorthPane();
		
		setInstruction( instruction );
		
		setDefaultSize();
		
		createSouthPane();
		
		defineCancelAction();
		
		setLocation( getCenteredCorner() );
	}
	
	/**
	 * Mount the north pane in wizard dialog
	 */
	private void createNorthPane() {
		JPanel pane = new JPanel();
		
		pane.setLayout( new BorderLayout() );
		
		// Creating workspace command label
		instructionLabel = new JLabel( "" );
		
		instructionLabel.setPreferredSize( new Dimension( 40, 25 ) );
		
		pane.add( instructionLabel, BorderLayout.PAGE_START );
		
		// creating workspace info text area
		hintTextArea = new JTextArea( "" );
		
		hintTextArea.setPreferredSize( new Dimension( 40, 40 ) );
		
		hintTextArea.setOpaque( false );
		
		hintTextArea.setEditable( false );
		
		pane.add( hintTextArea, BorderLayout.CENTER );
		
		JSeparator separator = new JSeparator();
		
		separator.setForeground( Color.GRAY );
		
		pane.add( separator, BorderLayout.PAGE_END );
		
		pane.setBackground( Color.WHITE );
		
		add( pane, BorderLayout.NORTH );
		
	}
	
	/**
	 * Mount the south pane in wizard dialog
	 */
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
	
	/**
	 * Define a default action on cancel button click
	 */
	private void defineCancelAction() {
		getCancelButton().addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				setVisible( false );
				dispose();
			}
		} );
	}
	
	/**
	 * Add one component in the dialog center
	 * 
	 * @param component
	 */
	public void addCenterComponent( Component component ) {
		add( component, BorderLayout.CENTER );
	}
	
	/**
	 * Set the dialog dafault size as 525x500.
	 */
	private void setDefaultSize() {
		setSize( 525, 500 );
	}
	
	/**
	 * Return wizard instruction
	 * 
	 * @return
	 */
	public String getInstruction() {
		return instruction;
	}
	
	/**
	 * Set wizard instruction
	 * 
	 * @param instruction
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
		
		this.instructionLabel.setText( instruction );
	}
	
	/**
	 * Return wizard hint
	 * 
	 * @return
	 */
	public String getHint() {
		return hint;
	}
	
	/**
	 * Set wizard hint
	 * 
	 * @param hint
	 */
	public void setHint(String hint) {
		this.hint = hint;
		this.hintTextArea.setText( hint );
	}
	
	/**
	 * Return the centered point that the dialog must appears in the screen
	 * 
	 * @return A <code>Point</code> for positioning the dialog
	 */
	protected Point getCenteredCorner() {
		
		Dimension dim = null;
		
		// if the parent dont exists or his size is 0,0 get the screen size
		if( getParent().getX() == 0 && getParent().getY() == 0 && 
				getParent().getWidth() == 0 && getParent().getHeight() == 0 ) {
			dim = Toolkit.getDefaultToolkit().getScreenSize();
		}
		else {
			dim = new Dimension( getParent().getWidth(), 
					getParent().getHeight() );
		}
		
        return new Point(
            (int)((dim.getWidth() - getWidth()) / 2) + getParent().getX(),
            (int)((dim.getHeight() - getHeight()) / 2) + getParent().getY()
        );
    }

	/**
	 * Return the wizard back button
	 * 
	 * @return
	 */
	public JButton getBackButton() {
		return backButton;
	}
	
	/**
	 * Return the wizard next button
	 * 
	 * @return
	 */
	public JButton getNextButton() {
		return nextButton;
	}
	
	/**
	 * Return the wizard finish button
	 * 
	 * @return
	 */
	public JButton getFinishButton() {
		return finishButton;
	}

	/**
	 * Return the wizard cancel button
	 * 
	 * @return
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	/**
	 * Return the wizard back dialog
	 * 
	 * @return The back <code>AbstractWizardDialog</code>
	 */
	public AbstractWizardDialog getBackDialog() {
		return backDialog;
	}
	
	/**
	 * Set back dialog.
	 * 
	 * @param dialog The back <code>AbstractWizardDialog</code>
	 */
	public void setBackDialog( AbstractWizardDialog dialog ) {
		backDialog = dialog;
		
		if( backDialog != null ) {
			getBackButton().setEnabled( true );
		}
	}
	
	/**
	 * Return the wizard next dialog
	 * 
	 * @return The next <code>AbstractWizardDialog</code>
	 */
	public AbstractWizardDialog getNextDialog() {
		return nextDialog;
	}
	
	/**
	 * Set next dialog.
	 * 
	 * @param dialog The next <code>AbstractWizardDialog</code>
	 */
	public void setNextDialog( AbstractWizardDialog dialog ) {
		nextDialog = dialog;
		
		if( nextDialog != null ) {
			getNextButton().setEnabled( true );
		}
	}
	
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */