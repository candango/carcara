package org.candango.carcara.model.environment;

public interface WorkspaceReference {
	
	public String getPath();
	
	public void setPath( String path );
	
	public boolean isDefault();
	
	public void setDefault( boolean value );
	
}
