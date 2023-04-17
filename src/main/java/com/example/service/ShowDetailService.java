package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.mapper.CategoriesMapper;
import com.example.mapper.ItemsMapper;

@Service
public class ShowDetailService {

	@Autowired
	private ItemsMapper itemsMapper;
	@Autowired
	private CategoriesMapper categoriesMapper;

	public Item showDetail(int itemId) {
		Item item = itemsMapper.load(itemId);
		List<Category> categoryList = categoriesMapper.findByDescendantId(item.getCategoryId());
		item.setCategoryList(categoryList);

		return item;

	}
}
