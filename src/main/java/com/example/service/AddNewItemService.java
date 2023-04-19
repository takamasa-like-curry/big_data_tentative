package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.common.NullValue;
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



	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer ancestorId, Integer level) {
		List<Category> categoryList = categoriesMapper.findByAncestorIdAndLevel(ancestorId, level);
		//無名カテゴリ排除。新規追加商品には無名カテゴリを使用させないため。
		for (int i = 0; i < categoryList.size(); i++) {
			if ("".equals(categoryList.get(i).getName())) {
				categoryList.remove(i);
			}
		}
		return categoryList;
	}

	public void insertItem(ItemForm form) {
		Item item = createItem(form);
		itemsMapper.deleteIndexForItemId();
		itemsMapper.insert(item);
		itemsMapper.createIndexForItemId();
	}

	private Item createItem(ItemForm form) {
		Item item = new Item();
		item.setName(form.getInputName());
		item.setCondition(form.getCondition());
		item.setBrand(form.getBrand());
		item.setPrice(Double.parseDouble(form.getPrice()));
		item.setShipping(NullValue.SHIPPING.getValue()); 
		item.setDescription(form.getDescription());
		item.setCategoryId(form.getGrandChildId());
		Integer itemId = itemsMapper.pickUpLatestItemId();
		item.setItemId(++itemId);

		return item;
	}
	


}
