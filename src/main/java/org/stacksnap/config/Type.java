package org.stacksnap.config;

public class Type {

	private Match match;
	
	private Ignore ignore;

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Ignore getIgnore() {
		return ignore;
	}

	public void setIgnore(Ignore ignore) {
		this.ignore = ignore;
	}

	@Override
	public String toString() {
		return "Type [match=" + match + ", ignore=" + ignore + "]";
	}
	
}
