package org.stacksnap.config;

import java.util.Set;
import java.util.TreeSet;

public class BaseMethod extends CommonElements {

	private Set<String> canThrow = new TreeSet<String>();
	private Set<String> declaresException = new TreeSet<String>();
	private Set<String> isOverriddenFrom = new TreeSet<String>();
	
	private boolean isPrivate;
	private boolean isVirtual;
	private boolean isDefaultMethod;
	private boolean isSetter;
	private boolean isGetter;
	private boolean isSynchronized;
	private boolean isStrict;

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

	public boolean isVirtual() {
		return isVirtual;
	}

	public void setVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public boolean isDefaultMethod() {
		return isDefaultMethod;
	}

	public void setDefaultMethod(boolean isDefaultMethod) {
		this.isDefaultMethod = isDefaultMethod;
	}

	public boolean isSetter() {
		return isSetter;
	}

	public void setSetter(boolean isSetter) {
		this.isSetter = isSetter;
	}

	public boolean isGetter() {
		return isGetter;
	}

	public void setGetter(boolean isGetter) {
		this.isGetter = isGetter;
	}

	public void setSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}

	public void setStrict(boolean isStrict) {
		this.isStrict = isStrict;
	}
	
	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

}
