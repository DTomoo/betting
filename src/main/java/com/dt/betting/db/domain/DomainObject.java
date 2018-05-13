package com.dt.betting.db.domain;

public class DomainObject<T> {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	boolean equalsId(DomainObject<T> data) {
		if (data == null) {
			return false;
		}
		if (id == null) {
			if (data.id != null) {
				return false;
			}
		} else if (!id.equals(data.id)) {
			return false;
		}
		return true;
	}
}
