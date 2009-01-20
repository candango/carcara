/* BasicWorkspace - BasicWorkspace.java
 * 
 * Basic implementation of Workspace interface. Its a basic class that can do
 * all jobs defined by workspace interface. 
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

import java.io.IOException;
import java.util.ArrayList;

import org.candango.carcara.engine.WorkspaceHandler;
import org.candango.carcara.model.environment.exception.ProjectAlreadyExistsException;
import org.candango.carcara.model.project.Project;

/**
 * Basic implementation of Workspace interface. Its a basic class that can do
 * all jobs defined by workspace interface. 
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 */
public class BasicWorkspace implements Workspace {
	
	/**
	 * Array list that stores projects
	 */
	private  ArrayList< Project > projectList = new ArrayList<Project>(); 
	
	private String path;
	
	/* (non-Javadoc)
	 * @see org.candango.carcara.model.environment.Workspace#getPath()
	 */
	@Override
	public String getPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see org.candango.carcara.model.environment.Workspace#setPath(java.lang.String)
	 */
	@Override
	public void setPath(String path) {
		this.path = path;
	}
	
	/* (non-Javadoc)
	 * @see org.candango.carcara.model.environment.Workspace#addProject(org.candango.carcara.model.project.Project)
	 */
	@Override
	public void addProject(Project project)throws 
		ProjectAlreadyExistsException {
		if( hasProject( project.getName() ) ) {
			throw new ProjectAlreadyExistsException();
		}
		projectList.add( project );
		try {
			WorkspaceHandler.save( this );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.candango.carcara.model.environment.Workspace#getProject(java.lang.String)
	 */
	@Override
	public Project getProject(String name) {
		for( Project project : projectList ) {
			if( project.getName().equals( name ) ) {
				return project;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.candango.carcara.model.environment.Workspace#getProjects()
	 */
	@Override
	public Project[] getProjects() {
		Project[] projects = new Project[ projectList.size() ];
		return projectList.toArray( projects );
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String out = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		out += "<workspace>\n";
		out += "\t<project>\n";
		for( Project project : projectList ) {
			out += "\t\t<project >\n";
			out += "\t\t\t<name>" + project.getName() + "</name>\n";
			out += "\t\t\t<package>" + project.getProjectPackage() + "</package>\n";
			out += "\t\t</project >\n";
		}
		out += "\t</project>\n";
		out += "</workspace>";
		return out;
	}

	/* (non-Javadoc)
	 * @see org.candango.carcara.model.environment.Workspace#getReference()
	 */
	@Override
	public WorkspaceReference getReference() {
		WorkspaceReference reference = new BasicWorkspaceReference();
		reference.setPath( getPath() );
		return reference;
	}

	/* (non-Javadoc)
	 * @see org.candango.carcara.model.environment.Workspace#hasProject(java.lang.String)
	 */
	@Override
	public boolean hasProject(String name) {
		for( Project project : projectList ) {
			if( project.getName().equals( name ) ) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.candango.carcara.model.environment.Workspace#getProjectCount()
	 */
	@Override
	public int getProjectCount() {
		return getProjects().length;
	}
	
}
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */