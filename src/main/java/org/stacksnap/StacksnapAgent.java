package org.stacksnap;

import java.lang.instrument.Instrumentation;

import org.stacksnap.config.StacksnapConfigurationBuilder;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

public class StacksnapAgent {
	
	public static void agentmain(String arguments, Instrumentation instrumentation) {
		premain(arguments, instrumentation);
	}
	
	public static void premain(String arguments, Instrumentation instrumentation) {
		System.out.println("[stacksnap] Welcome to Stacksnap.");
		
		StacksnapConfigurationBuilder config = new StacksnapConfigurationBuilder();
		
		new AgentBuilder.Default()
			.disableClassFormatChanges()
			.with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
			.with(AgentBuilder.Listener.StreamWriting.toSystemOut().withTransformationsOnly())
			//.with(AgentBuilder.Listener.StreamWriting.toSystemOut())
			//.type(ElementMatchers.nameEndsWith("Timed"))
			//.type(ElementMatchers.nameEndsWith("FooMappingExamplesController").or(ElementMatchers.nameEndsWith("Timed")))
			
			.ignore(ElementMatchers.nameMatches("org.stacksnap.*")
					.or(ElementMatchers.nameMatches("com.thoughtworks.xstream.*"))
					.or(ElementMatchers.nameMatches("net.bytebuddy.*"))
					.or(ElementMatchers.nameMatches("org.yaml.snakeyaml.*"))
					.or(ElementMatchers.nameMatches("java.*"))
					.or(ElementMatchers.nameMatches("javax.*"))
					.or(ElementMatchers.nameMatches("sun.*"))
					.or(ElementMatchers.nameMatches("jdk.*"))
					.or(ElementMatchers.nameMatches("com.sun.*"))
					.or(config.typeIgnores())
					)
			.type(config.typeMatches())
			.transform((builder, type, classLoader, module) -> 
				builder.visit(Advice.to(StacksnapAdviceHandler.class).on(ElementMatchers.isMethod())))
			.installOn(instrumentation);	
		
	}
	
	
}
