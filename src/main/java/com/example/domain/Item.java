package com.example.domain;

import java.util.List;

import lombok.Data;

/**
 * 商品情報を扱うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Data
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

}
