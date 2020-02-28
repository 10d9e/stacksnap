package org.stacksnap.config;

import static net.bytebuddy.matcher.ElementMatchers.annotationType;
import static net.bytebuddy.matcher.ElementMatchers.canThrow;
import static net.bytebuddy.matcher.ElementMatchers.declaresException;
import static net.bytebuddy.matcher.ElementMatchers.declaresField;
import static net.bytebuddy.matcher.ElementMatchers.declaresMethod;
import static net.bytebuddy.matcher.ElementMatchers.hasAnnotation;
import static net.bytebuddy.matcher.ElementMatchers.hasSuperClass;
import static net.bytebuddy.matcher.ElementMatchers.hasSuperType;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.isDefaultMethod;
import static net.bytebuddy.matcher.ElementMatchers.isFinal;
import static net.bytebuddy.matcher.ElementMatchers.isGetter;
import static net.bytebuddy.matcher.ElementMatchers.isOverriddenFrom;
import static net.bytebuddy.matcher.ElementMatchers.isPackagePrivate;
import static net.bytebuddy.matcher.ElementMatchers.isPrivate;
import static net.bytebuddy.matcher.ElementMatchers.isProtected;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.isSetter;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.isStrict;
import static net.bytebuddy.matcher.ElementMatchers.isSubTypeOf;
import static net.bytebuddy.matcher.ElementMatchers.isSuperTypeOf;
import static net.bytebuddy.matcher.ElementMatchers.isSynchronized;
import static net.bytebuddy.matcher.ElementMatchers.isVirtual;
import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.nameContainsIgnoreCase;
import static net.bytebuddy.matcher.ElementMatchers.nameEndsWith;
import static net.bytebuddy.matcher.ElementMatchers.nameEndsWithIgnoreCase;
import static net.bytebuddy.matcher.ElementMatchers.nameMatches;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.namedIgnoreCase;
import static net.bytebuddy.matcher.ElementMatchers.none;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.stacksnap.agent.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.yevdo.jwildcard.JWildcard;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatcher.Junction;

public class StacksnapConfigurationBuilder {

	private static Yaml yaml = new Yaml(new Constructor(StacksnapConfiguration.class));

	private static StacksnapConfiguration configuration;

	public static StacksnapConfiguration build() {
		try {
			Path p = Paths.get("stacksnap.yml");
			configuration = yaml.load(Files.readString(p));
			Logger.log("Loaded configuration: 'stacksnap.yml'");
		} catch (Exception e) {
			Logger.error("Error loading configuration: 'stacksnap.yml'");
			printPathError();
		}
		return configuration;
	}

	private static void printPathError() {

		Logger.error("Create a 'stacksnap.yml' configuration file in the root");
		Logger.error("of your classpath, for example:");


		InputStream inputStream = StacksnapConfigurationBuilder.class
				.getResourceAsStream("/org/stacksnap/config/configuration-template.yml");
		String text = null;
		try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
			text = scanner.useDelimiter("\\A").next();
		}
		System.err.println();
		System.err.println("----------------------< stacksnap.yml file >-----------------------");
		System.err.println();
		System.err.println(text);
		System.err.println();
		System.err.println("----------------------< stacksnap.yml file >------------------------");
		System.err.println();

	}

	public static StacksnapConfiguration getConfiguration() {
		if (configuration == null) {
			build();
		}
		return configuration;
	}

	@SuppressWarnings("unchecked")
	public static ElementMatcher<? super TypeDescription> typeIncludes() {
		if (configuration.getTypes() == null || configuration.getTypes().getInclude() == null) {
			return none();
		}
		Junction<?> current = none();
		boolean valid = configuration.getTypes().getInclude().checkValid();
		if (valid == true) {
			current = typeCombiner(current, configuration.getTypes().getInclude());
		}
		return (ElementMatcher<? super TypeDescription>) current;
	}

	@SuppressWarnings("unchecked")
	public static ElementMatcher<? super TypeDescription> typeIgnores() {
		if (configuration.getTypes() == null || configuration.getTypes().getIgnore() == null) {
			return none();
		}
		Junction<?> current = none();
		boolean valid = configuration.getTypes().getIgnore().checkValid();
		if (valid == true) {
			current = typeCombiner(current, configuration.getTypes().getIgnore());
		}
		return (ElementMatcher<? super TypeDescription>) current;
	}

	@SuppressWarnings("unchecked")
	public static ElementMatcher<? super MethodDescription> errorMethodIncludes() {
		if (configuration.getError() == null || configuration.getError().getMethods() == null
				|| configuration.getError().getMethods().getInclude() == null) {
			return none();
		}
		Junction<?> current = none();
		current = methodCombiner(current, configuration.getError().getMethods().getInclude());
		return (ElementMatcher<? super MethodDescription>) current;
	}

	@SuppressWarnings("unchecked")
	public static ElementMatcher<? super MethodDescription> recordMethodIncludes() {
		if (configuration.getRecord() == null || configuration.getRecord().getMethods() == null
				|| configuration.getRecord().getMethods().getInclude() == null) {
			return none();
		}
		Junction<?> current = none();
		current = methodCombiner(current, configuration.getRecord().getMethods().getInclude());
		return (ElementMatcher<? super MethodDescription>) current;
	}

	@SuppressWarnings("unchecked")
	public static ElementMatcher<? super MethodDescription> errorMethodIgnores() {
		if (configuration.getError() == null || configuration.getError().getMethods() == null
				|| configuration.getError().getMethods().getIgnore() == null) {
			return none();
		}
		Junction<?> current = none();
		current = methodCombiner(current, configuration.getError().getMethods().getIgnore());
		return (ElementMatcher<? super MethodDescription>) current;
	}

	@SuppressWarnings("unchecked")
	public static ElementMatcher<? super MethodDescription> recordMethodIgnores() {
		if (configuration.getRecord() == null || configuration.getRecord().getMethods() == null
				|| configuration.getRecord().getMethods().getIgnore() == null) {
			return none();
		}
		Junction<?> current = none();
		current = methodCombiner(current, configuration.getRecord().getMethods().getIgnore());
		return (ElementMatcher<? super MethodDescription>) current;
	}

	private static Junction<?> namesCombiner(Junction<?> current, CommonElements elem) {
		if (elem == null) {
			return current;
		}

		for (String s : elem.getNameEndsWith()) {
			current = current.or(nameEndsWith(s));
		}

		for (String s : elem.getNameContains()) {
			current = current.or(nameContains(s));
		}

		for (String s : elem.getNameContainsIgnoreCase()) {
			current = current.or(nameContainsIgnoreCase(s));
		}

		for (String s : elem.getNamed()) {
			current = current.or(named(s));
		}

		for (String s : elem.getNamedIgnoreCase()) {
			current = current.or(namedIgnoreCase(s));
		}

		for (String s : elem.getNameEndsWith()) {
			current = current.or(nameEndsWith(s));
		}

		for (String s : elem.getNameEndsWithIgnoreCase()) {
			current = current.or(nameEndsWithIgnoreCase(s));
		}

		for (String s : elem.getNameRegex()) {
			current = current.or(nameMatches(s));
		}

		for (String s : elem.getNameMatches()) {
			current = current.or(nameMatches(JWildcard.wildcardToRegex(s)));
		}

		return current;

	}

	@SuppressWarnings("unchecked")
	private static Junction<?> typeCombiner(Junction<?> current, BaseType elem) {
		if (elem == null) {
			return current;
		}
		try {

			current = namesCombiner(current, elem);

			for (String s : elem.getDeclaresField()) {
				current = current.or(declaresField(named(s)));
			}

			for (String s : elem.getDeclaresMethod()) {
				current = current.or(declaresMethod(named(s)));
			}

			for (String s : elem.getHasAnnotation()) {
				current = current.or(hasAnnotation(annotationType((Class<? extends Annotation>) Class.forName(s))));
			}

			for (String s : elem.getHasSuperClass()) {
				current = current.or(hasSuperClass(named(s)));
			}

			for (String s : elem.getHasSuperType()) {
				current = current.or(hasSuperType(named(s)));
			}

			for (String s : elem.getIsAnnotatedWith()) {
				current = current.or(isAnnotatedWith((Class<? extends Annotation>) Class.forName(s)));
			}

			if (elem.isFinal() == true) {
				current = current.or(isFinal());
			}

			if (elem.isPackagePrivate() == true) {
				current = current.or(isPackagePrivate());
			}

			if (elem.isProtected() == true) {
				current = current.or(isProtected());
			}

			if (elem.isPublic() == true) {
				current = current.or(isPublic());
			}

			if (elem.isStatic() == true) {
				current = current.or(isStatic());
			}

			for (String s : elem.getIsSubTypeOf()) {
				current = current.or(isSubTypeOf(Class.forName(s)));
			}

			for (String s : elem.getIsSuperTypeOf()) {
				current = current.or(isSuperTypeOf(Class.forName(s)));
			}

		} catch (Exception e) {
			Logger.log("Configuration Error" + e.getMessage());
			e.printStackTrace();
		}
		return current;
	}

	@SuppressWarnings("unchecked")
	private static Junction<?> methodCombiner(Junction<?> current, BaseMethod elem) {
		if (elem == null) {
			return current;
		}
		try {

			current = namesCombiner(current, elem);

			for (String s : elem.getIsAnnotatedWith()) {
				current = current.or(isAnnotatedWith((Class<? extends Annotation>) Class.forName(s)));
			}

			if (elem.isFinal() == true) {
				current = current.or(isFinal());
			}

			if (elem.isPackagePrivate() == true) {
				current = current.or(isPackagePrivate());
			}

			if (elem.isProtected() == true) {
				current = current.or(isProtected());
			}

			if (elem.isPublic() == true) {
				current = current.or(isPublic());
			}

			if (elem.isPrivate() == true) {
				current = current.or(isPrivate());
			}

			if (elem.isStatic() == true) {
				current = current.or(isStatic());
			}

			if (elem.getIsSynchronized() == true) {
				current = current.or(isSynchronized());
			}

			if (elem.getIsStrict() == true) {
				current = current.or(isStrict());
			}

			for (String s : elem.getCanThrow()) {
				current = current.or(canThrow((Class<? extends Throwable>) Class.forName(s)));
			}

			for (String s : elem.getDeclaresException()) {
				current = current.or(declaresException((Class<? extends Throwable>) Class.forName(s)));
			}

			for (String s : elem.getIsOverriddenFrom()) {
				current = current.or(isOverriddenFrom(Class.forName(s)));
			}

			if (elem.isVirtual() == true) {
				current = current.or(isVirtual());
			}

			if (elem.isDefaultMethod() == true) {
				current = current.or(isDefaultMethod());
			}

			if (elem.isSetter() == true) {
				current = current.or(isSetter());
			}

			if (elem.isGetter() == true) {
				current = current.or(isGetter());
			}

		} catch (Exception e) {
			Logger.log("Configuration Error" + e.getMessage());
			e.printStackTrace();
		}
		return current;
	}

}
