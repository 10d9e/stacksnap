package org.stacksnap.config;

import java.util.Set;
import java.util.TreeSet;

/*
named
namedIgnoreCase
nameStartsWith
nameStartsWithIgnoreCase
nameEndsWith
nameEndsWithIgnoreCase
nameContains
nameContainsIgnoreCase
nameMatches

isAnnotatedWith
isPublic
isProtected
isPackagePrivate
isPrivate
isStatic
isFinal
canThrow
declaresException
isOverriddenFrom
isVirtual
isDefaultMethod
isSetter
isGetter
*/

public class BaseMethod extends NamingElements {

	private boolean isPublic;
	private boolean isProtected;
	private boolean isPrivate;
	private boolean isPackagePrivate;
	private boolean isStatic;
	private boolean isFinal;
	private boolean isSynchronized;
	private boolean isStrict;

	private Set<String> isAnnotatedWith = new TreeSet<String>();
	private Set<String> canThrow = new TreeSet<String>();
	private Set<String> declaresException = new TreeSet<String>();
	private Set<String> isOverriddenFrom = new TreeSet<String>();
	private boolean isVirtual;
	private boolean isDefaultMethod;
	private boolean isSetter;
	private boolean isGetter;


	public Set<String> getIsAnnotatedWith() {
		return isAnnotatedWith;
	}

	public void setIsAnnotatedWith(Set<String> isAnnotatedWith) {
		this.isAnnotatedWith = isAnnotatedWith;
	}

	public boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean getIsProtected() {
		return isProtected;
	}

	public void setIsProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public boolean getIsPackagePrivate() {
		return isPackagePrivate;
	}

	public void setIsPackagePrivate(boolean isPackagePrivate) {
		this.isPackagePrivate = isPackagePrivate;
	}

	public boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public boolean getIsSynchronized() {
		return isSynchronized;
	}

	public void setIsSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}

	public boolean getIsStrict() {
		return isStrict;
	}

	public void setIsStrict(boolean isStrict) {
		this.isStrict = isStrict;
	}

	public Set<String> getCanThrow() {
		return canThrow;
	}

	public void setCanThrow(Set<String> canThrow) {
		this.canThrow = canThrow;
	}

	public Set<String> getDeclaresException() {
		return declaresException;
	}

	public void setDeclaresException(Set<String> declaresException) {
		this.declaresException = declaresException;
	}

	public Set<String> getIsOverriddenFrom() {
		return isOverriddenFrom;
	}

	public void setIsOverriddenFrom(Set<String> isOverriddenFrom) {
		this.isOverriddenFrom = isOverriddenFrom;
	}

	public boolean getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public boolean getIsDefaultMethod() {
		return isDefaultMethod;
	}

	public void setIsDefaultMethod(boolean isDefaultMethod) {
		this.isDefaultMethod = isDefaultMethod;
	}

	public boolean getIsSetter() {
		return isSetter;
	}

	public void setIsSetter(boolean isSetter) {
		this.isSetter = isSetter;
	}

	public boolean getIsGetter() {
		return isGetter;
	}

	public void setIsGetter(boolean isGetter) {
		this.isGetter = isGetter;
	}

	@Override
	public String toString() {
		return "BaseMethod [isPublic=" + isPublic + ", isProtected=" + isProtected + ", isPrivate=" + isPrivate
				+ ", isPackagePrivate=" + isPackagePrivate + ", isStatic=" + isStatic + ", isFinal=" + isFinal
				+ ", isSynchronized=" + isSynchronized + ", isStrict=" + isStrict + ", isAnnotatedWith="
				+ isAnnotatedWith + ", canThrow=" + canThrow + ", declaresException=" + declaresException
				+ ", isOverriddenFrom=" + isOverriddenFrom + ", isVirtual=" + isVirtual + ", isDefaultMethod="
				+ isDefaultMethod + ", isSetter=" + isSetter + ", isGetter=" + isGetter + ", getNameEndsWith()="
				+ getNameEndsWith() + ", getNameEndsWithIgnoreCase()=" + getNameEndsWithIgnoreCase()
				+ ", getNameStartsWith()=" + getNameStartsWith() + ", getNamed()=" + getNamed()
				+ ", getNamedIgnoreCase()=" + getNamedIgnoreCase() + ", getNameContains()=" + getNameContains()
				+ ", getNameContainsIgnoreCase()=" + getNameContainsIgnoreCase() + ", getNameMatches()="
				+ getNameRegex() + "]";
	}

}
