package com.example.form;

import java.util.List;

import com.example.common.CategoryLevel;
import com.example.domain.Category;

import lombok.Data;

@Data
public class SerchItemsForm {

	/** 商品名 */
	private String name;
	/** 親カテゴリID */
	private Integer parentCategoryId;
	/** 子カテゴリID */
	private Integer childCategoryId;
	/** 孫カテゴリID */
	private Integer grandChildCategoryId;
	/** ブランド名 */
	private String brand;

	public SerchItemsForm() {
//		categoryIdIsNullValue();
	};

	private SerchItemsForm(String brand) {
		this.brand = brand;
	}

	private SerchItemsForm(List<Category> categoryList) {
		for (Category category : categoryList) {
			Integer level = category.getLevel();
			if (level == CategoryLevel.PARENT.getLevel()) {
				this.parentCategoryId = category.getId();
			} else if (level == CategoryLevel.CHILD.getLevel()) {
				this.childCategoryId = category.getId();
			} else if (level == CategoryLevel.GRAND_CHILD.getLevel()) {
				this.grandChildCategoryId = category.getId();
			} else {
				// エラー処理
			}

			
		}
	}

	public static SerchItemsForm createFormByBrand(String brand) {
		return new SerchItemsForm(brand);

	}

	public static SerchItemsForm createFormByCategoryList(List<Category> categoryList) {
		return new SerchItemsForm(categoryList);

	}


}
