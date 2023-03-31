package com.example.domain;

import java.util.List;

/**
 * 商品情報を扱うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
public class Item {

	/** 商品ID(自動採番IDとは異なる) */
	private Integer itemId;
	/** 商品名 */
	private String name;
	/** 商品状態 */
	private Integer condition;
	/** ブランド名 */
	private String brand;
	/** 価格 */
	private Double price;
	/** 郵送方法(あってる？) */
	private Integer shipping;
	/** 商品説明 */
	private String description;
	/** カテゴリID */
	private Integer categoryId;
	/** 親リスト(自分と先祖全て) */
	private List<Category> categoryList;
	/** パス(完全商品名) */
	private String nameAll;

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", name=" + name + ", condition=" + condition + ", brand=" + brand
				+ ", price=" + price + ", shipping=" + shipping + ", description=" + description + ", categoryId="
				+ categoryId + ", categoryList=" + categoryList + ", nameAll=" + nameAll + "]";
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

}
