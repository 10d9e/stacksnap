package org.stacksnap.agent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.stacksnap.serialization.Camera;
import org.stacksnap.serialization.Entrance;
import org.stacksnap.util.FastCache;

import net.bytebuddy.asm.Advice;

public class StacksnapExceptionHandler {

	//public static Map<String, Object> context = new HashMap<>();
	
	// Test with crunchifyTimeToLive = 200 seconds
    // crunchifyTimerInterval = 500 seconds
    // maxItems = 100
	public static FastCache<String, Object> context = new FastCache(200, 500, 100);
	
	@Advice.OnMethodEnter
	private static void enter(@Advice.This(optional = true) Object obj, @Advice.Origin Class<?> clazz,
			@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
	
		context.put(clazz.getName(), obj);
		/*
		System.out.println("Enter " + method.getDeclaringClass() + ":" + method.getName());
		for (Object argument : arguments) {
			System.out.println("\t" + argument.getClass().getSimpleName() + ":" + argument.toString());
		}
		*/
	}

	@Advice.OnMethodExit(onThrowable = Throwable.class)
	private static void exit(@Advice.Origin Method method, @Advice.This(optional = true) Object instance,
			@Advice.AllArguments Object[] arguments, @Advice.Thrown Throwable e) {
		
		if (e != null) {
			Camera.snap(Thread.currentThread().getId(), Entrance.EXIT, instance, method, arguments, e, context);
			//context.cleanup();
		}
	}

}
