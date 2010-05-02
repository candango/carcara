package org.candango.carcara.util;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityHandler {
	
	public static String getTemplateString( VelocityContext context, String templatePath ) {
		
		// inicializando o velocity  
		VelocityEngine ve = new VelocityEngine();  
		try {
			ve.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		// choosing the template
		Template t = null;
		try {
			t = ve.getTemplate( templatePath );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		StringWriter writer = new StringWriter();  

		// merging the context with the template  
		try {
			t.merge(context, writer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		String out = writer.toString();  
		
		return out;
	}
	
}
