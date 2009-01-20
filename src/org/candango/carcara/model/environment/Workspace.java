/* Workspace - Workspace.java
 * 
 * Caracara workspace interface. Defines the workspace behavior into Caracara
 * system. 
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
package org.candango.carcara.model.environment;

import org.candango.carcara.model.environment.exception.ProjectAlreadyExistsException;
import org.candango.carcara.model.project.Project;

/**
 * Caracara workspace interface. Defines the workspace behavior into Caracara
 * system. 
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 */
public interface Workspace {
	
	/**
	 * Return the workspace path
	 * 
	 * @return
	 */
	public String getPath();
	
	/**
	 * Set the workspace path
	 * 
	 * @param path
	 */
	public void setPath( String path );
	
	/**
	 * Return a project by a given name
	 * 
	 * @param name
	 * @return
	 */
	public Project getProject( String name );
	
	/**
	 * Return all projects stored in wokspace
	 * 
	 * @return
	 */
	public Project[] getProjects();
	
	/**
	 * Add a new project to workspace
	 * 
	 * @param project
	 * @throws ProjectAlreadyExistsException
	 */
	public void addProject( Project project ) throws 
		ProjectAlreadyExistsException;
	
	/**
	 * Return workspace project count
	 * 
	 * @return
	 */
	public int getProjectCount();
	
	/**
	 * Verifies if the workspace has a project by a given name
	 *
	 * @param name
	 * @return
	 */
	public boolean hasProject( String name );
	
	/**
	 * Return the workspace reference
	 * 
	 * @return
	 */
	public WorkspaceReference getReference();
	
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */