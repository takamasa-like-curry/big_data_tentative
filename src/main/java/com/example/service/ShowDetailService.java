package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.repository.CategoriesRepository;
import com.example.repository.ItemsRepository;

@Service
public class ShowDetailService {

	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private CategoriesRepository categoriesRepository;

	public Item showDetail(Integer itemId) {
		Item item = itemsRepository.load(itemId);
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
