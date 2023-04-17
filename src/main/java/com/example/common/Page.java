package com.example.common;

public enum Page {

	FIRST_PAGE(1),;

	private int page;

	private Page(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}
}
