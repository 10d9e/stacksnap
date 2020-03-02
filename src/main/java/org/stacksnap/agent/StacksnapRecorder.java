package org.stacksnap.agent;

import java.lang.reflect.Method;

import org.stacksnap.serialization.Entrance;
import org.stacksnap.serialization.Recorder;
import org.stacksnap.serialization.Snapshot;
import org.stacksnap.util.StacknapStack;

import net.bytebuddy.asm.Advice;

public class StacksnapRecorder {

	@Advice.OnMethodEnter
	private static void enter(@Advice.This(optional = true) Object instance, @Advice.Origin Class<?> clazz,
			@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {

		Logger.log("%s: %s", "ENTER StacksnapRecorder", method.getName());

		Snapshot snapshot = new Snapshot(Thread.currentThread().getId(), Entrance.ENTER, instance, method, arguments);
		StacknapStack.put(clazz, method, snapshot);

		// hack
		if (method.getName().equals("dump")) {
			Logger.log("dumping recording...");
			Recorder.persist();
			return;
		}

		Logger.log("Enter " + method.getDeclaringClass().getName() + "." + method.getName());
		Logger.log("Thread: " + Thread.currentThread().getId());

	//	Recorder.append(snapshot);

	}

	@Advice.OnMethodExit(onThrowable = Throwable.class)
	private static void exit(@Advice.Origin Method method, @Advice.This(optional = true) Object instance,
			@Advice.Origin Class<?> clazz, @Advice.AllArguments Object[] arguments,
			@Advice.Thrown Throwable exception) {

		Logger.log("%s: %s", "EXIT StacksnapRecorder", method.getName());

		Snapshot snapshot = new Snapshot(Thread.currentThread().getId(), Entrance.EXIT, instance, method, arguments,
				exception);
		StacknapStack.put(clazz, method, snapshot);

		Logger.log("Exit " + method.getDeclaringClass().getName() + "." + method.getName());
		Logger.log("Thread: " + Thread.currentThread().getId());

//		Recorder.append(snapshot);

		StacknapStack.remove(clazz, method);

	}

}
