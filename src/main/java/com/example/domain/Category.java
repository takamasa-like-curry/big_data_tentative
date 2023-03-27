package com.example.domain;

/**
 * カテゴリーの情報を扱うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
public class Category {

	/** カテゴリID */
	private Integer id;
	/** カテゴリ名 */
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
}
