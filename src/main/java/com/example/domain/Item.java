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

	/** ID(自動採番) */
	private int id;
	/** 商品ID(自動採番IDとは異なる) */
	private int itemId;
	/** 商品名 */
	private String name;
	/** 商品状態 */
	private int condition;
	/** ブランド名 */
	private String brand;
	/** 価格 */
	private double price;
	/** 郵送方法(あってる？) */
	private int shipping;
	/** 商品説明 */
	private String description;
	/** カテゴリID */
	private int categoryId;
	/** 親リスト(自分と先祖全て) */
	private List<Category> categoryList;

}
