package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.mapper.ItemsMapper;
import com.example.repository.CategoriesRepository;

@Service
public class ShowDetailService {

	@Autowired
	private ItemsMapper itemsMapper;
	@Autowired
	private CategoriesRepository categoriesRepository;

	public Item showDetail(Integer itemId) {
		Item item = itemsMapper.load(itemId);
		List<Category> categoryList = categoriesRepository.pickUpCategoryListByDescendantId(item.getCategoryId());
		item.setCategoryList(categoryList);

		String nameAll = null;
		for (int i = 0; i < categoryList.size(); i++) {
			if (i == 0) {
				nameAll = categoryList.get(i).getName();
			} else {
				nameAll += " / " + categoryList.get(i).getName();
			}
		}
		item.setNameAll(nameAll);

		return item;

	}
}
