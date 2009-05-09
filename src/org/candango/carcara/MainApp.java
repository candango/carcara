/* MainApp - MainApp.java
 * 
 * Carcara main application class.
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
package org.candango.carcara;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.candango.carcara.ui.core.MainFrame;
import org.candango.carcara.ui.launcher.WorkspaceLauncherFrame;
import org.candango.carcara.engine.EnvironmentHandler;
import org.candango.carcara.engine.WorkspaceHandler;
import org.candango.carcara.model.environment.BasicEnvironment;
import org.candango.carcara.model.environment.Environment;
import org.candango.carcara.model.environment.Workspace;
import org.candango.carcara.model.environment.WorkspaceReference;

/**
 * Carcara main application class.
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 * @since 0.0.1
 */
public class MainApp {
	
	
	private static MainFrame mainFrame;
	
	private static Environment environment;

	/**
	 * Main Application root path
	 */
	private static String rootPath;
	
	private static String userHomePath; 
	
	/**
	 * OS path separator
	 */
	private static String pathSeparator;
	
	/**
	 * OS file separator
	 */
	private static String fileSeparator;
	
	/**
	 * Main method. Begins all the magic.
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
		
		configureEnviromentVariables();
		
		configureEnvironment();
	
	}
	
	/**
	 * Configure some envirement variables useful for file operations
	 */
	private static void configureEnviromentVariables() {
		rootPath = System.getProperties().getProperty( "user.dir");
		userHomePath = System.getProperties().getProperty( "user.home");
		pathSeparator = System.getProperties().getProperty( "path.separator");
		fileSeparator = System.getProperties().getProperty( "file.separator");
	}
	
	/**
	 * Configuring the enviroment. Running tasks as verify if configuration file 
	 * exists, create file if not exists, choose a workspace location, and so 
	 * on...
	 */
	private static void configureEnvironment() {
		
		if( !getConfigurationFile().exists() ) {
			environment = new BasicEnvironment();
			createConfigurationFile();
		}
		else {
			environment = EnvironmentHandler.loadEnvironment();
		}
		
		WorkspaceLauncherFrame launcherFrame = new WorkspaceLauncherFrame();
		
		launcherFrame.setVisible( true );	
	}
	
	public static void showMainFrame( WorkspaceReference reference ) {
		
		Workspace workspace = WorkspaceHandler.create( reference );
		
		getEnvironment().setWorkspace( workspace );
		
		mainFrame = new MainFrame();
	}
	
	/**
	 * Return main application root path
	 * 
	 * @return
	 */
	public static String getRootPath() {
		return rootPath;
	}
	
	/**
	 * Return user home path
	 * 
	 * @return
	 */
	public static String getUserHomePath() {
		return userHomePath;
	}
	
	/**
	 * Return os path separator
	 * 
	 * @return
	 */
	public static String getPathSeparator() {
		return pathSeparator;
	}
	
	/**
	 * Return os file separator
	 * 
	 * @return
	 */
	public static String getFileSeparator() {
		return fileSeparator;
	}
	
	/**
	 * Return the carcara configuration file
	 * 
	 * @return
	 */
	public static File getConfigurationFile() {
		return new File( getRootPath() + getFileSeparator() + 
				".conf" + getFileSeparator() + "environment.xml" );
	}
	
	public static void createConfigurationFile() {
		try {
			File dir = new File( getConfigurationFile().getParent() ) ;
			
			dir.mkdir();
			
			getConfigurationFile().createNewFile();
			
			saveEnvitonment();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			JOptionPane.showMessageDialog( null, 
					"Can't create configuration file.", 
					"Caracara Modeling Tool", JOptionPane.ERROR_MESSAGE );
			
			System.exit( 1 );
		}
	}
	
	public static void saveEnvitonment() throws IOException {
		FileWriter out = new FileWriter( getConfigurationFile() );
		
		out.write( environment.toString() );
		
		out.close();
	}
	
	public static Environment getEnvironment() {
		return environment;
	}

	public static void setEnvironment(Environment environment) {
		MainApp.environment = environment;
	}
	
	public static MainFrame getMainFrame(){
		return mainFrame;
	}
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */