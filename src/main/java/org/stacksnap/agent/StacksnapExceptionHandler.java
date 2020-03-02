package org.stacksnap.agent;

import java.lang.reflect.Method;

import org.stacksnap.serialization.Camera;
import org.stacksnap.serialization.Entrance;
import org.stacksnap.serialization.Snapshot;
import org.stacksnap.util.StacknapStack;

import net.bytebuddy.asm.Advice;

public class StacksnapExceptionHandler {

	@Advice.OnMethodEnter
	private static void enter(@Advice.This(optional = true) Object instance, @Advice.Origin Class<?> clazz,
			@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
		
		Logger.log("%s: %s", "ENTER StacksnapExceptionHandler", method.getName());
		StacknapStack.put(clazz, method,
				new Snapshot(Thread.currentThread().getId(), Entrance.ENTER, instance, method, arguments));

	}

	@Advice.OnMethodExit(onThrowable = Throwable.class)
	private static void exit(@Advice.Origin Method method, @Advice.This(optional = true) Object instance,
			@Advice.Origin Class<?> clazz, @Advice.AllArguments Object[] arguments, @Advice.Thrown Throwable e) {
		
		Logger.log("%s: %s", "EXIT StacksnapExceptionHandler", method.getName());
		
		Snapshot snapshot = new Snapshot(Thread.currentThread().getId(), Entrance.EXIT, instance, method, arguments, e);
		StacknapStack.put(clazz, method, snapshot);
		if (e != null) {
			Camera.snap(snapshot);
		}
		StacknapStack.remove(clazz, method);
	}

}
