package org.candango.carcara.model.environment;

import java.util.ArrayList;


public class BasicEnvironment implements Environment {
	
	/**
	 * Workspace array list. Store all environment workspaces
	 */
	private ArrayList<Workspace> workspaceList = new ArrayList<Workspace>();
	
	@Override
	public void addWorkspace( Workspace workspace ) {
		workspaceList.add( workspace );
	}

	@Override
	public Workspace getWorkspace( int index ) {
		return workspaceList.get( index );
	}

	@Override
	public Workspace[] getWorkspaces() {
		Workspace[] workspaces = new Workspace[ workspaceList.size() ];
		return workspaceList.toArray( workspaces );
	}

	@Override
	public Workspace getDefaultWorkspace() {
		for( Workspace workspace : getWorkspaces() ) {
			if( workspace.isDefault() ) {
				return workspace;
			}
		}
		return null;
	}
	
	
	public String toString() {
		String out = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		out += "<environment>\n";
		out += "\t<workspaces>\n";
		out += "\t</workspaces>\n";
		out += "</environment>";
		return out;
	}
	
}