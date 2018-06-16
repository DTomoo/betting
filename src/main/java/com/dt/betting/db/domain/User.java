package com.dt.betting.db.domain;

import java.util.ArrayList;
import java.util.List;

public class User extends DomainObject<User> {

	private List<Long> betIds = new ArrayList<>();
	private boolean admin;
	private long scores;

	public List<Long> getBetIds() {
		return betIds;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public long getScores() {
		return scores;
	}

	public void setScores(long scores) {
		this.scores = scores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
