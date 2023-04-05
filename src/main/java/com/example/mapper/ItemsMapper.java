package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Item;

@Mapper
public interface ItemsMapper {

	List<Item> findAll(Integer offset);

	List<Item> findByForm(@Param("id") Integer id, @Param("name") String name, @Param("brand") String brand,
			@Param("offset") Integer offset);

	/**
	 * 全商品件数取得.
	 * 
	 * @return 全商品件数
	 */
	Integer countTotal();

	/**
	 * 該当商品件数取得.
	 * 
	 * @param id    カテゴリID
	 * @param name  商品名(曖昧検索)
	 * @param brand ブランド名(曖昧検索)
	 * @return 該当商品件数
	 */
	Integer countTotalByForm(@Param("id") Integer id, @Param("name") String name, @Param("brand") String brand);

	/**
	 * 商品追加.
	 * 
	 * @param item 追加する商品
	 */
	void insertItem(@Param("item") Item item);

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
	Item load(Integer itemId);

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
	 * itemsテーブルのitem_idを削除.
	 * 
	 */
	void deleteIndexForItemId();

}
