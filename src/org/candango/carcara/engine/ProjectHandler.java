package org.candango.carcara.engine;

import org.candango.carcara.model.project.Project;

public class ProjectHandler {
	
	/**
	 * Create one project by a given key
	 * 
	 * @param key
	 */
	public static Project createProject( String key, String className  ) {
		
		Project project = null;
			
	    try {
			project = (Project) Class.forName( className ).newInstance();
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		project.setName( key );
		
		return project;
		
	}
	
}
