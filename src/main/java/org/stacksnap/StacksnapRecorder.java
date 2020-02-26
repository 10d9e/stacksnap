package org.stacksnap;

import java.lang.reflect.Method;

import org.stacksnap.serialization.Entrance;
import org.stacksnap.serialization.Recorder;

import net.bytebuddy.asm.Advice;

public class StacksnapRecorder {

	
	@Advice.OnMethodEnter
	private static void enter(@Advice.This(optional = true) Object self, @Advice.Origin Class<?> clazz,
			@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
		
		// hack 
		if (method.getName().equals("dump")) {
			Logger.log("dumping recording...");
			Recorder.persist();
			return;
		}
		
		Logger.log("Enter " + method.getDeclaringClass().getName() + "." + method.getName());
		Logger.log("Thread: " + Thread.currentThread().getId());
		
		Recorder.append(Thread.currentThread().getId(), Entrance.ENTER, self, method, arguments);
		
		/*
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for(StackTraceElement e : stackTraceElements) {
			Logger.log( String.format("\t%s, %s, %s, %s, %s, %s, %s", e.getClassLoaderName(), e.getClassName(), e.getFileName(), e.getLineNumber(), e.getMethodName(), e.getModuleName(), e.getModuleVersion()  ) );
		}
		*/

	}

	@Advice.OnMethodExit(onThrowable = Throwable.class)
	private static void exit(@Advice.Origin Method method, @Advice.This(optional = true) Object self,
			@Advice.AllArguments Object[] arguments, @Advice.Thrown Throwable exception) {
		
		Logger.log("Exit " + method.getDeclaringClass().getName() + "." + method.getName());
		Logger.log("Thread: " + Thread.currentThread().getId());
		
		Recorder.append(Thread.currentThread().getId(), Entrance.EXIT, self, method, arguments, exception);
		
		/*
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for(StackTraceElement e : stackTraceElements) {
			Logger.log( String.format("\t%s, %s, %s, %s, %s, %s, %s", e.getClassLoaderName(), e.getClassName(), e.getFileName(), e.getLineNumber(), e.getMethodName(), e.getModuleName(), e.getModuleVersion()  ) );
		}
		*/

	}

}
