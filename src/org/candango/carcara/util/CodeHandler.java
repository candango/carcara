package org.candango.carcara.util;

public class CodeHandler {
	
	public static String getEntityName( String entt ) {
		
		String enttName = "";
		
		String[] enttNameX = entt.split( "_" );
		
		for( String s : enttNameX ) {
			enttName += upperCaseFirst( s );
		}
		
		return enttName;
		
	}
	
	
	public static String getAttributeName( String attr ) {
		
		String attrName = lowerCaseFirst( attr );
		
		String[] attrNameX = attrName.split( "_" );
		
		attrName = "";
			
		boolean first = true;
		
		for( String s : attrNameX ) {
			attrName += first ? s : upperCaseFirst( s );
			first = false;
		}
		
		return attrName;
	}
	
	public static String getGetterName( String attr ) {
		return "get" + getMethodPostfix( attr );
	}
	
	public static String getSetterName( String attr ) {
		return "set" + getMethodPostfix( attr );
	}
	
	private static String getMethodPostfix( String attr ) {
		String[] methodNameX = attr.split( "_" );
		
		String methodName = "";
		
		for( String s : methodNameX ) {
			methodName += upperCaseFirst( s );
		}
		
		return methodName;
	}
	
	public static String upperCaseFirst( String s ) {
		return s.substring( 0, 1 ).toUpperCase() +
			s.substring( 1 , s.length() );
	}
	
	public static String lowerCaseFirst( String s ) {
		return s.substring( 0, 1 ).toLowerCase() +
		s.substring( 1 , s.length() );
	}
	
}
