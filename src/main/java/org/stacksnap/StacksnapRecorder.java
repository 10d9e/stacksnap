package org.stacksnap;

import java.lang.reflect.Method;

import net.bytebuddy.asm.Advice;

public class StacksnapRecorder {

	
	@Advice.OnMethodEnter
	private static void enter(@Advice.This(optional = true) Object self, @Advice.Origin Class<?> clazz,
			@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
		
		Logger.log("Enter " + method.getDeclaringClass().getName() + "." + method.getName());
		Logger.log("Thread: " + Thread.currentThread().getId());
				
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for(StackTraceElement e : stackTraceElements) {
			Logger.log( String.format("\t%s, %s, %s, %s, %s, %s, %s", e.getClassLoaderName(), e.getClassName(), e.getFileName(), e.getLineNumber(), e.getMethodName(), e.getModuleName(), e.getModuleVersion()  ) );
		}

	}

	@Advice.OnMethodExit(onThrowable = Throwable.class)
	private static void exit(@Advice.Origin Method method, @Advice.This(optional = true) Object self,
			@Advice.AllArguments Object[] arguments, @Advice.Thrown Throwable exception) {
		
		Logger.log("Exit " + method.getDeclaringClass().getName() + "." + method.getName());
		Logger.log("Thread: " + Thread.currentThread().getId());
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for(StackTraceElement e : stackTraceElements) {
			Logger.log( String.format("\t%s, %s, %s, %s, %s, %s, %s", e.getClassLoaderName(), e.getClassName(), e.getFileName(), e.getLineNumber(), e.getMethodName(), e.getModuleName(), e.getModuleVersion()  ) );
		}

	}

}
