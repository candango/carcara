package org.candango.carcara.model.environment;

public class BasicWorkspaceReference implements WorkspaceReference {
	
	private String path = "";
	
	private boolean defaultFlag = false;
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public boolean isDefault() {
		return defaultFlag;
	}

	public void setDefault( boolean value ) {
		defaultFlag = value;
	}
	
}
