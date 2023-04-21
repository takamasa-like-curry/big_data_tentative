package com.example.common;

public enum TentativeValue {

	CATEGORY_ID(-2);

	private int value;

	private TentativeValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
