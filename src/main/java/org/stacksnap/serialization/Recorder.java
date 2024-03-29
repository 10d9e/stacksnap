package org.stacksnap.serialization;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.stacksnap.agent.Logger;
import org.stacksnap.config.StacksnapConfigurationBuilder;

import com.thoughtworks.xstream.XStream;

public final class Recorder {

	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS");

	private static XStream xstream = new XStream();

	static {
		XStream.setupDefaultSecurity(xstream);
		xstream.allowTypesByWildcard(new String[] { "**.*" });
		xstream.alias("Snapshot", Snapshot.class);
	}

	public static List<Snapshot> recording = new ArrayList<>();

	public static <T> void append(Snapshot snapshot) {
		try {
			snapshot.setError(new Exception("Stack trace"));
			List<Map<String, Object>> frames = FrameUtil.parseFrames(snapshot);
			snapshot.setFrames(frames);

			recording.add(snapshot);			
		} catch (Exception e) {
			Logger.log("Error creating snapshot: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static String persist() {
		final String SNAP_DIRECTORY = StacksnapConfigurationBuilder.getConfiguration().getPath();
		
		final String filename = SNAP_DIRECTORY + File.separator + "recording-" + SDF.format(new Date());
		try {
			String xml = xstream.toXML(recording);

			if (Files.notExists(Paths.get(SNAP_DIRECTORY))) {
				Files.createDirectory(Paths.get(SNAP_DIRECTORY));
			}

			Files.write(Paths.get(filename), xml.getBytes());
			Logger.log("created stack recording: " + filename);

		} catch (Exception e) {
			Logger.log("Error creating recording: " + e.getMessage());
			e.printStackTrace();
		}

		return filename;
	}

	@SuppressWarnings("unchecked")
	public static <T> T restore(String path) {
		T rValue = null;
		try {
			String xml = Files.readString(Paths.get(path));
			rValue = (T) xstream.fromXML(xml);
		} catch (Exception e) {
			Logger.log("Error restoring recording: " + e.getMessage());
			e.printStackTrace();
		}
		return rValue;
	}

	public static Object replay(String path) {
		try {
			String xml = Files.readString(Paths.get(path));
			Snapshot snap = (Snapshot) xstream.fromXML(xml);
			Object target = snap.getTarget();
			Method m = snap.getMethod();
			Object[] args = snap.getArguments();

			return m.invoke(target, args);

		} catch (Exception e) {
			Logger.log("Error replaying snapshot: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static class Test {
		private int age = 44;
		private String name = "Jay";
		private Boolean male = true;

		public void doIt() {
			age = 90;
		}

		@Override
		public String toString() {
			return "Test [age=" + age + ", name=" + name + ", male=" + male + "]";
		}

	}

	public static void main(String... args) throws NoSuchMethodException, SecurityException {

		Test t = new Recorder.Test();

		Method m = t.getClass().getDeclaredMethod("doIt");
		
		long currentThreadId = Thread.currentThread().getId();
				
		Recorder.append(new Snapshot(currentThreadId, Entrance.ENTER, t, m, new Object[] {}));
		Recorder.append(new Snapshot(currentThreadId, Entrance.ENTER, t, m, new Object[] {}));
		Recorder.append(new Snapshot(currentThreadId, Entrance.ENTER, t, m, new Object[] {}));
		Recorder.append(new Snapshot(currentThreadId, Entrance.ENTER, t, m, new Object[] {}));
		Recorder.append(new Snapshot(currentThreadId, Entrance.ENTER, t, m, new Object[] {}));
		Recorder.append(new Snapshot(currentThreadId, Entrance.ENTER, t, m, new Object[] {}));
		
		String filename = Recorder.persist();
		List<Snapshot> recording = Recorder.restore(filename);

		System.out.println(recording);
	}
}
