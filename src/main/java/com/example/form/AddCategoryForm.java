package com.example.form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCategoryForm {

	@NotNull(message="選択必須項目です")
	private Integer parentCategoryId;
	@NotNull(message="選択必須項目です")
	private Integer childCategoryId;
	@NotBlank(message="入力必須項目です")
	private String categoryName;
	
	public AddCategoryForm() {
	}
}
