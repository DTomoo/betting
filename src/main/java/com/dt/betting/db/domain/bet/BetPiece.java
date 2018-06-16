package com.dt.betting.db.domain.bet;

public abstract class BetPiece<T> {

	private T value;

	public BetPiece(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public boolean equalsValue(BetPiece<?> other) {
		if (other == null)
			return false;
		return value == null ? other.getValue() == null : value.equals(other.getValue());
	}

	public abstract BetPieceType getType();

	public abstract long getScoreIfOk();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + value + "]";
	}
}
