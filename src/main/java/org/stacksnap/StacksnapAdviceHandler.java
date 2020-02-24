package org.stacksnap;

import java.lang.reflect.Method;

import net.bytebuddy.asm.Advice;

public class StacksnapAdviceHandler {

	@Advice.OnMethodEnter
	private static void enter(@Advice.This(optional = true) Object obj, @Advice.Origin Class<?> clazz,
			@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
		System.out.println("Enter " + method.getDeclaringClass() + ":" + method.getName());
		for (Object argument : arguments) {
			System.out.println("\t" + argument.getClass().getSimpleName() + ":" + argument.toString());
		}
	}

	@Advice.OnMethodExit(onThrowable = Throwable.class)
	private static void exit(@Advice.Origin Method method, @Advice.This(optional = true) Object instance,
			@Advice.AllArguments Object[] arguments, @Advice.Thrown Throwable e) {
		System.out.println("Exit " + method.getDeclaringClass() + ":" + method.getName());
		for (Object argument : arguments) {
			System.out.println("\t" + argument.getClass().getSimpleName() + ":" + argument.toString());
		}
		if (e != null) {
			System.out.println(e);
			Camera.snap(instance, method, arguments, e);
		}
	}

}
