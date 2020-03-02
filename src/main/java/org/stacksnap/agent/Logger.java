package org.stacksnap.agent;

public final class Logger {
	
	private static StacksnapStreamWriting instance = StacksnapStreamWriting.toSystemOut();
	
	
	public static StacksnapStreamWriting toSystemError() {
		instance = StacksnapStreamWriting.toSystemError();
		return instance;
	}
	
	public static StacksnapStreamWriting toSystemOut() {
		instance = StacksnapStreamWriting.toSystemOut();
		return instance;
	}
	
	public static StacksnapStreamWriting getInstance() {
		return instance;
	}
	
	public static void log(String format, Object...args) {
		instance.log(format, args);
	}

	public static void error(String string) {
		instance.error(string);
	}

}
