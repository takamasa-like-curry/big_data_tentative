package com.example.common;

public enum CategoryLevel {

	PARENT(0), CHILD(1), GRAND_CHILD(2),;

	private final int level;

	private CategoryLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
