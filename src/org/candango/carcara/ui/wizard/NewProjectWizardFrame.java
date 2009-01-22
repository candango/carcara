package org.candango.carcara.ui.wizard;

import java.awt.Dimension;

public class NewProjectWizardFrame extends AbstractWizardFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8450906280661697033L;
	
	private static String instruction = "Select a  Wizard";
	
	public NewProjectWizardFrame() {
		
		super( "New Project", instruction );
		
		setSize( new Dimension( 525, 500 ) );
		
	}
	
}