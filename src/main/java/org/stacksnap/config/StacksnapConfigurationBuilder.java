package org.stacksnap.config;

import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.stacksnap.agent.Logger;
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
	}
	
	

	@SuppressWarnings("unchecked")
	public ElementMatcher<? super TypeDescription> typeMatches() {
		if(configuration.getTypes() == null
				|| configuration.getTypes().getMatch() == null) {
			return none();
		}
		Junction<?> current = none();
		boolean valid = configuration.getTypes().getMatch().isValid();
		if (configuration != null && valid == true ) {		
			current = typeCombiner(current, configuration.getTypes().getMatch());
		} else {
			current = none();
		}
		return (ElementMatcher<? super TypeDescription>) current;
	}
	
	public ElementMatcher<? super TypeDescription> typeIgnores() {
		if(configuration.getTypes() == null
				|| configuration.getTypes().getIgnore() == null) {
			return none();
		}
		Junction<?> current = any();
		boolean valid = configuration.getTypes().getIgnore().isValid();
		if (configuration != null && valid == true ) {
			current = typeCombiner(current, configuration.getTypes().getIgnore());
		}else {
			current = none();
		}
		return (ElementMatcher<? super TypeDescription>) current;
	}
	
	public ElementMatcher<? super MethodDescription> methodMatches() {
		Junction<?> current = none();
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
	
	private Junction<?> typeCombiner(Junction<?> current, BaseType elem) {
		if(elem == null) {
			return current;
		}
		try {
			
			current = namesCombiner(current, elem);

			for (String s : elem.getDeclaresField()) {
				current = current
						.or(declaresField(named(s)));
			}

			for (String s : elem.getDeclaresMethod()) {
				current = current.or(declaresMethod(named(s)));
			}

			for (String s : elem.getHasAnnotation()) {
				current = current.or(hasAnnotation(
						annotationType((Class<? extends Annotation>) Class.forName(s))));
			}

			for (String s : elem.getHasSuperClass()) {
				current = current.or(hasSuperClass(named(s)));
			}
			
			for (String s : elem.getHasSuperType()) {
				current = current.or(hasSuperType(named(s)));
			}

			for (String s : elem.getIsAnnotatedWith()) {
				current = current
						.or(isAnnotatedWith((Class<? extends Annotation>) Class.forName(s)));
			}

			if (elem.getIsFinal() == true) {
				current = current.or(isFinal());
			}

			if (elem.getIsPackagePrivate() == true) {
				current = current.or(isPackagePrivate());
			}

			if (elem.getIsProtected() == true) {
				current = current.or(isProtected());
			}

			if (elem.getIsPublic() == true) {
				current = current.or(isPublic());
			}
			
			if (elem.getIsPrivate() == true) {
				current = current.or(isPrivate());
			}

			if (elem.getIsStatic() == true) {
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
	
	private Junction<?> methodCombiner(Junction<?> current, BaseMethod elem) {
		if(elem == null) {
			return current;
		}
		try {
			
			current = namesCombiner(current, elem);

			for (String s : elem.getIsAnnotatedWith()) {
				current = current
						.or(isAnnotatedWith((Class<? extends Annotation>) Class.forName(s)));
			}

			if (elem.getIsFinal() == true) {
				current = current.or(isFinal());
			}

			if (elem.getIsPackagePrivate() == true) {
				current = current.or(isPackagePrivate());
			}

			if (elem.getIsProtected() == true) {
				current = current.or(isProtected());
			}

			if (elem.getIsPublic() == true) {
				current = current.or(isPublic());
			}
			
			if (elem.getIsPrivate() == true) {
				current = current.or(isPrivate());
			}

			if (elem.getIsStatic() == true) {
				current = current.or(isStatic());
			}
			
			if (elem.getIsSynchronized() == true) {
				current = current.or(isSynchronized());
			}
			
			if (elem.getIsStrict() == true) {
				current = current.or(isStrict());
			}

			for (String s : elem.getCanThrow()) {
				current = current.or(canThrow((Class<? extends Exception>) Class.forName(s)));
			}
			
			for (String s : elem.getDeclaresException()) {
				current = current.or(declaresException((Class<? extends Exception>) Class.forName(s)));
			}
			
			for (String s : elem.getIsOverriddenFrom()) {
				current = current.or(isOverriddenFrom(Class.forName(s)));
			}
			
			if (elem.getIsVirtual() == true) {
				current = current.or(isVirtual());
			}
			
			if (elem.getIsDefaultMethod() == true) {
				current = current.or(isDefaultMethod());
			}
			
			if (elem.getIsSetter() == true) {
				current = current.or(isSetter());
			}
			
			if (elem.getIsGetter() == true) {
				current = current.or(isGetter());
			}
			
		} catch (Exception e) {
			Logger.log("Configuration Error" + e.getMessage());
			e.printStackTrace();
		}
		return current;
	}

}
