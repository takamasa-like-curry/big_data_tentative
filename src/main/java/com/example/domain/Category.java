package com.example.domain;

import lombok.Data;

/**
 * カテゴリーの情報を扱うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Data
public class Category {

	/** カテゴリID */
	private int id;
	/** カテゴリ名 */
	private String name;
	private String description;
	/** カテゴリ階層 */
	private int level;

}
