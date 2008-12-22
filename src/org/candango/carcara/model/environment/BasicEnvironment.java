package org.candango.carcara.model.environment;

import java.util.ArrayList;


public class BasicEnvironment implements Environment {
	
	/**
	 * Workspace array list. Store all environment workspaces
	 */
	private ArrayList<WorkspaceReference> referenceList = new ArrayList<WorkspaceReference>();
	
	@Override
	public void addReference( WorkspaceReference ref ) {
		referenceList.add( ref );
	}

	@Override
	public WorkspaceReference getReference( int index ) {
		return referenceList.get( index );
	}

	@Override
	public WorkspaceReference[] getReferences() {
		WorkspaceReference[] references = new WorkspaceReference[ referenceList.size() ];
		return referenceList.toArray( references );
	}

	@Override
	public WorkspaceReference getDefaultReference() {
		for( WorkspaceReference ref : referenceList ) {
			if( ref.isDefault() ) {
				return ref;
			}
		}
		return null;
	}
	
	
	public String toString() {
		String out = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		out += "<environment>\n";
		out += "\t<workspaces>\n";
		for( WorkspaceReference ref : referenceList ) {
			out += "\t<workspace path=\"" + ref.getPath() + "\"/>\n";
		}
		out += "\t</workspaces>\n";
		out += "</environment>";
		return out;
	}
	
}