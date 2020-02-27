package org.stacksnap.agent;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

import java.lang.instrument.Instrumentation;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.stacksnap.config.Loggable;
import org.stacksnap.config.StacksnapConfiguration;
import org.stacksnap.config.StacksnapConfigurationBuilder;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

public class StacksnapAgent {

	public static void agentmain(String arguments, Instrumentation instrumentation) {
		premain(arguments, instrumentation);
	}

	public static void premain(String arguments, Instrumentation instrumentation) {
		Logger.log("Welcome to Stacksnap.");
		
		// suppress superfluous Illegal access warnings from JDK9+
		exportAndOpen(instrumentation);

		StacksnapConfiguration config = StacksnapConfigurationBuilder.build();

		if (config == null) {
			return;
		}

		AgentBuilder agentBuilder = new AgentBuilder.Default().disableClassFormatChanges()
				.with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION);

		agentBuilder = handleLogging(config, agentBuilder);

		agentBuilder
				.ignore(nameStartsWith("net.bytebuddy").or(nameStartsWith("org.stacksnap"))
						.or(nameStartsWith("com.yevdo.jwildcard")).or(nameStartsWith("com.thoughtworks.xstream"))
						.or(nameStartsWith("org.xmlpull")).or(nameStartsWith("jdk")).or(nameStartsWith("java"))
						.or(nameStartsWith("sun")).or(nameStartsWith("com.sun")).or(nameStartsWith("org.yaml")).or(
								StacksnapConfigurationBuilder.typeIgnores()))
				.type(StacksnapConfigurationBuilder
						.typeIncludes())
				.transform((builder, type, classLoader, module) -> builder
						.visit(Advice.to(StacksnapExceptionHandler.class)
								.on(isMethod().and(StacksnapConfigurationBuilder.errorMethodIncludes())))

						.visit(Advice.to(StacksnapRecorder.class)
								.on(isMethod().and(StacksnapConfigurationBuilder.recordMethodIncludes())))

				)

				.installOn(instrumentation);

	}

	private static void exportAndOpen(Instrumentation instrumentation) {
		Set<Module> unnamed = Collections.singleton(ClassLoader.getSystemClassLoader().getUnnamedModule());
		ModuleLayer.boot().modules()
				.forEach(module -> instrumentation.redefineModule(module, unnamed,
						module.getPackages().stream().collect(Collectors.toMap(Function.identity(), pkg -> unnamed)),
						module.getPackages().stream().collect(Collectors.toMap(Function.identity(), pkg -> unnamed)),
						Collections.emptySet(), Collections.emptyMap()));
	}

	private static AgentBuilder buildLog(AgentBuilder agentBuilder, Loggable loggable) {
		if (loggable != null) {
			StackSnapStreamWriting log = Logger.getInstance();

			if (loggable.isTransformationsOnly() == true) {
				agentBuilder = agentBuilder.with(log.withTransformationsOnly());
			}

			if (loggable.isErrorsOnly() == true) {
				agentBuilder = agentBuilder.with(log.withTransformationsOnly());
			}

			if (loggable.isTransformationsOnly() == false && loggable.isErrorsOnly() == false) {
				agentBuilder = agentBuilder.with(log);
			}
		}
		return agentBuilder;
	}

	private static AgentBuilder handleLogging(StacksnapConfiguration config, AgentBuilder agentBuilder) {
		if (config.getLogging() != null) {
			Loggable loggable = config.getLogging().getSystemError();
			if (loggable != null) {
				Logger.toSystemError();
				agentBuilder = buildLog(agentBuilder, loggable);
			}
			loggable = config.getLogging().getSystemOut();
			if (loggable != null) {
				Logger.toSystemOut();
				agentBuilder = buildLog(agentBuilder, loggable);
			}
		}
		return agentBuilder;
	}

}
