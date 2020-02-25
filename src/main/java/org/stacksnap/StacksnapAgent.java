package org.stacksnap;

import java.lang.instrument.Instrumentation;

import org.stacksnap.config.Loggable;
import org.stacksnap.config.StacksnapConfigurationBuilder;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import static net.bytebuddy.matcher.ElementMatchers.*;

public class StacksnapAgent {

	public static void agentmain(String arguments, Instrumentation instrumentation) {
		premain(arguments, instrumentation);
	}

	public static void premain(String arguments, Instrumentation instrumentation) {
		System.out.println("[stacksnap] Welcome to Stacksnap.");

		StacksnapConfigurationBuilder config = new StacksnapConfigurationBuilder();

		AgentBuilder agentBuilder = new AgentBuilder.Default().disableClassFormatChanges()
				.with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION);

		agentBuilder = handleLogging(config, agentBuilder);

		agentBuilder
				.ignore(anyOf(nameMatches("org.stacksnap.*"), nameMatches("com.thoughtworks.xstream.*"),
						nameMatches("net.bytebuddy.*"), nameMatches("org.yaml.snakeyaml.*"), nameMatches("java.*"),
						nameMatches("javax.*"), nameMatches("sun.*"), nameMatches("jdk.*"), nameMatches("com.sun.*"),
						config.typeIgnores()))
				.type(config.typeMatches())
				.transform((builder, type, classLoader, module) -> builder
						.visit(Advice.to(StacksnapAdviceHandler.class).on(isMethod().and(config.methodMatches()))))
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

	private static AgentBuilder handleLogging(StacksnapConfigurationBuilder config, AgentBuilder agentBuilder) {
		if (config.getConfiguration().getLogging() != null) {
			Loggable loggable = config.getConfiguration().getLogging().getSystemError();
			if (loggable != null) {
				Logger.toSystemError();
				agentBuilder = buildLog(agentBuilder, loggable);
			}
			loggable = config.getConfiguration().getLogging().getSystemOut();
			if (loggable != null) {
				Logger.toSystemOut();
				agentBuilder = buildLog(agentBuilder, loggable);
			}
		}
		return agentBuilder;
	}

}
