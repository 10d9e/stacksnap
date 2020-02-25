package org.stacksnap.config;

public class StacksnapConfiguration {
	
	private float version;
	
	private Logging logging;
	
	private Type types;
	
	private Method methods;
		
	public Type getTypes() {
		return types;
	}

	public void setTypes(Type types) {
		this.types = types;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}
	
	public Logging getLogging() {
		return logging;
	}

	public void setLogging(Logging logging) {
		this.logging = logging;
	}

	public Method getMethods() {
		return methods;
	}

	public void setMethods(Method methods) {
		this.methods = methods;
	}
}
