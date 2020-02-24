package org.stacksnap;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

public class StacksnapAgent {
	
	public static void agentmain(String arguments, Instrumentation instrumentation) {
		premain(arguments, instrumentation);
	}
	
	public static void premain(String arguments, Instrumentation instrumentation) {
		System.out.println("Welcome to Stacksnap!");
		
		new AgentBuilder.Default()
			.disableClassFormatChanges()
			.with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
			.with(AgentBuilder.Listener.StreamWriting.toSystemOut().withTransformationsOnly())
			//.type(ElementMatchers.nameEndsWith("Timed"))
			.type(ElementMatchers.nameEndsWith("FooMappingExamplesController"))
			.transform((builder, type, classLoader, module) -> 
				builder.visit( Advice.to(StacksnapAdviceHandler.class).on(ElementMatchers.isMethod()))
			)
			.installOn(instrumentation);	
		
		/*
		new AgentBuilder.Default()
				.disableClassFormatChanges()
				.with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
				.with(AgentBuilder.Listener.StreamWriting.toSystemOut().withTransformationsOnly())
				//.with(AgentBuilder.Listener.StreamWriting.toSystemOut())
				.type(ElementMatchers.nameEndsWith("Timed"))
				//.type(ElementMatchers.nameEndsWith("FooMappingExamplesController"))
				.transform((builder, type, classLoader, module) -> 
					builder.method(ElementMatchers.any())
					.intercept(Advice.to(StacksnapAdviceHandler.class)))
					//.intercept(MethodDelegation.to(StacksnapMethodInterceptor.class)))
				.installOn(instrumentation);
		*/		
		
	}
	
	
}
