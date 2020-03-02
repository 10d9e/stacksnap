package org.stacksnap.serialization;

import java.lang.reflect.Method;

import org.stacksnap.serialization.Camera.Test;
import org.stacksnap.util.FastCache;

public class CameraTest {

	private static int TEST = 42;

	public static void main(String... args) throws NoSuchMethodException, SecurityException {

		Test t = new Camera.Test();

		Method m = t.getClass().getDeclaredMethod("doIt");

		Snapshot s = new Snapshot(Thread.currentThread().getId(), Entrance.ENTER, t, m, new Object[] {},
				new Exception("Fna"));

		String filename = Camera.snap(s);

		// String filename =
		// "snap/error-org.stacksnap.serialization.Camera$Test-2020-02-29-18:04:07.073";
		Test restored = Camera.restore(filename);
		System.out.println(restored);

		Object out = Camera.replay(filename);
		System.out.println("Replayed: " + out);
	}

}
