package org.stacksnap;

import java.lang.reflect.Method;

public class Snapshot {

	private Object target;

	private Method method;

	private Throwable error;

	private Object[] arguments;

	public Snapshot() {
	}

	public Snapshot(Object target, Method method, Throwable error, Object[] arguments) {
		super();
		this.target = target;
		this.method = method;
		this.error = error;
		this.arguments = arguments;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

}
