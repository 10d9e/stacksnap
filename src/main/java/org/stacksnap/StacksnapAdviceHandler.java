package org.stacksnap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

public class StacksnapAdviceHandler {

	//private static final String FOO = "foo", BAR = "bar";

	@Advice.OnMethodEnter
	private static void enter( //@Advice.Local(FOO) Object foo, @Advice.Local(BAR) Object bar,
			@Advice.This(optional = true) Object obj,
			@Advice.Origin Class<?> clazz,
			@Advice.Origin Method method,
			@Advice.AllArguments Object[] arguments
			) {
		System.out.println("Enter " + method.getDeclaringClass() + ":" + method.getName());
		for (Object argument : arguments) {
            System.out.println("\t" + argument.getClass().getSimpleName() + ":" + argument.toString());
        }
	}

	@Advice.OnMethodExit(onThrowable = Throwable.class)
	private static void exit(@Advice.Origin Method method,
			@Advice.This(optional = true) Object instance,
			@Advice.AllArguments Object[] arguments,
			@Advice.Thrown Throwable e) {
		System.out.println("Exit " + method.getDeclaringClass() + ":" + method.getName());
		for (Object argument : arguments) {
            System.out.println("\t" + argument.getClass().getSimpleName() + ":" + argument.toString());
        }
		if(e != null) {
			System.out.println(e);
			Camera.snap(instance, method, arguments, e);
		}		
	}
	
	private static void displayFields(Object obj, Class<?> clazz) {
		for (Field f : clazz.getDeclaredFields()) {
			f.setAccessible(true);
			Object val;
			try {
				val = f.get(obj);
				System.out.println(String.format("\tfield: %s -> %s", f.getName(), val));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
