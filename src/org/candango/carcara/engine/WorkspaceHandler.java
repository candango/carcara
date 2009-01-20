package org.candango.carcara.engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.candango.carcara.MainApp;
import org.candango.carcara.model.environment.BasicWorkspace;
import org.candango.carcara.model.environment.Workspace;
import org.candango.carcara.model.environment.WorkspaceReference;

	
public class WorkspaceHandler {
	
	/**
	 * 
	 */
	public static final String CONF_PATH = ".conf";
	
	public static final String CONF_FILE = "workspace.xml";
	
	/**
	 * Create the workspace files if they aren't created yet
	 * 
	 * @param reference
	 */
	public static Workspace create( WorkspaceReference reference ) {
		
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
		
		Workspace workspace = new BasicWorkspace();
		
		workspace.setPath( reference.getPath() );
		
		try {
			save( workspace );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return workspace;
		
	}
	
	/**
	 * Returns if the workspace was created by his reference
	 * 
	 * @param reference
	 * @return
	 */
	private static boolean isCreated( WorkspaceReference reference ) {
		
		if( getConfFile( reference ).exists() ) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Return the workspace configuration file
	 * 
	 * @param reference
	 * @return
	 */
	public static File getConfFile( WorkspaceReference reference ) {
		return new File( reference.getPath() + MainApp.getFileSeparator() + 
				CONF_PATH + MainApp.getFileSeparator() + CONF_FILE );
	}
	
	/**
	 * Save the workspace in his file.
	 * 
	 * @param workspace
	 * @throws IOException
	 */
	public static void save( Workspace workspace ) throws IOException {
		
		FileWriter out = new FileWriter( getConfFile( workspace.getReference() ) );
		
		out.write( workspace.toString() );
		
		out.close();
		
	}
}