package org.candango.carcara.engine;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.candango.carcara.MainApp;
import org.candango.carcara.model.environment.BasicEnvironment;
import org.candango.carcara.model.environment.BasicWorkspaceReference;
import org.candango.carcara.model.environment.Environment;
import org.candango.carcara.model.environment.WorkspaceReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EnvironmentHandler {
	
	public static Environment loadEnvironment() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse( MainApp.getConfigurationFile() );
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
		
		if( root.getNodeName().toLowerCase().equals( "environment" ) ) {
			Environment environment = new BasicEnvironment();
			
			NodeList nodes = root.getChildNodes();
			
			for( int i = 0; i < nodes.getLength(); i++ ){
				Node node = nodes.item( i );
				if( !node.getNodeName().equals( "#text" ) ) {
					String name = node.getNodeName(); 
					if( name.toLowerCase().equals( "workspaces" ) ) {
						WorkspaceReference[] references = 
							loadWorkspaces( node.getChildNodes() );
						
						for( WorkspaceReference reference : references ){
							environment.addReference( reference );
						}
					}
				}
			}
			return environment;
		}
		
		return null;
	}
	
	private static WorkspaceReference[] loadWorkspaces( NodeList nodes ) {
		ArrayList<WorkspaceReference> referenceList = 
				new ArrayList<WorkspaceReference>();
		
		for( int i = 0; i < nodes.getLength(); i++ ){
			Node node = nodes.item( i );
			if( !node.getNodeName().equals( "#text" ) ) {
				String name = node.getNodeName(); 
				if( name.toLowerCase().equals( "workspace" ) ) {
					WorkspaceReference reference = new BasicWorkspaceReference();
					for( int j = 0; j < node.getAttributes().getLength(); j++) {
						if( node.getAttributes().item( j ).getNodeName().toLowerCase().equals( "path" ) ) {
							reference.setPath( node.getAttributes().item( j ).getNodeValue() );
						}
						else if( node.getAttributes().item( j ).getNodeName().toLowerCase().equals( "default" ) ) {
							if( node.getAttributes().item( j ).getNodeValue().toLowerCase().equals( "true" ) ) {
								reference.setDefault( true );
							}
						}
					}
					referenceList.add( reference );
				}
			}
		}
		WorkspaceReference[] references = new WorkspaceReference[ referenceList.size() ];
		
		return referenceList.toArray( references );
	}
	
}