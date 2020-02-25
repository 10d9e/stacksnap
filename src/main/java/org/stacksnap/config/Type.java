package org.stacksnap.config;

public class Type {

	private BaseType match;
	
	private BaseType ignore;

	public BaseType getMatch() {
		return match;
	}

	public void setMatch(BaseType match) {
		this.match = match;
	}

	public BaseType getIgnore() {
		return ignore;
	}

	public void setIgnore(BaseType ignore) {
		this.ignore = ignore;
	}

	@Override
	public String toString() {
		return "Type [match=" + match + ", ignore=" + ignore + "]";
	}
	
}
