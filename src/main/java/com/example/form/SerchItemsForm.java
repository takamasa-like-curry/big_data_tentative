package com.example.form;

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

}
