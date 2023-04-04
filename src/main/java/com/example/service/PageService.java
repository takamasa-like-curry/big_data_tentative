package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.repository.CategoriesRepository;
import com.example.repository.ItemsRepository;

@Service
@Transactional
public class PageService {

	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	CategoriesRepository categoriesRepository;

	public List<Item> showList(Integer thisPage) {

		Integer offset = 30 * (thisPage - 1);

		List<Item> itemList = itemsRepository.findAll(offset);
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

		List<Item> itemList = itemsRepository.findByform(id, name, brand, offset);
		itemList = createCategoryList(itemList);
		return itemList;

	}

	public List<Item> createCategoryList(List<Item> itemList) {

		for (Item item : itemList) {
			Integer categoryId = item.getCategoryId();
			List<Category> categoryList = categoriesRepository.pickUpCategoryListByDescendantId(categoryId);
			item.setCategoryList(categoryList);
		}

		return itemList;
	}
	
	public Integer countTotal() {
		return itemsRepository.countTotal();
	}

	public Integer countTotalByInfo(String name, String parentId, String childId, String grandChildId,
			String brand) {
		
		Integer id = null;

			
			if (Integer.parseInt(grandChildId) > 0) {
				id = Integer.parseInt(grandChildId);
			} else if (Integer.parseInt(childId) > 0) {
				id = Integer.parseInt(childId);
			} else if (Integer.parseInt(parentId) > 0) {
				id = Integer.parseInt(parentId);
			}

		return itemsRepository.countTotalByForm(id, name, brand);

	}
	
	public List<Category> pickUpCategoryListByDescendantId(Integer descendantId) {
		List<Category> categoryList = categoriesRepository.pickUpCategoryListByDescendantId(descendantId);
		return categoryList;
	}
}
