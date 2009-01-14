package org.candango.carcara.model.environment;

import org.candango.carcara.model.project.Project;

public interface Workspace {
	
	public Project getProject( String name );
	
	public Project[] getProjects();
	
	public void addProject( Project project );
	
}