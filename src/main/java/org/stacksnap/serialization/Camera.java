package org.stacksnap.serialization;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.stacksnap.agent.Logger;
import org.stacksnap.config.StacksnapConfigurationBuilder;
import org.stacksnap.util.FastCache;

import com.thoughtworks.xstream.XStream;

public final class Camera {

	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS");

	private static XStream xstream = new XStream();

	static {
		XStream.setupDefaultSecurity(xstream);
		xstream.allowTypesByWildcard(new String[] { "**.*" });
		xstream.denyTypes(new Class[]{XStream.class});
		xstream.alias("Snapshot", Snapshot.class);
	}

	public static <T> String snap(Snapshot snapshot) {
		try {
			final String SNAP_DIRECTORY = StacksnapConfigurationBuilder.getConfiguration().getPath();
			
			List<Map<String, Object>> frames = FrameUtil.parseFrames(snapshot);
			snapshot.setFrames(frames);
			
			Object target = snapshot.getTarget();
			Method method = snapshot.getMethod();

			String filename;
			if(target != null) {
				filename = SNAP_DIRECTORY + File.separator + "error-" + target.getClass().getName() + "." + method.getName() + "-"
						+ SDF.format(new Date());
			} else {
				filename = SNAP_DIRECTORY + File.separator + "error-" + method.getName() + "-"
						+ SDF.format(new Date());
			}
			
			String xml = xstream.toXML(snapshot);

			if (Files.notExists(Paths.get(SNAP_DIRECTORY))) {
				Files.createDirectory(Paths.get(SNAP_DIRECTORY));
			}

			Files.write(Paths.get(filename), xml.getBytes());
			Logger.log("created stack snapshot: " + filename);
			return filename;
		} catch (Exception e) {
			Logger.log("Error creating snapshot: " + e.getMessage());
			//e.printStackTrace();
		}
		return null;

	}
	
	public static Snapshot restoreSnapshot(String path) {
		try {
			String xml = Files.readString(Paths.get(path));
			return (Snapshot) xstream.fromXML(xml);

		} catch (Exception e) {
			Logger.log("Error restoring snapshot: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T restore(String path) {
		T rValue = null;
		try {
			String xml = Files.readString(Paths.get(path));
			Snapshot snap = (Snapshot) xstream.fromXML(xml);
			rValue = (T) snap.getTarget();

		} catch (Exception e) {
			Logger.log("Error restoring snapshot: " + e.getMessage());
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
		
		private static final String SECRET = "hellow";
		
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

	
}
