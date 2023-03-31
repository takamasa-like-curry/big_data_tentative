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
	private Integer level;

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", level=" + level + "]";
	}

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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
