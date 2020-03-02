package org.stacksnap.serialization;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Snapshot {
	
	private Entrance entrance;
	
	private long threadId;

	private Object target;

	private Method method;

	private Throwable error;

	private Object[] arguments;
	
	List<Map<String, Object>> frames;

	public Snapshot() {
	}

	public Snapshot(long threadId, Entrance entrance, Object target, Method method, Object[] arguments, Throwable error, List<Map<String, Object>> frames) {
		super();
		this.threadId = threadId;
		this.entrance = entrance;
		this.target = target;
		this.method = method;
		this.error = error;
		this.arguments = arguments;
		this.frames = frames;
	}
	
	public Snapshot(long threadId, Entrance entrance, Object target, Method method, Object[] arguments, Throwable error) {
		super();
		this.threadId = threadId;
		this.entrance = entrance;
		this.target = target;
		this.method = method;
		this.error = error;
		this.arguments = arguments;
	}
	
	public Snapshot(long threadId, Entrance entrance, Object target, Method method, Object[] arguments) {
		super();
		this.threadId = threadId;
		this.entrance = entrance;
		this.target = target;
		this.method = method;
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

	public Entrance getEntrance() {
		return entrance;
	}

	public void setEntrance(Entrance entrance) {
		this.entrance = entrance;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public List<Map<String, Object>> getFrames() {
		return frames;
	}

	public void setFrames(List<Map<String, Object>> frames) {
		this.frames = frames;
	}

	
}
