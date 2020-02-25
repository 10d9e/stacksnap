package org.stacksnap;

import java.io.PrintStream;

import net.bytebuddy.agent.builder.AgentBuilder.Listener;
import net.bytebuddy.agent.builder.AgentBuilder.Listener.WithErrorsOnly;
import net.bytebuddy.agent.builder.AgentBuilder.Listener.WithTransformationsOnly;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

/**
 * A listener that writes events to a {@link PrintStream}. This listener prints
 * a line per event, including the event type and the name of the type in
 * question.
 */
@HashCodeAndEqualsPlugin.Enhance
class StackSnapStreamWriting implements Listener {

	/**
	 * The prefix that is appended to all written messages.
	 */
	protected static final String PREFIX = "[stacksnap]";

	/**
	 * The print stream written to.
	 */
	private final PrintStream printStream;

	/**
	 * Creates a new stream writing listener.
	 *
	 * @param printStream The print stream written to.
	 */
	public StackSnapStreamWriting(PrintStream printStream) {
		this.printStream = printStream;
	}

	/**
	 * Creates a new stream writing listener that writes to {@link System#out}.
	 *
	 * @return A listener writing events to the standard output stream.
	 */
	public static StackSnapStreamWriting toSystemOut() {
		return new StackSnapStreamWriting(System.out);
	}

	/**
	 * Creates a new stream writing listener that writes to {@link System#err}.
	 *
	 * @return A listener writing events to the standard error stream.
	 */
	public static StackSnapStreamWriting toSystemError() {
		return new StackSnapStreamWriting(System.err);
	}

	/**
	 * Returns a version of this listener that only reports successfully transformed
	 * classes and failed transformations.
	 *
	 * @return A version of this listener that only reports successfully transformed
	 *         classes and failed transformations.
	 */
	public Listener withTransformationsOnly() {
		return new WithTransformationsOnly(this);
	}

	/**
	 * Returns a version of this listener that only reports failed transformations.
	 *
	 * @return A version of this listener that only reports failed transformations.
	 */
	public Listener withErrorsOnly() {
		return new WithErrorsOnly(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
		printStream.printf(PREFIX + " DISCOVERY %s [%s, %s, loaded=%b]%n", typeName, classLoader, module, loaded);
	}

	/**
	 * {@inheritDoc}
	 */
	public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
			boolean loaded, DynamicType dynamicType) {
		printStream.printf(PREFIX + " TRANSFORM %s [%s, %s, loaded=%b]%n", typeDescription.getName(), classLoader,
				module, loaded);
	}

	/**
	 * {@inheritDoc}
	 */
	public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
		printStream.printf(PREFIX + " IGNORE %s [%s, %s, loaded=%b]%n", typeDescription.getName(), classLoader, module,
				loaded);
	}

	/**
	 * {@inheritDoc}
	 */
	public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded,
			Throwable throwable) {
		synchronized (printStream) {
			printStream.printf(PREFIX + " ERROR %s [%s, %s, loaded=%b]%n", typeName, classLoader, module, loaded);
			throwable.printStackTrace(printStream);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
		printStream.printf(PREFIX + " COMPLETE %s [%s, %s, loaded=%b]%n", typeName, classLoader, module, loaded);
	}
}