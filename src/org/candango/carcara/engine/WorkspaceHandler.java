package org.candango.carcara.engine;

import java.io.File;
import java.io.IOException;

import org.candango.carcara.MainApp;
import org.candango.carcara.model.environment.Workspace;
import org.candango.carcara.model.environment.WorkspaceReference;

	
public class WorkspaceHandler {
	
	public static final String CONF_PATH = ".conf";
	
	public static final String CONF_FILE = "workspace.xml";
	
	public static void create( WorkspaceReference reference ) {
		
		if( !isCreated( reference ) ) {
			
			if( !new File( reference.getPath() ).exists() ) {
				File dir = new File( reference.getPath() ) ;
				
				dir.mkdir();
			}
			
			if( !getConfFile( reference ).getParentFile().exists() ) {
				File dir = getConfFile( reference ).getParentFile();
				
				dir.mkdir();
			}
			
			if(  !getConfFile( reference ).exists() ) {
				try {
					getConfFile( reference ).createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
		}
		
		
		
	}
	
	private static boolean  isCreated( WorkspaceReference reference ) {
		
		if( getConfFile( reference ).exists() ) {
			return true;
		}
		
		return false;
	}
	
	public static File getConfFile( WorkspaceReference reference ) {
		return new File( reference.getPath() + MainApp.getFileSeparator() + 
				CONF_PATH + MainApp.getFileSeparator() + CONF_FILE );
	}
	
}