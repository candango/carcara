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
 * The Original Code is Carcara "a Candango Modelling tool to create 
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
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.candango.carcara.ui.MainFrame;
import org.candango.carcara.model.project.Project;

/**
 * Carcara main application class.
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 */
public class MainApp {
	
	/**
	 * Project map. Store all project that application will handle.
	 */
	private static HashMap<String, Project> projectMap = 
		new HashMap<String, Project>();
	
	private static MainFrame mainFrame;
	
	/**
	 * Main Application root path
	 */
	private static String rootPath;
	
	/**
	 * OS path separator
	 */
	private static String pathSeparator;
	
	/**
	 * OS file separator
	 */
	private static String fileSeparator;
	
	/**
	 * Main methos. Begins all the magic.
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
		
		configureEnv();
		
		configureWorkspace();
		
		mainFrame = new MainFrame();
	
	}
	
	/**
	 * Return all projects stored in application
	 * 
	 * @return An array of projects
	 */
	public static Project[] getProjects() {
		
		Project[] projects = new Project[ projectMap.size() ];
		
		int i = 0;
		
		for( String key : projectMap.keySet() ){
			projects[ i ] = projectMap.get( key );
			i++;
		}
		
		return projects;
	}
	
	/**
	 * Add one project into application control.
	 * 
	 * @param key
	 * @param project
	 */
	public static void addProject( String key, Project project ) {
		projectMap.put( key , project );
	}
	
	/**
	 * Return one project by a given key
	 * 
	 * @param key
	 * @return One project
	 */
	public static Project getProject( String key ) {
		return projectMap.get( key );
	}
	
	public static boolean hasProject( String key ) {
		return projectMap.containsKey( key );
	}
	
	/**
	 * Create one project by a given key
	 * 
	 * @param key
	 */
	public static void createProject( String key  ) {
		
		if( hasProject( key ) ){
			JOptionPane.showMessageDialog(mainFrame, "A project with name \"" + 
					key + "\" already exists in the workspace.", 
					"Creating Project", JOptionPane.ERROR_MESSAGE );
		}
		else {
			Project project = null;
			
		    try {
				project = (Project) Class.forName( 
						"org.candango.carcara.php.Project" ).newInstance();
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}catch (InstantiationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IllegalAccessException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			project.setName( key );
			
			MainApp.addProject( key , project );
			
			update();
			
			mainFrame.getProjectTree().selectProject( key );
		}
	}
	
	/**
	 * Configure some envirement variables useful for file operations
	 */
	private static void configureEnv() {
		rootPath = System.getProperties().getProperty( "user.dir");
		pathSeparator = System.getProperties().getProperty( "path.separator");
		fileSeparator = System.getProperties().getProperty( "file.separator");
	}
	
	/**
	 * Configuring the workspace. Running tasks as verify if configuration file 
	 * exists, create file if not exists, choose a workspace location, and so 
	 * on...
	 */
	private static void configureWorkspace() {
		
		if( getConfigurationFile().exists() ) {
			// load configuration file
		}
		else {
			// create configuration file
		}
		
	}
	
	/**
	 * Return the number of projects in application
	 * 
	 * @return
	 */
	public static int getProjectCount() {
		return projectMap.size();
	}
	
	private static void update() {
		mainFrame.update();
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
		return new File( getRootPath() + getFileSeparator() + "carcara.conf" );
	}
	
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */