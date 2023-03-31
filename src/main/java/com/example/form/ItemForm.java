package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ItemForm {

	/** 商品名 */
	@NotBlank(message = "入力必須項目です")
	@Size(max = 255, message = "最大255文字までです。")
	private String inputName;
	/** 価格 */
	@NotBlank(message = "入力必須項目です")
	private String price;
	/** 親カテゴリ */
	@NotNull(message = "選択必須項目です")
	private String parentId;
	/** 子カテゴリ */
	@NotNull(message = "選択必須項目です")
	private String childId;
	/** 孫カテゴリ */
	@NotNull(message = "選択必須項目です")
	private String grandChildId;
	/** ブランド名 */
	@Size(max = 255, message = "最大255文字までです。")
	private String brand;
	/** 商品状態 */
	@NotNull(message = "選択必須項目です")
	private Integer condition;
	/** 商品説明 */
	@NotBlank(message = "入力必須項目です")
	private String description;

	@Override
	public String toString() {
		return "AddNewItemForm [inputName=" + inputName + ", price=" + price + ", parentId=" + parentId + ", childId="
				+ childId + ", grandChildId=" + grandChildId + ", brand=" + brand + ", condition=" + condition
				+ ", description=" + description + "]";
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public String getGrandChildId() {
		return grandChildId;
	}

	public void setGrandChildId(String grandChildId) {
		this.grandChildId = grandChildId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
