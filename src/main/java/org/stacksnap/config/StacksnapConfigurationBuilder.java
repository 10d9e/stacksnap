package org.stacksnap.config;

import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatcher.Junction;
import net.bytebuddy.matcher.ElementMatchers;

public class StacksnapConfigurationBuilder {

	private Yaml yaml = new Yaml(new Constructor(StacksnapConfiguration.class));

	private StacksnapConfiguration configuration;

	

	public StacksnapConfigurationBuilder() {
		try {
			this.configuration = yaml.load(Files.readString(Paths.get("stacksnap.yml")));
			System.out.println("[stacksnap] Loaded configuration: stacksnap.yml");
			System.out.println(configuration);
		} catch (Exception e) {
			System.err.println("Error loading configuration: stacksnap.yml");
		}
	}
	
	public StacksnapConfiguration getConfiguration() {
		return configuration;
	}

	public StacksnapConfigurationBuilder(String configPath) throws Exception {
		super();
		this.configuration = yaml.load(Files.readString(Paths.get(configPath)));
		System.out.println(configuration);
	}
	
	private Junction<?> combiner(Junction<?> current, BaseType elem) {
		if(elem == null) {
			return current;
		}
		try {
			for (String s : elem.getNameEndsWith()) {
				current = current.or(ElementMatchers.nameEndsWith(s));
			}

			for (String s : elem.getDeclaresField()) {
				current = current
						.or(ElementMatchers.declaresField(ElementMatchers.named(s)));
			}

			for (String s : elem.getDeclaresMethod()) {
				current = current.or(ElementMatchers.declaresMethod(ElementMatchers.named(s)));
			}

			for (String s : elem.getHasAnnotation()) {
				current = current.or(ElementMatchers.hasAnnotation(
						ElementMatchers.annotationType((Class<? extends Annotation>) Class.forName(s))));
			}

			for (String s : elem.getHasSuperClass()) {
				current = current.or(ElementMatchers.hasSuperClass(ElementMatchers.named(s)));
			}
			
			for (String s : elem.getHasSuperType()) {
				current = current.or(ElementMatchers.hasSuperType(ElementMatchers.named(s)));
			}

			for (String s : elem.getIsAnnotatedWith()) {
				current = current
						.or(ElementMatchers.isAnnotatedWith((Class<? extends Annotation>) Class.forName(s)));
			}

			if (elem.getIsFinal() == true) {
				current = current.or(ElementMatchers.isFinal());
			}

			if (elem.getIsPackagePrivate() == true) {
				current = current.or(ElementMatchers.isPackagePrivate());
			}

			if (elem.getIsProtected() == true) {
				current = current.or(ElementMatchers.isProtected());
			}

			if (elem.getIsPublic() == true) {
				current = current.or(ElementMatchers.isPublic());
			}

			if (elem.getIsStatic() == true) {
				current = current.or(ElementMatchers.isStatic());
			}

			for (String s : elem.getIsSubTypeOf()) {
				current = current.or(ElementMatchers.isSubTypeOf(Class.forName(s)));
			}

			for (String s : elem.getIsSuperTypeOf()) {
				current = current.or(ElementMatchers.isSuperTypeOf(Class.forName(s)));
			}

			for (String s : elem.getNameContains()) {
				current = current.or(ElementMatchers.nameContains(s));
			}

			for (String s : elem.getNameContainsIgnoreCase()) {
				current = current.or(ElementMatchers.nameContainsIgnoreCase(s));
			}

			for (String s : elem.getNamed()) {
				current = current.or(ElementMatchers.named(s));
			}

			for (String s : elem.getNamedIgnoreCase()) {
				current = current.or(ElementMatchers.namedIgnoreCase(s));
			}

			for (String s : elem.getNameEndsWith()) {
				current = current.or(ElementMatchers.nameEndsWith(s));
			}

			for (String s : elem.getNameEndsWithIgnoreCase()) {
				current = current.or(ElementMatchers.nameEndsWithIgnoreCase(s));
			}

			for (String s : elem.getNameMatches()) {
				current = current.or(ElementMatchers.nameMatches(s));
			}
		} catch (Exception e) {
			System.out.println("[stacksnap] Configuration Error: " + e.getMessage());
			e.printStackTrace();
		}
		return current;
	}

	@SuppressWarnings("unchecked")
	public ElementMatcher<? super TypeDescription> typeMatches() {
		Junction<?> current = ElementMatchers.none();
		if (configuration != null) {			
			current = combiner(current, configuration.getType().getMatch());
		}
		return (ElementMatcher<? super TypeDescription>) current;
	}
	
	public ElementMatcher<? super TypeDescription> typeIgnores() {
		Junction<?> current = ElementMatchers.none();
		if (configuration != null) {
			current = combiner(current, configuration.getType().getIgnore());
		}
		return (ElementMatcher<? super TypeDescription>) current;
	}

}
