/* WorkspaceHandler - WorkspaceHandler.java
 * 
 * Utility class that have some util methods to handle workspaces.
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * This product includes software developed by the Fusebox Corporation 
 * (http://www.fusebox.org/).
 * 
 * The Original Code is Carcara "a Candango Modeling tool to create 
 * myFuses/iflux applications" part .
 * 
 * The Initial Developer of the Original Code is Flavio Goncalves Garcia.
 * Portions created by Flavio Goncalves Garcia are Copyright (C) 2008 - 2009.
 * All Rights Reserved.
 * 
 * Contributor(s): Flavio Goncalves Garcia.
 */
package org.candango.carcara.engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.candango.carcara.MainApp;
import org.candango.carcara.model.environment.BasicWorkspace;
import org.candango.carcara.model.environment.Workspace;
import org.candango.carcara.model.environment.WorkspaceReference;
import org.candango.carcara.model.environment.exception.ProjectAlreadyExistsException;
import org.candango.carcara.model.project.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility class that have some util methods to handle workspaces.
 *
 * @author     Flavio Goncalves Garcia <flavio.garcia at candango.org>
 * @copyright  Copyright (c) 2008 - 2009 Candango Open Source Group
 * @link       http://www.candango.org/myfuses
 * @license    http://www.mozilla.org/MPL/MPL-1.1.html  MPL 1.1
 * @version    SVN: $Id: Project.java 23 2008-12-07 02:54:38Z flavio.garcia $
 */
public class WorkspaceHandler {
	
	/**
	 * Workspace configuration path
	 */
	public static final String CONF_PATH = ".conf";
	
	/**
	 * Workspace configuration file
	 */
	public static final String CONF_FILE = "workspace.xml";
	
	/**
	 * Create the workspace files if they aren't created yet
	 * 
	 * @param reference
	 * @return
	 */
	public static Workspace create( WorkspaceReference reference ) {
		
		Workspace workspace = new BasicWorkspace();
		
		workspace.setPath( reference.getPath() );
		
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
		else {
			loadWorkspace( workspace );
		}
		
		/*try {
			save( workspace );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return workspace;
		
	}
	
	/**
	 * Loding workspace data from configuration file
	 * 
	 * @param workspace
	 */
	public static void loadWorkspace( Workspace workspace ) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse( getConfFile( workspace.getReference() ) );
		} 
		catch ( ParserConfigurationException e ) {
			e.printStackTrace();
		} 
		catch ( SAXException e ) {
			e.printStackTrace();
		} 
		catch ( IOException e ) {
			e.printStackTrace();
		}
		
		Element root = document.getDocumentElement();
		
		if( root.getNodeName().toLowerCase().equals( "workspace" ) ) {
			
			NodeList nodes = root.getChildNodes();
			
			for( int i = 0; i < nodes.getLength(); i++ ){
				Node node = nodes.item( i );
				if( !node.getNodeName().equals( "#text" ) ) {
					String name = node.getNodeName(); 
					if( name.toLowerCase().equals( "projects" ) ) {
						Project[] projects = 
							loadProjects( node.getChildNodes() );
						if( projects != null ) {
							for( Project project : projects ){
								try {
									workspace.addProject( project );
								} catch (ProjectAlreadyExistsException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			
		}
		
	}
	
	private static Project[] loadProjects( NodeList nodes ) {
		ArrayList<Project> projectList = new ArrayList<Project>();
		
		for( int i = 0; i < nodes.getLength(); i++ ) {
			Node node = nodes.item( i );
			if( !node.getNodeName().equals( "#text" ) ) {
				String nodeName = node.getNodeName();
				
				if( nodeName.toLowerCase().equals( "project" ) ) {
					
					String name = null;
					
					String pack = null;
					
					
					NodeList children = node.getChildNodes();
					
					for( int j = 0; j < children.getLength(); j++ ) {
						Node child = children.item( j );
						if( !child.getNodeName().equals( "#text" ) ) {
							
							if( child.getNodeName().toLowerCase().equals( "name" ) ) {
								System.out.println( child.getNodeValue() );
								System.out.println( "" + child.getNextSibling().getNodeValue() );
								name = child.getNodeValue();
							}
							
							if( child.getNodeName().toLowerCase().equals( "package" ) ) {
								pack = child.getNodeValue();
							}
							
						}
						
					}
					
					Project project = ProjectHandler.createProject( name , pack );
					
					projectList.add( project );
					
				}
			}
		}
		
		Project[] projects = new Project[ projectList.size() ];
		
		
		return projectList.toArray( projects );
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