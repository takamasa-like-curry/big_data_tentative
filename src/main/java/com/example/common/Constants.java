package com.example.common;

public enum Constants {
	
	NAME_IS_NULL(""),
	BRAND_IS_NULL(""),
	CATEGORY_ID_IS_NULL("-1"),;

	private final String value;

	private Constants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
