package org.stacksnap.config;

import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.stacksnap.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.yevdo.jwildcard.JWildcard;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatcher.Junction;
import static net.bytebuddy.matcher.ElementMatchers.*;

public class StacksnapConfigurationBuilder {

	private Yaml yaml = new Yaml(new Constructor(StacksnapConfiguration.class));

	private StacksnapConfiguration configuration;

	public StacksnapConfigurationBuilder() {
		try {
			this.configuration = yaml.load(Files.readString(Paths.get("stacksnap.yml")));
			Logger.log("Loaded configuration: stacksnap.yml");
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
	
	

	@SuppressWarnings("unchecked")
	public ElementMatcher<? super TypeDescription> typeMatches() {
		Junction<?> current = any();
		if (configuration != null) {			
			current = typeCombiner(current, configuration.getTypes().getMatch());
		}
		return (ElementMatcher<? super TypeDescription>) current;
	}
	
	public ElementMatcher<? super TypeDescription> typeIgnores() {
		Junction<?> current = any();
		if (configuration != null) {
			current = typeCombiner(current, configuration.getTypes().getIgnore());
		}
		return (ElementMatcher<? super TypeDescription>) current;
	}
	
	public ElementMatcher<? super MethodDescription> methodMatches() {
		Junction<?> current = any();
		if (configuration != null) {
			current = methodCombiner(current, configuration.getMethods().getMatch());
		}
		return (ElementMatcher<? super MethodDescription>) current;
	}
	
	public ElementMatcher<? super MethodDescription> methodIgnores() {
		Junction<?> current = any();
		if (configuration != null) {
			current = methodCombiner(current, configuration.getMethods().getIgnore());
		}
		return (ElementMatcher<? super MethodDescription>) current;
	}
	
	private Junction<?> namesCombiner(Junction<?> current, NamingElements elem) {
		if(elem == null) {
			return current;
		}
		
		for (String s : elem.getNameEndsWith()) {
			current = current.and(nameEndsWith(s));
		}
		
		for (String s : elem.getNameContains()) {
			current = current.and(nameContains(s));
		}

		for (String s : elem.getNameContainsIgnoreCase()) {
			current = current.and(nameContainsIgnoreCase(s));
		}

		for (String s : elem.getNamed()) {
			current = current.and(named(s));
		}

		for (String s : elem.getNamedIgnoreCase()) {
			current = current.and(namedIgnoreCase(s));
		}

		for (String s : elem.getNameEndsWith()) {
			current = current.and(nameEndsWith(s));
		}

		for (String s : elem.getNameEndsWithIgnoreCase()) {
			current = current.and(nameEndsWithIgnoreCase(s));
		}

		for (String s : elem.getNameRegex()) {
			current = current.and(nameMatches(s));
		}
		
		for (String s : elem.getNameMatches()) {
			current = current.and(nameMatches(JWildcard.wildcardToRegex(s)));
		}
		
		return current;
		
	}
	
	private Junction<?> typeCombiner(Junction<?> current, BaseType elem) {
		if(elem == null) {
			return current;
		}
		try {
			
			current = namesCombiner(current, elem);

			for (String s : elem.getDeclaresField()) {
				current = current
						.and(declaresField(named(s)));
			}

			for (String s : elem.getDeclaresMethod()) {
				current = current.and(declaresMethod(named(s)));
			}

			for (String s : elem.getHasAnnotation()) {
				current = current.and(hasAnnotation(
						annotationType((Class<? extends Annotation>) Class.forName(s))));
			}

			for (String s : elem.getHasSuperClass()) {
				current = current.and(hasSuperClass(named(s)));
			}
			
			for (String s : elem.getHasSuperType()) {
				current = current.and(hasSuperType(named(s)));
			}

			for (String s : elem.getIsAnnotatedWith()) {
				current = current
						.and(isAnnotatedWith((Class<? extends Annotation>) Class.forName(s)));
			}

			if (elem.getIsFinal() == true) {
				current = current.and(isFinal());
			}

			if (elem.getIsPackagePrivate() == true) {
				current = current.and(isPackagePrivate());
			}

			if (elem.getIsProtected() == true) {
				current = current.and(isProtected());
			}

			if (elem.getIsPublic() == true) {
				current = current.and(isPublic());
			}
			
			if (elem.getIsPrivate() == true) {
				current = current.and(isPrivate());
			}

			if (elem.getIsStatic() == true) {
				current = current.and(isStatic());
			}

			for (String s : elem.getIsSubTypeOf()) {
				current = current.and(isSubTypeOf(Class.forName(s)));
			}

			for (String s : elem.getIsSuperTypeOf()) {
				current = current.and(isSuperTypeOf(Class.forName(s)));
			}

			
		} catch (Exception e) {
			Logger.log("Configuration Error" + e.getMessage());
			e.printStackTrace();
		}
		return current;
	}
	
	private Junction<?> methodCombiner(Junction<?> current, BaseMethod elem) {
		if(elem == null) {
			return current;
		}
		try {
			
			current = namesCombiner(current, elem);

			for (String s : elem.getIsAnnotatedWith()) {
				current = current
						.and(isAnnotatedWith((Class<? extends Annotation>) Class.forName(s)));
			}

			if (elem.getIsFinal() == true) {
				current = current.and(isFinal());
			}

			if (elem.getIsPackagePrivate() == true) {
				current = current.and(isPackagePrivate());
			}

			if (elem.getIsProtected() == true) {
				current = current.and(isProtected());
			}

			if (elem.getIsPublic() == true) {
				current = current.and(isPublic());
			}
			
			if (elem.getIsPrivate() == true) {
				current = current.and(isPrivate());
			}

			if (elem.getIsStatic() == true) {
				current = current.and(isStatic());
			}
			
			if (elem.getIsSynchronized() == true) {
				current = current.and(isSynchronized());
			}
			
			if (elem.getIsStrict() == true) {
				current = current.and(isStrict());
			}

			for (String s : elem.getCanThrow()) {
				current = current.and(canThrow((Class<? extends Exception>) Class.forName(s)));
			}
			
			for (String s : elem.getDeclaresException()) {
				current = current.and(declaresException((Class<? extends Exception>) Class.forName(s)));
			}
			
			for (String s : elem.getIsOverriddenFrom()) {
				current = current.and(isOverriddenFrom(Class.forName(s)));
			}
			
			if (elem.getIsVirtual() == true) {
				current = current.and(isVirtual());
			}
			
			if (elem.getIsDefaultMethod() == true) {
				current = current.and(isDefaultMethod());
			}
			
			if (elem.getIsSetter() == true) {
				current = current.and(isSetter());
			}
			
			if (elem.getIsGetter() == true) {
				current = current.and(isGetter());
			}
			
		} catch (Exception e) {
			Logger.log("Configuration Error" + e.getMessage());
			e.printStackTrace();
		}
		return current;
	}

}
