package org.candango.carcara.model.environment;

import org.candango.carcara.model.project.Project;

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
	 */
	public void addProject( Project project );
	
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