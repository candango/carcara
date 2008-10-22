package org.candango.carcara.model.database;

import java.util.ArrayList;

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
    
}
