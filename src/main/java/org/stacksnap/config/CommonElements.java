package org.stacksnap.config;

import java.util.Set;
import java.util.TreeSet;

public class CommonElements {
	private Set<String> nameEndsWith = new TreeSet<String>();
	private Set<String> nameEndsWithIgnoreCase = new TreeSet<String>();
	private Set<String> nameStartsWith = new TreeSet<String>();
	private Set<String> named = new TreeSet<String>();
	private Set<String> namedIgnoreCase = new TreeSet<String>();
	private Set<String> nameContains = new TreeSet<String>();
	private Set<String> nameContainsIgnoreCase = new TreeSet<String>();
	private Set<String> nameRegex = new TreeSet<String>();
	private Set<String> nameMatches = new TreeSet<String>();
	private Set<String> isAnnotatedWith = new TreeSet<String>();

	private boolean isPublic;
	private boolean isProtected;
	private boolean isPackagePrivate;
	private boolean isStatic;
	private boolean isFinal;

	public boolean checkValid() {
		return !nameEndsWith.isEmpty() || !nameEndsWithIgnoreCase.isEmpty() || !nameStartsWith.isEmpty()
				|| !named.isEmpty() || !namedIgnoreCase.isEmpty() || !nameContains.isEmpty()
				|| !nameContainsIgnoreCase.isEmpty() || !nameRegex.isEmpty() || !nameMatches.isEmpty()
				|| !isAnnotatedWith.isEmpty() || isPublic == true || isProtected == true
				|| isPackagePrivate == true || isStatic == true || isFinal == true;
	}

	public Set<String> getNameEndsWith() {
		return nameEndsWith;
	}

	public void setNameEndsWith(Set<String> nameEndsWith) {
		this.nameEndsWith = nameEndsWith;
	}

	public Set<String> getNameEndsWithIgnoreCase() {
		return nameEndsWithIgnoreCase;
	}

	public void setNameEndsWithIgnoreCase(Set<String> nameEndsWithIgnoreCase) {
		this.nameEndsWithIgnoreCase = nameEndsWithIgnoreCase;
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

	public Set<String> getNameRegex() {
		return nameRegex;
	}

	public void setNameRegex(Set<String> nameRegex) {
		this.nameRegex = nameMatches;
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

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public boolean isPackagePrivate() {
		return isPackagePrivate;
	}

	public void setPackagePrivate(boolean isPackagePrivate) {
		this.isPackagePrivate = isPackagePrivate;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

}
