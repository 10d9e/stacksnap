package org.stacksnap.config;

public class StacksnapConfiguration {

	private float version;

	private Logging logging;
	
	private String path = "snap";
	
	private Type types;

	private Section error;

	private Section record;

	public Section getError() {
		return error;
	}

	public void setError(Section error) {
		this.error = error;
	}

	public Section getRecord() {
		return record;
	}

	public void setRecord(Section record) {
		this.record = record;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Type getTypes() {
		return types;
	}

	public void setTypes(Type types) {
		this.types = types;
	}

}
