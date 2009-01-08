package org.candango.carcara.model.environment;

public interface Environment {
	
	public void addReference( WorkspaceReference ref );
	
	public WorkspaceReference[] getReferences();
	
	public WorkspaceReference getReference( int index );
	
	public WorkspaceReference getDefaultReference();
	
	public Workspace getWorkspace();

	public void setWorkspace( Workspace workspace );
	
}