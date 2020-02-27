package org.stacksnap.config;

public class Type {

	private BaseType include;
	
	private BaseType ignore;

	public BaseType getInclude() {
		return include;
	}

	public void setInclude(BaseType include) {
		this.include = include;
	}

	public BaseType getIgnore() {
		return ignore;
	}

	public void setIgnore(BaseType ignore) {
		this.ignore = ignore;
	}

	
}
