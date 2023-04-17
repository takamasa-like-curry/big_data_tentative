package com.example.domain;

import lombok.Data;

/**
 * 該当ページに表示する商品の検索フィルター.
 * 
 * @author sugaharatakamasa
 *
 */
@Data
public final class FilterOfShowItems {

	/** 検索する商品名(曖昧も可) */
	private String name;
	/** 検索するカテゴリID */
	private int categoryId;
	/** 検索するブランド名(曖昧も可) */
	private String brand;
	/** 表示するページ */
	private int page;

	public FilterOfShowItems() {
	};

	private FilterOfShowItems(String name, int categoryId, String brand, int page) {
		this.name = name;
		this.categoryId = categoryId;
		this.brand = brand;
		this.page = page;
	}

	public static FilterOfShowItems createFilter(String name, int categoryId, String brand, int page) {
		return new FilterOfShowItems(name, categoryId, brand, page);
	}
}
