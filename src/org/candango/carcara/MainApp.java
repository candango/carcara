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

import java.util.HashMap;


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
	
	/**
	 * Main methos. Begins all the magic.
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
		
		
		MainFrame frame = new MainFrame();
	
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
	
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */