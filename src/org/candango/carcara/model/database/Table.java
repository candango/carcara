package org.candango.carcara.model.database;

import java.util.ArrayList;

import org.candango.carcara.util.CodeHandler;

public class Table {
	
	private String name;
	
    private ArrayList<Field> fieldList = new ArrayList<Field>();
	
    public void addField( Field field ) {
    	fieldList.add( field );
    }
    
    public Field[] getFields() {
    	Field[] field = new Field[ fieldList.size() ];
    	return fieldList.toArray( field );
    }
    
    public Field[] getSerials() {
    	int serialCount = 0;
    	
    	Field[] fields = new Field[ fieldList.size() ];
    	
    	for( Field field : fieldList.toArray( fields ) )  {
    		if( field.isSerial() ) {
    			serialCount++;
    		}
    	}
    	
    	Field[] serials = new Field[ serialCount ];
    	
    	int i = 0;
    	
    	for( Field field : fieldList.toArray( fields ) )  {
    		if( field.isSerial() ) {
    			serials[ i++ ] = field;
    		}
    	}
    	
    	return serials;
    }
    
    public Field[] getNonSerials() {
    	int nonSerialCount = 0;
    	
    	Field[] fields = new Field[ fieldList.size() ];
    	
    	for( Field field : fieldList.toArray( fields ) )  {
    		if( !field.isSerial() ) {
    			nonSerialCount++;
    		}
    	}
    	
    	Field[] nonSerials = new Field[ nonSerialCount ];
    	
    	int i = 0;
    	
    	for( Field field : fieldList.toArray( fields ) )  {
    		if( !field.isSerial() ) {
    			nonSerials[ i++ ] = field;
    		}
    	}
    	
    	return nonSerials;
    }
    
    public Field[] getPks() {
    	int pkCount = 0;
    	
    	Field[] fields = new Field[ fieldList.size() ];
    	
    	for( Field field : fieldList.toArray( fields ) )  {
    		if( field.isPk() ) {
    			pkCount++;
    		}
    	}
    	
    	Field[] pks = new Field[ pkCount ];
    	
    	int i = 0;
    	
    	for( Field field : fieldList.toArray( fields ) )  {
    		if( field.isPk() ) {
    			pks[ i++ ] = field;
    		}
    	}
    	
    	return pks;
    }
    
    public Field[] getNonPks() {
    	int nonPkCount = 0;
    	
    	Field[] fields = new Field[ fieldList.size() ];
    	
    	for( Field field : fieldList.toArray( fields ) )  {
    		if( !field.isPk() ) {
    			nonPkCount++;
    		}
    	}
    	
    	Field[] nonPks = new Field[ nonPkCount ];
    	
    	int i = 0;
    	
    	for( Field field : fieldList.toArray( fields ) )  {
    		if( !field.isPk() ) {
    			nonPks[ i++ ] = field;
    		}
    	}
    	
    	return nonPks;
    }
    
    public String toString() {
    	String out = "*\tDatabase " +  getName() + "\n";
    	out += "*\t\tFields:" + "\n";
    	for( Field field : getFields() ) {
    		out += field;
    	}
    	return out;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	public String getEntityName() {
		return CodeHandler.getEntityName( getName() );
	}
	
	public String getAttributeName() {
		return CodeHandler.getAttributeName( getName() );
	}
}