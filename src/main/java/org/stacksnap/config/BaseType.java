package org.stacksnap.config;

import java.util.Set;
import java.util.TreeSet;

public class BaseType extends CommonElements {

	private Set<String> isSubTypeOf = new TreeSet<String>();
	private Set<String> isSuperTypeOf = new TreeSet<String>();
	private Set<String> hasSuperClass = new TreeSet<String>();
	private Set<String> hasSuperType = new TreeSet<String>();
	private Set<String> hasAnnotation = new TreeSet<String>();
	private Set<String> declaresField = new TreeSet<String>();
	private Set<String> declaresMethod = new TreeSet<String>();

	public boolean checkValid() {
		return super.checkValid() || !isSubTypeOf.isEmpty() || !isSuperTypeOf.isEmpty() || !hasSuperClass.isEmpty()
				|| !hasSuperType.isEmpty() || !hasAnnotation.isEmpty() || !declaresField.isEmpty()
				|| !declaresMethod.isEmpty();
	}

	public BaseType() {
	}

	public Set<String> getIsSubTypeOf() {
		return isSubTypeOf;
	}

	public void setIsSubTypeOf(Set<String> isSubTypeOf) {
		this.isSubTypeOf = isSubTypeOf;
	}

	public Set<String> getIsSuperTypeOf() {
		return isSuperTypeOf;
	}

	public void setIsSuperTypeOf(Set<String> isSuperTypeOf) {
		this.isSuperTypeOf = isSuperTypeOf;
	}

	public Set<String> getHasSuperClass() {
		return hasSuperClass;
	}

	public void setHasSuperClass(Set<String> hasSuperClass) {
		this.hasSuperClass = hasSuperClass;
	}

	public Set<String> getHasAnnotation() {
		return hasAnnotation;
	}

	public void setHasAnnotation(Set<String> hasAnnotation) {
		this.hasAnnotation = hasAnnotation;
	}

	public Set<String> getDeclaresField() {
		return declaresField;
	}

	public void setDeclaresField(Set<String> declaresField) {
		this.declaresField = declaresField;
	}

	public Set<String> getDeclaresMethod() {
		return declaresMethod;
	}

	public void setDeclaresMethod(Set<String> declaresMethod) {
		this.declaresMethod = declaresMethod;
	}

	public Set<String> getHasSuperType() {
		return hasSuperType;
	}

	public void setHasSuperType(Set<String> hasSuperType) {
		this.hasSuperType = hasSuperType;
	}

}
