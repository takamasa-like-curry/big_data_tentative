package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.ItemForm;
import com.example.mapper.CategoriesMapper;
import com.example.mapper.ItemsMapper;

@Service
@Transactional
public class EditService {

	@Autowired
	private ItemsMapper itemsMapper;
	@Autowired
	private CategoriesMapper categoriesMapper;

	public Item load(Integer itemId) {
		Item item = itemsMapper.load(itemId);
		List<Category> categoryList = categoriesMapper.findByDescendantId(item.getCategoryId());
		item.setCategoryList(categoryList);

		return item;
	}



	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer ancestorId, Integer level) {
		List<Category> categoryList = categoriesMapper.findByAncestorIdAndLevel(ancestorId, level);
		return categoryList;
	}

	public synchronized void upDateItem(ItemForm form, Integer itemId) {

		Item item = createItem(form, itemId);
		itemsMapper.updateItem(item);
	}

	private Item createItem(ItemForm form, Integer itemId) {
		Item item = new Item();
		item.setItemId(itemId);
		item.setName(form.getInputName());
		item.setCondition(form.getCondition());
		item.setBrand(form.getBrand());
		item.setPrice(Double.parseDouble(form.getPrice()));
		item.setDescription(form.getDescription());
		item.setCategoryId(form.getGrandChildId());

		return item;
	}
}
