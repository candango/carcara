package org.candango.carcara.model.environment;

public interface Environment {
	
	public void addWorkspace( Workspace workspace );
	
	public Workspace[] getWorkspaces();
	
	public Workspace getWorkspace( int index );
	
	public Workspace getDefaultWorkspace();
	
}