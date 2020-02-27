package org.stacksnap.agent;

import java.lang.instrument.Instrumentation;

import org.stacksnap.config.Loggable;
import org.stacksnap.config.StacksnapConfiguration;
import org.stacksnap.config.StacksnapConfigurationBuilder;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import static net.bytebuddy.matcher.ElementMatchers.*;

public class StacksnapAgent {

	public static void agentmain(String arguments, Instrumentation instrumentation) {
		premain(arguments, instrumentation);
	}

	public static void premain(String arguments, Instrumentation instrumentation) {
		Logger.log("[stacksnap] Welcome to Stacksnap.");

		StacksnapConfiguration config = StacksnapConfigurationBuilder.build();

		AgentBuilder agentBuilder = new AgentBuilder.Default().disableClassFormatChanges()
				.with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION);

		agentBuilder = handleLogging(config, agentBuilder);

		agentBuilder
				.ignore(nameStartsWith("net.bytebuddy")
						.or(nameStartsWith("org.stacksnap"))
						.or(nameStartsWith("com.yevdo.jwildcard"))
						.or(nameStartsWith("com.thoughtworks.xstream"))
						.or(nameStartsWith("org.xmlpull"))
						.or(nameStartsWith("jdk"))
						.or(nameStartsWith("java"))
						.or(nameStartsWith("sun"))
						.or(nameStartsWith("com.sun"))
						.or(nameStartsWith("org.yaml"))
						.or(StacksnapConfigurationBuilder.typeIgnores())
				)
				.type(StacksnapConfigurationBuilder.typeIncludes())
				.transform((builder, type, classLoader, module) -> builder
						.visit(Advice.to(StacksnapExceptionHandler.class).on(isMethod().and(StacksnapConfigurationBuilder.errorMethodIncludes())))
						
						.visit(Advice.to(StacksnapRecorder.class).on(isMethod().and(StacksnapConfigurationBuilder.recordMethodIncludes())))
						
				)
						
				.installOn(instrumentation);

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
