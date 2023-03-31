package com.example.form;

public class SerchItemsForm {

	private String name;
	private String parentId;
	private String childId;
	private String grandChildId;
	private String brand;

	@Override
	public String toString() {
		return "SerchItemsForm [name=" + name + ", parentId=" + parentId + ", childId=" + childId + ", grandChildId="
				+ grandChildId + ", brand=" + brand + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
