package com.example.common;

public enum NullValue {

	CATEGORY_ID(-1),PAGE(1),SHIPPING(-1),;
	
	private final int value;
	
	private NullValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
