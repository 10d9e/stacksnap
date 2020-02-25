package org.stacksnap.config;

import java.util.Set;
import java.util.TreeSet;


public class BaseType {

	private Set<String> nameEndsWith = new TreeSet<String>();
	private Set<String> nameEndsWithIgnoreCase = new TreeSet<String>();
	private Set<String> nameStartsWith = new TreeSet<String>();
	private Set<String> named = new TreeSet<String>();
	private Set<String> namedIgnoreCase = new TreeSet<String>();
	private Set<String> nameContains = new TreeSet<String>();
	private Set<String> nameContainsIgnoreCase = new TreeSet<String>();
	private Set<String> nameMatches = new TreeSet<String>();
	private Set<String> isAnnotatedWith = new TreeSet<String>();
	private Set<String> isSubTypeOf = new TreeSet<String>();
	private Set<String> isSuperTypeOf = new TreeSet<String>();
	private Set<String> hasSuperClass = new TreeSet<String>();
	private Set<String> hasAnnotation = new TreeSet<String>();
	private Set<String> declaresField = new TreeSet<String>();
	private Set<String> declaresMethod = new TreeSet<String>();
	private boolean isPublic;
	private boolean isProtected;
	private boolean isPackagePrivate;
	private boolean isStatic;
	private boolean isFinal;

	public BaseType() {
	}

	public Set<String> getNameEndsWith() {
		return nameEndsWith;
	}

	public void setNameEndsWith(Set<String> nameEndsWith) {
		this.nameEndsWith = nameEndsWith;
	}

	public Set<String> getNameStartsWith() {
		return nameStartsWith;
	}

	public void setNameStartsWith(Set<String> nameStartsWith) {
		this.nameStartsWith = nameStartsWith;
	}

	public Set<String> getNamed() {
		return named;
	}

	public void setNamed(Set<String> named) {
		this.named = named;
	}

	public Set<String> getNamedIgnoreCase() {
		return namedIgnoreCase;
	}

	public void setNamedIgnoreCase(Set<String> namedIgnoreCase) {
		this.namedIgnoreCase = namedIgnoreCase;
	}

	public Set<String> getNameContains() {
		return nameContains;
	}

	public void setNameContains(Set<String> nameContains) {
		this.nameContains = nameContains;
	}

	public Set<String> getNameContainsIgnoreCase() {
		return nameContainsIgnoreCase;
	}

	public void setNameContainsIgnoreCase(Set<String> nameContainsIgnoreCase) {
		this.nameContainsIgnoreCase = nameContainsIgnoreCase;
	}

	public Set<String> getNameMatches() {
		return nameMatches;
	}

	public void setNameMatches(Set<String> nameMatches) {
		this.nameMatches = nameMatches;
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

	public Set<String> getNameEndsWithIgnoreCase() {
		return nameEndsWithIgnoreCase;
	}

	public void setNameEndsWithIgnoreCase(Set<String> nameEndsWithIgnoreCase) {
		this.nameEndsWithIgnoreCase = nameEndsWithIgnoreCase;
	}

	@Override
	public String toString() {
		return "Type [nameEndsWith=" + nameEndsWith + ", nameEndsWithIgnoreCase=" + nameEndsWithIgnoreCase
				+ ", nameStartsWith=" + nameStartsWith + ", named=" + named + ", namedIgnoreCase=" + namedIgnoreCase
				+ ", nameContains=" + nameContains + ", nameContainsIgnoreCase=" + nameContainsIgnoreCase
				+ ", nameMatches=" + nameMatches + ", isAnnotatedWith=" + isAnnotatedWith + ", isPublic=" + isPublic
				+ ", isProtected=" + isProtected + ", isPackagePrivate=" + isPackagePrivate + ", isStatic=" + isStatic
				+ ", isFinal=" + isFinal + ", isSubTypeOf=" + isSubTypeOf + ", isSuperTypeOf=" + isSuperTypeOf
				+ ", hasSuperClass=" + hasSuperClass + ", hasAnnotation=" + hasAnnotation + ", declaresField="
				+ declaresField + ", declaresMethod=" + declaresMethod + "]";
	}
	
	
	
}
