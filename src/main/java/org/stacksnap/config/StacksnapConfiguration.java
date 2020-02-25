package org.stacksnap.config;

public class StacksnapConfiguration {
	
	private float version;
	
	private Logging logging;
	
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
	
	public Logging getLogging() {
		return logging;
	}

	public void setLogging(Logging logging) {
		this.logging = logging;
	}

	@Override
	public String toString() {
		return "StacksnapConfiguration [version=" + version + ", logging=" + logging + ", type=" + type + "]";
	}
}
