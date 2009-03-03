package org.candango.carcara.model.environment;

import java.util.ArrayList;

public class BasicEnvironment implements Environment {
	
	/**
	 * Workspace array list. Store all environment workspaces
	 */
	private ArrayList<WorkspaceReference> referenceList = 
		new ArrayList<WorkspaceReference>();
	
	private Workspace workspace;

	public void addReference( WorkspaceReference ref ) {
		if( referenceExists( ref ) ) {
			for( int i = 0; i < referenceList.size(); i++ ) {
				if( referenceList.get( i ).getPath().equals( ref.getPath() ) ) {
					referenceList.set( i , ref );
				}
			}
		}
		else {
			referenceList.add( ref );
		}
	}

	public WorkspaceReference getReference( int index ) {
		return referenceList.get( index );
	}

	public WorkspaceReference[] getReferences() {
		WorkspaceReference[] references = 
			new WorkspaceReference[ referenceList.size() ];
		return referenceList.toArray( references );
	}

	public WorkspaceReference getDefaultReference() {
		for( WorkspaceReference ref : referenceList ) {
			if( ref.isDefault() ) {
				return ref;
			}
		}
		return null;
	}
	
	public boolean referenceExists( WorkspaceReference reference ) {
		for( WorkspaceReference ref : getReferences() ) {
			if( ref.getPath().equals( reference.getPath() ) ) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String out = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		out += "<environment>\n";
		out += "\t<workspaces>\n";
		for( WorkspaceReference ref : referenceList ) {
			out += "\t\t<workspace path=\"" + ref.getPath() + "\"" + 
			( ref.isDefault() ? " default=\"true\"" : "" ) + "/>\n";
		}
		out += "\t</workspaces>\n";
		out += "</environment>";
		return out;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace( Workspace workspace ) {
		this.workspace = workspace;
	}
}