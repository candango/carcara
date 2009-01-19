package org.candango.carcara.model.environment;

import java.util.ArrayList;

import org.candango.carcara.model.project.Project;

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
	public void addProject(Project project) {
		projectList.add( project );
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

	@Override
	public boolean hasProject(String name) {
		for( Project project : projectList ) {
			if( project.getName().equals( name ) ) {
				return true;
			}
		}
		return false;
	}

	
	
}
