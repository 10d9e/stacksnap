package org.stacksnap.serialization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.stacksnap.util.FastCache;
import org.stacksnap.util.StacknapStack;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public final class FrameUtil {

	private final static SourceLocator[] sourceLocators = { new FileSearchSourceLocator("./src/main/java"),
			new FileSearchSourceLocator("./src"), new FileSearchSourceLocator("./src/test/java") };

	/**
	 * Parses all stack frames for an exception into a view model.
	 *
	 * @param e An exception.
	 * @return A view model for the frames in the exception.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static List<Map<String, Object>> parseFrames(Snapshot snapshot) {

		ImmutableList.Builder<Map<String, Object>> frames = ImmutableList.builder();
		if (snapshot.getError() != null) {
			for (StackTraceElement frame : snapshot.getError().getStackTrace()) {

				Snapshot s = StacknapStack.get(frame.getClassName(), frame.getMethodName());
				frames.add(parseFrame(frame, s));
			}
		}
		return frames.build();
	}

	private static Map<String, Object> methodParameters(Snapshot s) {
		if (s == null) {
			return null;
		}
		Method m = s.getMethod();
		Object[] args = s.getArguments();
		LinkedHashMap<String, Object> info = new LinkedHashMap<>();
		Parameter[] params = m.getParameters();
		for (int i = 0; i < params.length; i++) {
			info.put(String.format("%s [%s]", params[i].getName(), params[i].getType().getCanonicalName()),
					args[i].toString());
		}
		if (info.isEmpty()) {
			return null;
		}
		return info;
	}

	private static Map<String, Object> targetToMap(Snapshot s) {
		if (s == null) {
			return null;
		}
		Object target = s.getTarget();
		if (target == null) {
			return null;
		}
		Class<?> clazz = target.getClass();
		LinkedHashMap<String, Object> info = new LinkedHashMap<>();
		// info.put("[this]", clazz.getName());

		for (Field f : clazz.getDeclaredFields()) {
			try {
				f.setAccessible(true);
				String name = String.format("%s [%s]", f.getName(), f.getType().getCanonicalName());
				if (target != null) {
					info.put(name, f.get(target).toString());
				} else if (Modifier.isStatic(f.getModifiers())) {
					info.put(name, f.get(null));
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return info;
	}

	/**
	 * Parses a stack frame into a view model.
	 *
	 * @param sframe A stack trace frame.
	 * @return A view model for the given frame in the template.
	 */
	private static Map<String, Object> parseFrame(StackTraceElement sframe, Snapshot framesnap) {

		ImmutableMap.Builder<String, Object> frame = ImmutableMap.builder();
		frame.put("file", Optional.fromNullable(sframe.getFileName()).or("<#unknown>"));

		Object frameThis = targetToMap(framesnap);
		frame.put("this", Optional.fromNullable(frameThis).or(""));

		Object params = methodParameters(framesnap);
		frame.put("params", Optional.fromNullable(params).or(""));

		frame.put("class", Optional.fromNullable(sframe.getClassName()).or(""));
		frame.put("line", Optional.fromNullable(Integer.toString(sframe.getLineNumber())).or(""));
		frame.put("function", Optional.fromNullable(sframe.getMethodName()).or(""));
		frame.put("comments", ImmutableList.of());

		// Try to find the source file corresponding to this exception stack frame.
		// Go through the locators in order until the source file is found.
		Optional<File> file = Optional.absent();
		for (SourceLocator locator : sourceLocators) {
			file = locator.findFileForFrame(sframe);

			if (file.isPresent()) {
				break;
			}
		}

		// Fetch +-10 lines from the triggering line.
		Optional<Map<Integer, String>> codeLines = fetchFileLines(file, sframe);

		if (codeLines.isPresent()) {
			// Write the starting line number (1-indexed).
			frame.put("code_start", Iterables.reduce(codeLines.get().keySet(), Integer.MAX_VALUE, Math::min) + 1);

			// Write the code as a single string, replacing empty lines with a " ".
			frame.put("code",
					Joiner.on("\n").join(Iterables.map(codeLines.get().values(), (x) -> x.length() == 0 ? " " : x)));

			// Write the canonical path.
			try {
				frame.put("canonical_path", file.get().getPath());
			} catch (Exception e) {
				// Not much we can do, so ignore and just don't have the canonical path.
			}
		}

		return frame.build();
	}

	/**
	 * Fetches the lines of the source file corresponding to a StackTraceElement
	 * (fetches 20 lines total centered on the line number given in the trace).
	 *
	 * @param file  An optional text file.
	 * @param frame A stack trace frame.
	 * @return An optional map of line numbers to the content of the lines (not
	 *         terminated with \n).
	 */
	private static Optional<Map<Integer, String>> fetchFileLines(Optional<File> file, StackTraceElement frame) {
		// If no line number is given or no file exists, we can't fetch lines.
		if (!file.isPresent() || frame.getLineNumber() == -1) {
			return Optional.absent();
		}

		// Otherwise, fetch 20 lines centered on the number provided in the trace.
		ImmutableMap.Builder<Integer, String> lines = ImmutableMap.builder();
		int start = Math.max(frame.getLineNumber() - 10, 0);
		int end = start + 20;
		int current = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(file.get()))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (current < start) {
					current += 1;
					continue;
				}

				if (current > end) {
					break;
				}

				lines.put(current, line);
				current += 1;
			}
		} catch (Exception e) {
			// If we get an IOException, not much we can do... just ignore it and move on.
			return Optional.absent();
		}

		return Optional.of(lines.build());
	}
}
