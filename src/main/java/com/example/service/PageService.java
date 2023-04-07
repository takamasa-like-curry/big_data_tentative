package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.mapper.CategoriesMapper;
import com.example.mapper.ItemsMapper;

@Service
@Transactional
public class PageService {

	@Autowired
	private ItemsMapper itemsMapper;
	@Autowired
	private CategoriesMapper categoriesMapper;

	public List<Item> showList(Integer thisPage) {

		Integer offset = 30 * (thisPage - 1);

		List<Item> itemList = itemsMapper.findAll(offset);
		itemList = createCategoryList(itemList);

		return itemList;

	}

	public List<Item> ShowListByForm(String name, String parentId, String childId, String grandChildId, String brand,
			Integer page) {

		Integer offset = 30 * (page - 1);
		Integer id = null;
		if (Integer.parseInt(grandChildId) > 0) {
			id = Integer.parseInt(grandChildId);
		} else if (Integer.parseInt(childId) > 0) {
			id = Integer.parseInt(childId);
		} else if (Integer.parseInt(parentId) > 0) {
			id = Integer.parseInt(parentId);
		}

		List<Item> itemList = itemsMapper.findByFilter(id, name, brand, offset);
		itemList = createCategoryList(itemList);
		return itemList;

	}

	public List<Item> createCategoryList(List<Item> itemList) {

		for (Item item : itemList) {
			Integer categoryId = item.getCategoryId();
			List<Category> categoryList = categoriesMapper.findByDescendantId(categoryId);
			item.setCategoryList(categoryList);
		}

		return itemList;
	}
}
