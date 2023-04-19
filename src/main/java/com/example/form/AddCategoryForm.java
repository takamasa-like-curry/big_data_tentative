package com.example.form;

import com.example.common.NullValue;

import lombok.Data;

@Data
public class AddCategoryForm {

	private Integer parentId;
	private Integer childId;
	private String parentCategoryName;
	private String childCategoryName;
	private String grandChildCategoryName;
	
	public AddCategoryForm() {
		this.parentId = NullValue.CATEGORY_ID.getValue();
		this.childId = NullValue.CATEGORY_ID.getValue();
	}
}
