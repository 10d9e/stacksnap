package org.stacksnap.config;

public class Logging {

	private Loggable systemOut;
	
	private Loggable systemError;

	public Loggable getSystemOut() {
		return systemOut;
	}

	public void setSystemOut(Loggable systemOut) {
		this.systemOut = systemOut;
	}

	public Loggable getSystemError() {
		return systemError;
	}

	public void setSystemError(Loggable systemError) {
		this.systemError = systemError;
	}

}
