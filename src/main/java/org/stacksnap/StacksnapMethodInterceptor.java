package org.stacksnap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.stacksnap.serialization.Camera;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

public class StacksnapMethodInterceptor {

	@RuntimeType
	public static Object intercept(@This(optional = true) Object obj, @Origin Class<?> clazz, @Origin Method method,
			@AllArguments Object[] args, @SuperCall Callable<?> callable) {

		if (obj != null) {
			displayFields(obj, clazz);
		}
		System.out.println("Intercepting method " + method.getName());
		long start = System.currentTimeMillis();

		Object rVal = null;
		try {

			rVal = callable.call();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(obj != null) {
				String file = Camera.snap(obj, method, args, e);
				System.out.println(file);
			}
		} finally {
			System.out.println(method + " took " + (System.currentTimeMillis() - start));

			if (obj != null) {
				displayFields(obj, clazz);
			}

			String params = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
			System.out.println(method.getReturnType().getSimpleName() + " " + method.getName() + "(" + params
					+ ") took " + ((System.nanoTime() - start) / 1000000) + " ms");

		}
		return rVal;
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
