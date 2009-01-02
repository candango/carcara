package org.candango.carcara.model.environment;

public class BasicWorkspaceReference implements WorkspaceReference {
	
	private String path = "";
	
	private boolean defaultFlag = false;
	
	@Override
	public String getPath() {
		return path;
	}
	
	@Override
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public boolean isDefault() {
		return defaultFlag;
	}

	@Override
	public void setDefault( boolean value ) {
		defaultFlag = value;
	}
	
}
