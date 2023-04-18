package com.example.form;

import lombok.Data;

@Data
public class AddCategoryForm {

	private Integer parentId;
	private Integer childId;
	private Integer grandChildId;
	private String parentCategoryName;
	private String childCategoryName;
	private String grandChildCategoryName;
}
