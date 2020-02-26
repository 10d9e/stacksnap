package org.stacksnap.agent;

public final class Logger {
	
	private static StackSnapStreamWriting instance = StackSnapStreamWriting.toSystemOut();
	
	
	public static StackSnapStreamWriting toSystemError() {
		instance = StackSnapStreamWriting.toSystemError();
		return instance;
	}
	
	public static StackSnapStreamWriting toSystemOut() {
		instance = StackSnapStreamWriting.toSystemOut();
		return instance;
	}
	
	public static StackSnapStreamWriting getInstance() {
		return instance;
	}
	
	public static void log(String format, Object...args) {
		instance.log(format, args);
	}

}
