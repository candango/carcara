package org.candango.carcara.model.environment;

import java.util.ArrayList;

import org.candango.carcara.model.project.Project;

public class BasicWorkspace implements Workspace {
	
	private  ArrayList< Project > projectList = new ArrayList<Project>(); 
	
	@Override
	public void addProject(Project project) {
		projectList.add( project );
	}

	@Override
	public Project getProject(String name) {
		for( Project project : projectList ) {
			if( project.getName().equals( name ) ) {
				return project;
			}
		}
		return null;
	}

	@Override
	public Project[] getProjects() {
		Project[] projects = new Project[ projectList.size() ];
		return projectList.toArray( projects );
	}

}
