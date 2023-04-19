package com.example.form;

import java.util.List;

import com.example.common.CategoryLevel;
import com.example.common.NullValue;
import com.example.domain.Category;

import lombok.Data;

@Data
public class SerchItemsForm {

	/** 商品名 */
	private String name;
	/** 親カテゴリID */
	private Integer parentId;
	/** 子カテゴリID */
	private Integer childId;
	/** 孫カテゴリID */
	private Integer grandChildId;
	/** ブランド名 */
	private String brand;

	public SerchItemsForm() {
//		categoryIdIsNullValue();
	};

	private SerchItemsForm(String brand) {
		categoryIdIsNullValue();
		this.brand = brand;
	}

	private SerchItemsForm(List<Category> categoryList) {
		for (Category category : categoryList) {
			Integer level = category.getLevel();
			if (level == CategoryLevel.PARENT.getLevel()) {
				this.parentId = category.getId();
			} else if (level == CategoryLevel.CHILD.getLevel()) {
				this.childId = category.getId();
			} else if (level == CategoryLevel.GRAND_CHILD.getLevel()) {
				this.grandChildId = category.getId();
			} else {
				// エラー処理
			}

			if (this.parentId == null) {
				this.parentId = NullValue.CATEGORY_ID.getValue();
			}
			if (this.childId == null) {
				this.childId = NullValue.CATEGORY_ID.getValue();
			}
			if (this.grandChildId == null) {
				this.grandChildId = NullValue.CATEGORY_ID.getValue();
			}
		}
	}

	public static SerchItemsForm createFormByBrand(String brand) {
		return new SerchItemsForm(brand);

	}

	public static SerchItemsForm createFormByCategoryList(List<Category> categoryList) {
		return new SerchItemsForm(categoryList);

	}

	private void categoryIdIsNullValue() {
		this.parentId = NullValue.CATEGORY_ID.getValue();
		this.childId = NullValue.CATEGORY_ID.getValue();
		this.grandChildId = NullValue.CATEGORY_ID.getValue();
	}

}
