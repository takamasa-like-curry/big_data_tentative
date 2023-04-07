package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.ItemForm;
import com.example.mapper.CategoriesMapper;
import com.example.mapper.ItemsMapper;

@Service
public class AddNewItemService {

	@Autowired
	private ItemsMapper itemsMapper;
	@Autowired
	private CategoriesMapper categoriesMapper;

	public List<Category> pickUpCategoryListByLevel(Integer level) {
		List<Category> categoryList = categoriesMapper.findByLevel(level);
		for (int i = 0; i < categoryList.size(); i++) {
			if ("".equals(categoryList.get(i).getName())) {
				categoryList.remove(i);
			}
		}
		return categoryList;
	}

	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer ancestorId, Integer level) {
		List<Category> categoryList = categoriesMapper.findByAncestorIdAndLevel(ancestorId, level);
		return categoryList;
	}

	public void insertItem(ItemForm form) {
		Item item = createItem(form);
		itemsMapper.deleteIndexForItemId();
		itemsMapper.insert(item);
		itemsMapper.createIndexForItemId();
	}

	public Item createItem(ItemForm form) {
		Item item = new Item();
		item.setName(form.getInputName());
		item.setCondition(form.getCondition());
		item.setBrand(form.getBrand());
		item.setPrice(Double.parseDouble(form.getPrice()));
		item.setShipping(9999); // shippingを一旦9999にしてしているが、データベースをnot nullにするほうが良いのか？
		item.setDescription(form.getDescription());
		item.setCategoryId(form.getGrandChildId());
		Integer itemId = itemsMapper.pickUpLatestItemId();
		itemId++;
		item.setItemId(itemId);

		return item;
	}

}
