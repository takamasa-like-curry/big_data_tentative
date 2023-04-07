package com.example.common;

public enum IntegerConstants {
	
	CATEGORY_ID_IS_NULL(-1),;

	private final Integer value;

	private IntegerConstants(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

}
