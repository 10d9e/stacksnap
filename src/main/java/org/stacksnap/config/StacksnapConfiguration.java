package org.stacksnap.config;

public class StacksnapConfiguration {
	
	private float version;
	
	private Type type;
		
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

}
