package com.example.common;

public enum PasingConstants {

	FIRST_PAGE(1), SIZE(30),;

	private final int page;

	private PasingConstants(Integer page) {
		this.page = page;
	}

	public Integer getPage() {
		return page;
	}

}
