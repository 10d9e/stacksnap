package org.stacksnap.config;

import java.util.Set;
import java.util.TreeSet;


public class BaseType extends NamingElements {

	private Set<String> isAnnotatedWith = new TreeSet<String>();
	private Set<String> isSubTypeOf = new TreeSet<String>();
	private Set<String> isSuperTypeOf = new TreeSet<String>();
	private Set<String> hasSuperClass = new TreeSet<String>();
	private Set<String> hasSuperType = new TreeSet<String>();
	private Set<String> hasAnnotation = new TreeSet<String>();
	private Set<String> declaresField = new TreeSet<String>();
	private Set<String> declaresMethod = new TreeSet<String>();
	
	private boolean isPrivate;
	private boolean isPublic;
	private boolean isProtected;
	private boolean isPackagePrivate;
	private boolean isStatic;
	private boolean isFinal;

	public BaseType() {
	}

	public Set<String> getIsAnnotatedWith() {
		return isAnnotatedWith;
	}

	public void setIsAnnotatedWith(Set<String> isAnnotatedWith) {
		this.isAnnotatedWith = isAnnotatedWith;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Boolean getIsProtected() {
		return isProtected;
	}

	public void setIsProtected(Boolean isProtected) {
		this.isProtected = isProtected;
	}

	public Boolean getIsPackagePrivate() {
		return isPackagePrivate;
	}

	public void setIsPackagePrivate(Boolean isPackagePrivate) {
		this.isPackagePrivate = isPackagePrivate;
	}

	public Boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
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
	
	public boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	@Override
	public String toString() {
		return "BaseType [isAnnotatedWith=" + isAnnotatedWith + ", isSubTypeOf=" + isSubTypeOf + ", isSuperTypeOf="
				+ isSuperTypeOf + ", hasSuperClass=" + hasSuperClass + ", hasSuperType=" + hasSuperType
				+ ", hasAnnotation=" + hasAnnotation + ", declaresField=" + declaresField + ", declaresMethod="
				+ declaresMethod + ", isPrivate=" + isPrivate + ", isPublic=" + isPublic + ", isProtected="
				+ isProtected + ", isPackagePrivate=" + isPackagePrivate + ", isStatic=" + isStatic + ", isFinal="
				+ isFinal + ", getNameEndsWith()=" + getNameEndsWith() + ", getNameEndsWithIgnoreCase()="
				+ getNameEndsWithIgnoreCase() + ", getNameStartsWith()=" + getNameStartsWith() + ", getNamed()="
				+ getNamed() + ", getNamedIgnoreCase()=" + getNamedIgnoreCase() + ", getNameContains()="
				+ getNameContains() + ", getNameContainsIgnoreCase()=" + getNameContainsIgnoreCase()
				+ ", getNameMatches()=" + getNameRegex() + "]";
	}

	
}
