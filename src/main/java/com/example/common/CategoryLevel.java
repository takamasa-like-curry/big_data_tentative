package com.example.common;

public enum CategoryLevel {

	PARENT(0), CHILD(1), GRAND_CHILD(2),;

	private final Integer level;

	private CategoryLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return level;
	}
}
