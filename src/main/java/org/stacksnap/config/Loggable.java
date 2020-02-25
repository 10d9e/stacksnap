package org.stacksnap.config;

public class Loggable {
	
	boolean errorsOnly;
	
	boolean transformationsOnly;

	public boolean isErrorsOnly() {
		return errorsOnly;
	}

	public void setErrorsOnly(boolean errorsOnly) {
		this.errorsOnly = errorsOnly;
	}

	public boolean isTransformationsOnly() {
		return transformationsOnly;
	}

	public void setTransformationsOnly(boolean transformationsOnly) {
		this.transformationsOnly = transformationsOnly;
	}

	@Override
	public String toString() {
		return "Loggable [errorsOnly=" + errorsOnly + ", transformationsOnly=" + transformationsOnly + "]";
	}
}
