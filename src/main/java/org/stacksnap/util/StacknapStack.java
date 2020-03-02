package org.stacksnap.util;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.stacksnap.serialization.Snapshot;

public final class StacknapStack {
	
	public static Map<String, Snapshot> STACKSNAPS = new LinkedHashMap<>();
	
	public static Snapshot get(Object key) {
		return STACKSNAPS.get(key);
	}
	
	public static Snapshot get(String className, String methodName) {
		return STACKSNAPS.get(named(className, methodName));
	}
	
	public static Snapshot get(Class<?> clazz, Method method) {
		return STACKSNAPS.get(named(clazz, method));
	}
	
	public static Snapshot put(String className, String methodName, Snapshot value) {
		return STACKSNAPS.put(named(className, methodName), value);
	}
	
	public static Snapshot put(Class<?> clazz, Method method, Snapshot value) {
		return STACKSNAPS.put(named(clazz, method), value);
	}

	public static Snapshot put(String key, Snapshot value) {
		return STACKSNAPS.put(key, value);
	}
	
	public static Snapshot remove(Class<?> clazz, Method method) {
		return STACKSNAPS.remove(named(clazz, method));
	}
	
	public static Snapshot remove(String className, String methodName) {
		return STACKSNAPS.remove(named(className, methodName));
	}

	public static Snapshot remove(Object key) {
		return STACKSNAPS.remove(key);
	}

	public static String named(Class<?> clazz, Method method) {
		return String.format("%s:%s", clazz.getName(), method.getName());
	}
	
	public static String named(String className, String methodName) {
		return String.format("%s:%s", className, methodName);
	}

}
