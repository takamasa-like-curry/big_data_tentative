package com.example.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import com.example.domain.Item;

/**
 * ItemsMapperクラスのテストを行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/ItemsMapperTest.sql")
class ItemsMapperTest {

	@Autowired
	ItemsMapper itemsMapper;

	@Test
	void 該当商品件数を取得_条件なし() {
		Integer categoryId = null;
		String name = null;
		String brand = null;
		Integer answerTotalQuantity = itemsMapper.countTotalQuantity(categoryId, name, brand);

		assertEquals(answerTotalQuantity, 10, "取得した要素の数が異なります");
	}

	@Test
	void 該当商品件数を取得_カテゴリ検索() {
		Integer categoryId = 100;
		String name = null;
		String brand = null;
		Integer answerTotalQuantity = itemsMapper.countTotalQuantity(categoryId, name, brand);

		assertEquals(answerTotalQuantity, 1, "取得した要素の数が異なります");
	}

	@Test
	void 該当商品件数を取得_名前検索() {
		Integer categoryId = null;
		String name = "item";
		String brand = null;
		Integer answerTotalQuantity = itemsMapper.countTotalQuantity(categoryId, name, brand);

		assertEquals(answerTotalQuantity, 10, "取得した要素の数が異なります");
	}

	@Test
	void 該当商品件数を取得_名前検索2() {
		Integer categoryId = null;
		String name = "item1";
		String brand = null;
		Integer answerTotalQuantity = itemsMapper.countTotalQuantity(categoryId, name, brand);

		assertEquals(answerTotalQuantity, 2, "取得した要素の数が異なります");
	}

	@Test
	void 該当商品件数を取得_ブランド名検索1() {
		Integer categoryId = null;
		String name = null;
		String brand = "test company";
		Integer answerTotalQuantity = itemsMapper.countTotalQuantity(categoryId, name, brand);

		assertEquals(answerTotalQuantity, 10, "取得した要素の数が異なります");
	}

	@Test
	void 該当商品件数を取得_ブランド名検索2() {
		Integer categoryId = null;
		String name = null;
		String brand = "pany";
		Integer answerTotalQuantity = itemsMapper.countTotalQuantity(categoryId, name, brand);

		assertEquals(answerTotalQuantity, 10, "取得した要素の数が異なります");
	}

	@Test
	void 該当商品情報リストを取得_条件なし() {
		Integer categoryId = null;
		String name = null;
		String brand = null;
		Integer offset = 0;
		List<Item> answerItemList = itemsMapper.findByFilter(categoryId, name, brand, offset);
		int answerItemListSize = answerItemList.size();
		int latestIndex = answerItemListSize - 1;

		assertEquals(answerItemListSize, 10, "取得情報の件数が異なります。");
		assertEquals(answerItemList.get(0).getItemId(), 1, "1件目の商品IDが異なります。");
		assertEquals(answerItemList.get(0).getName(), "item1", "1件目の商品名が異なります。");
		assertEquals(answerItemList.get(0).getCondition(), 1, "1件目の商品状態が異なります。");
		assertEquals(answerItemList.get(0).getBrand(), "test company", "1件目のブランド名が異なります。");
		assertEquals(answerItemList.get(0).getPrice(), 10, "1件目の商品価格が異なります。");
		assertEquals(answerItemList.get(0).getShipping(), 1, "1件目のshippingが異なります。");
		assertEquals(answerItemList.get(0).getDescription(), "item1 is good", "1件目の商品概要が異なります。");
		assertEquals(answerItemList.get(0).getCategoryId(), 100, "1件目のカテゴリIDが異なります。");
		assertEquals(answerItemList.get(0).getCategoryList(), null, "1件目のカテゴリリストが異なります。");

		assertEquals(answerItemList.get(latestIndex).getItemId(), 10, "最後の商品IDが異なります。");
		assertEquals(answerItemList.get(latestIndex).getName(), "item10", "最後の商品名が異なります。");
		assertEquals(answerItemList.get(latestIndex).getCondition(), 1, "最後の商品状態が異なります。");
		assertEquals(answerItemList.get(latestIndex).getBrand(), "test company", "最後のブランド名が異なります。");
		assertEquals(answerItemList.get(latestIndex).getPrice(), 100, "最後の商品価格が異なります。");
		assertEquals(answerItemList.get(latestIndex).getShipping(), 1, "最後のshippingが異なります。");
		assertEquals(answerItemList.get(latestIndex).getDescription(), "item10 is good", "最後の商品概要が異なります。");
		assertEquals(answerItemList.get(latestIndex).getCategoryId(), 1000, "最後のカテゴリIDが異なります。");
		assertEquals(answerItemList.get(latestIndex).getCategoryList(), null, "最後のカテゴリIDが異なります。");

	}

	@Test
	void 該当商品情報リストを取得_名前検索1() {
		Integer categoryId = null;
		String name = "item2";
		String brand = null;
		Integer offset = 0;
		List<Item> answerItemList = itemsMapper.findByFilter(categoryId, name, brand, offset);
		int answerItemListSize = answerItemList.size();

		assertEquals(answerItemListSize, 1, "取得情報の件数が異なります。");
		assertEquals(answerItemList.get(0).getItemId(), 2, "1件目の商品IDが異なります。");
		assertEquals(answerItemList.get(0).getName(), "item2", "1件目の商品名が異なります。");
		assertEquals(answerItemList.get(0).getCondition(), 2, "1件目の商品状態が異なります。");
		assertEquals(answerItemList.get(0).getBrand(), "test company", "1件目のブランド名が異なります。");
		assertEquals(answerItemList.get(0).getPrice(), 20, "1件目の商品価格が異なります。");
		assertEquals(answerItemList.get(0).getShipping(), 1, "1件目のshippingが異なります。");
		assertEquals(answerItemList.get(0).getDescription(), "item2 is good", "1件目の商品概要が異なります。");
		assertEquals(answerItemList.get(0).getCategoryId(), 200, "1件目のカテゴリIDが異なります。");
		assertEquals(answerItemList.get(0).getCategoryList(), null, "1件目のカテゴリリストが異なります。");
	}

	@Test
	void 該当商品情報リストを取得_名前検索2() {
		Integer categoryId = null;
		String name = "item1";
		String brand = null;
		Integer offset = 0;
		List<Item> answerItemList = itemsMapper.findByFilter(categoryId, name, brand, offset);
		int answerItemListSize = answerItemList.size();
		int latestIndex = answerItemListSize - 1;

		assertEquals(answerItemListSize, 2, "取得情報の件数が異なります。");
		assertEquals(answerItemList.get(0).getItemId(), 1, "1件目の商品IDが異なります。");
		assertEquals(answerItemList.get(0).getName(), "item1", "1件目の商品名が異なります。");
		assertEquals(answerItemList.get(0).getCondition(), 1, "1件目の商品状態が異なります。");
		assertEquals(answerItemList.get(0).getBrand(), "test company", "1件目のブランド名が異なります。");
		assertEquals(answerItemList.get(0).getPrice(), 10, "1件目の商品価格が異なります。");
		assertEquals(answerItemList.get(0).getShipping(), 1, "1件目のshippingが異なります。");
		assertEquals(answerItemList.get(0).getDescription(), "item1 is good", "1件目の商品概要が異なります。");
		assertEquals(answerItemList.get(0).getCategoryId(), 100, "1件目のカテゴリIDが異なります。");
		assertEquals(answerItemList.get(0).getCategoryList(), null, "1件目のカテゴリリストが異なります。");

		assertEquals(answerItemList.get(latestIndex).getItemId(), 10, "最後の商品IDが異なります。");
		assertEquals(answerItemList.get(latestIndex).getName(), "item10", "最後の商品名が異なります。");
		assertEquals(answerItemList.get(latestIndex).getCondition(), 1, "最後の商品状態が異なります。");
		assertEquals(answerItemList.get(latestIndex).getBrand(), "test company", "最後のブランド名が異なります。");
		assertEquals(answerItemList.get(latestIndex).getPrice(), 100, "最後の商品価格が異なります。");
		assertEquals(answerItemList.get(latestIndex).getShipping(), 1, "最後のshippingが異なります。");
		assertEquals(answerItemList.get(latestIndex).getDescription(), "item10 is good", "最後の商品概要が異なります。");
		assertEquals(answerItemList.get(latestIndex).getCategoryId(), 1000, "最後のカテゴリIDが異なります。");
		assertEquals(answerItemList.get(latestIndex).getCategoryList(), null, "最後のカテゴリIDが異なります。");

	}

	@Test
	void 該当商品情報リストを取得_ブランド名検索1() {
		Integer categoryId = null;
		String name = null;
		String brand = "test company";
		Integer offset = 0;
		List<Item> answerItemList = itemsMapper.findByFilter(categoryId, name, brand, offset);
		int answerItemListSize = answerItemList.size();
		int latestIndex = answerItemListSize - 1;

		assertEquals(answerItemListSize, 10, "取得情報の件数が異なります。");
		assertEquals(answerItemList.get(0).getItemId(), 1, "1件目の商品IDが異なります。");
		assertEquals(answerItemList.get(0).getName(), "item1", "1件目の商品名が異なります。");
		assertEquals(answerItemList.get(0).getCondition(), 1, "1件目の商品状態が異なります。");
		assertEquals(answerItemList.get(0).getBrand(), "test company", "1件目のブランド名が異なります。");
		assertEquals(answerItemList.get(0).getPrice(), 10, "1件目の商品価格が異なります。");
		assertEquals(answerItemList.get(0).getShipping(), 1, "1件目のshippingが異なります。");
		assertEquals(answerItemList.get(0).getDescription(), "item1 is good", "1件目の商品概要が異なります。");
		assertEquals(answerItemList.get(0).getCategoryId(), 100, "1件目のカテゴリIDが異なります。");
		assertEquals(answerItemList.get(0).getCategoryList(), null, "1件目のカテゴリリストが異なります。");

		assertEquals(answerItemList.get(latestIndex).getItemId(), 10, "最後の商品IDが異なります。");
		assertEquals(answerItemList.get(latestIndex).getName(), "item10", "最後の商品名が異なります。");
		assertEquals(answerItemList.get(latestIndex).getCondition(), 1, "最後の商品状態が異なります。");
		assertEquals(answerItemList.get(latestIndex).getBrand(), "test company", "最後のブランド名が異なります。");
		assertEquals(answerItemList.get(latestIndex).getPrice(), 100, "最後の商品価格が異なります。");
		assertEquals(answerItemList.get(latestIndex).getShipping(), 1, "最後のshippingが異なります。");
		assertEquals(answerItemList.get(latestIndex).getDescription(), "item10 is good", "最後の商品概要が異なります。");
		assertEquals(answerItemList.get(latestIndex).getCategoryId(), 1000, "最後のカテゴリIDが異なります。");
		assertEquals(answerItemList.get(latestIndex).getCategoryList(), null, "最後のカテゴリIDが異なります。");

	}

	@Test
	void 該当商品情報リストを取得_ブランド名検索2() {
		Integer categoryId = null;
		String name = null;
		String brand = "apple";
		Integer offset = 0;
		List<Item> answerItemList = itemsMapper.findByFilter(categoryId, name, brand, offset);
		int answerItemListSize = answerItemList.size();

		assertEquals(answerItemListSize, 0, "取得情報の件数が異なります。");

	}

	@Test
	void 該当商品情報リストを取得_カテゴリ検索() {
		Integer categoryId = 100;
		String name = null;
		String brand = null;
		Integer offset = 0;
		List<Item> answerItemList = itemsMapper.findByFilter(categoryId, name, brand, offset);
		int answerItemListSize = answerItemList.size();

		assertEquals(answerItemListSize, 1, "取得情報の件数が異なります。");
		assertEquals(answerItemList.get(0).getItemId(), 1, "1件目の商品IDが異なります。");
		assertEquals(answerItemList.get(0).getName(), "item1", "1件目の商品名が異なります。");
		assertEquals(answerItemList.get(0).getCondition(), 1, "1件目の商品状態が異なります。");
		assertEquals(answerItemList.get(0).getBrand(), "test company", "1件目のブランド名が異なります。");
		assertEquals(answerItemList.get(0).getPrice(), 10, "1件目の商品価格が異なります。");
		assertEquals(answerItemList.get(0).getShipping(), 1, "1件目のshippingが異なります。");
		assertEquals(answerItemList.get(0).getDescription(), "item1 is good", "1件目の商品概要が異なります。");
		assertEquals(answerItemList.get(0).getCategoryId(), 100, "1件目のカテゴリIDが異なります。");
		assertEquals(answerItemList.get(0).getCategoryList(), null, "1件目のカテゴリリストが異なります。");

	}

	@Test
	void 該当商品情報リストを取得_ページ指定() {
		Integer categoryId = 100;
		String name = null;
		String brand = null;
		Integer offset = 30; // ２ページ目は30件目より後の情報を取得する
		List<Item> answerItemList = itemsMapper.findByFilter(categoryId, name, brand, offset);
		int answerItemListSize = answerItemList.size();

		assertEquals(answerItemListSize, 0, "取得情報の件数が異なります。");

	}

	@Test
	void 該当商品情報を取得() {
		Integer itemId = 1;
		Item answerItem = itemsMapper.load(itemId);

		assertEquals(answerItem.getItemId(), 1, "商品IDが異なります。");
		assertEquals(answerItem.getName(), "item1", "商品名が異なります。");
		assertEquals(answerItem.getCondition(), 1, "商品状態が異なります。");
		assertEquals(answerItem.getBrand(), "test company", "ブランド名が異なります。");
		assertEquals(answerItem.getPrice(), 10, "商品価格が異なります。");
		assertEquals(answerItem.getShipping(), 1, "shippingが異なります。");
		assertEquals(answerItem.getDescription(), "item1 is good", "商品概要が異なります。");
		assertEquals(answerItem.getCategoryId(), 100, "カテゴリIDが異なります。");
		assertEquals(answerItem.getCategoryList(), null, "カテゴリリストが異なります。");

	}

}
