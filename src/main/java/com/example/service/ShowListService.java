package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.PasingConstants;
import com.example.domain.Category;
import com.example.domain.FilterOfShowItems;
import com.example.domain.Item;
import com.example.mapper.CategoriesMapper;
import com.example.mapper.ItemsMapper;

@Service
@Transactional
public class ShowListService {

	@Autowired
	private ItemsMapper itemsMapper;
	@Autowired
	private CategoriesMapper categoriesMapper;

	/**
	 * 指定階層のカテゴリリストを取得.
	 * 
	 * 主に、検索カテゴリ選択フォームの表示に使用
	 * @param level カテゴリの階層
	 * @return 該当階層のカテゴリリスト
	 */

	public List<Category> pickUpCategoryListByDescendantId(Integer descendantId) {
		List<Category> categoryList = categoriesMapper.findByDescendantId(descendantId);
		return categoryList;
	}

	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer ancestorId, Integer level) {
		List<Category> categoryList = categoriesMapper.findByAncestorIdAndLevel(ancestorId, level);
		return categoryList;
	}

	public int countTotaQuantitylByFilter(FilterOfShowItems filter) {
		String name = filter.getName();
		String brand = filter.getBrand();
		int categoryId = filter.getCategoryId();

		return itemsMapper.countTotalQuantity(name, brand, categoryId);
	}

	public List<Item> PickUpItemListByFilter(FilterOfShowItems filter) {
		String name = filter.getName();
		String brand = filter.getBrand();
		int categoryId = filter.getCategoryId();
		int page = filter.getPage();
		int offset = PasingConstants.SIZE.getPage() * --page;

		List<Item> itemList = itemsMapper.findByFilter(name, brand, categoryId, offset);
		itemList = createCategoryList(itemList);
		return itemList;
	}



	private List<Item> createCategoryList(List<Item> itemList) {

		for (Item item : itemList) {
			Integer categoryId = item.getCategoryId();
			List<Category> categoryList = categoriesMapper.findByDescendantId(categoryId);
			item.setCategoryList(categoryList);
		}

		return itemList;
	}

}
