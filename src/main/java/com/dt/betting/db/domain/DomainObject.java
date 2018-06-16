package com.dt.betting.db.domain;

import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DomainObject<T> {

	protected Long id;
	protected String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean equalsId(long id) {
		return this.id != null && this.id.longValue() == id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equalsName(String name) {
		return this.name != null && this.name.equals(name);
	}

	public boolean equalsId(DomainObject<T> data) {
		return equalsON(data, DomainObject::getId);
	}

	public boolean equalsName(DomainObject<T> data) {
		return equalsON(data, DomainObject::getName);
	}

	private boolean equalsON(DomainObject<T> data, Function<DomainObject<T>, ?> domainObject) {
		if (data == null) {
			return false;
		}
		if (domainObject.apply(this) == null) {
			if (domainObject.apply(data) != null) {
				return false;
			}
		} else if (!domainObject.apply(this).equals(domainObject.apply(data))) {
			return false;
		}
		return true;
	}

	// private boolean equalsON(DomainObject<T> data) {
	// if (data == null) {
	// return false;
	// }
	// if (name == null) {
	// if (data.name != null) {
	// return false;
	// }
	// } else if (!name.equals(data.name)) {
	// return false;
	// }
	// return true;
	// }
	//
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
