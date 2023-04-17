package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Item;

@Mapper
public interface ItemsMapper {


	List<Item> findByFilter(@Param("name") String name, @Param("brand") String brand,
			@Param("categoryId") int categoryId, @Param("offset") int offset);

	int countTotalQuantity(@Param("name") String name, @Param("brand") String brand,
			@Param("categoryId") int categoryId);

	/**
	 * 商品追加.
	 * 
	 * @param item 追加する商品
	 */
	void insert(@Param("item") Item item);

	/**
	 * 商品情報更新.
	 * 
	 * @param item 更新商品情報
	 */
	void updateItem(@Param("item") Item item);

	/**
	 * ID検索.
	 * 
	 * @param itemId 商品ID
	 * @return 該当商品情報
	 */
	Item load(int itemId);

	/**
	 * 最新の商品IDを取得(自動採番でないID)
	 * 
	 * @return 最新の商品ID
	 */
	Integer pickUpLatestItemId();

	/**
	 * itemsテーブルのitem_idにINDEXを設定.
	 * 
	 */
	void createIndexForItemId();

	/**
	 * itemsテーブルのitem_idに設定されているINDEXを削除.
	 * 
	 */
	void deleteIndexForItemId();

}
