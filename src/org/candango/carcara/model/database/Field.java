package org.candango.carcara.model.database;

public class Field {
	
	private String name;
	
	private String type;

	private boolean pk = false; 
	
	private boolean serial = false;
	
	public boolean isSerial() {
		return serial;
	}

	public void setSerial(boolean serial) {
		this.serial = serial;
	}

	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}
	
	public String toString() {
    	String out = "*\t\t " +  getName() + " " + getType() + ( isPk() ? " (PK)" : "" ) + "\n";
    	return out;
    }
	
	public boolean isPk() {
		return pk;
	}

	public void setPk( boolean pk ) {
		this.pk = pk;
	}
	
}