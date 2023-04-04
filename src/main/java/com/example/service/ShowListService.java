package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SerchItemsForm;
import com.example.repository.CategoriesRepository;
import com.example.repository.ItemMapper;
import com.example.repository.ItemsRepository;

@Service
@Transactional
public class ShowListService {

	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private CategoriesRepository categoriesRepository;
	@Autowired
	private ItemMapper itemMapper;

	public List<Item> showList(Integer thisPage) {

		Integer offset = 30 * (thisPage - 1);

		List<Item> itemList = itemMapper.findAll(offset);
		itemList = createCategoryList(itemList);

		return itemList;

	}

	public List<Category> pickUpCategoryListByLevel(Integer level) {
		List<Category> categoryList = categoriesRepository.pickUpCategoryListByLevel(level);
		if (categoryList.size() == 0) {
			throw new RuntimeException("categoriesテーブルに該当カテゴリが存在しません。");
		}
		return categoryList;
	}

	public List<Category> pickUpCategoryListByDescendantId(Integer descendantId) {
		List<Category> categoryList = categoriesRepository.pickUpCategoryListByDescendantId(descendantId);
		return categoryList;
	}

	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer ancestorId, Integer level) {
		List<Category> categoryList = categoriesRepository.pickUpCategoryListByAncestorIdAndLevel(ancestorId, level);
		return categoryList;
	}

	public Integer countTotal() {
		return itemMapper.countTotal();
	}

	public Integer countTotalByForm(SerchItemsForm form) {
		
		String name = form.getName();
		String brand = form.getBrand();
		Integer id = null;

			
			if (Integer.parseInt(form.getGrandChildId()) > 0) {
				id = Integer.parseInt(form.getGrandChildId());
			} else if (Integer.parseInt(form.getChildId()) > 0) {
				id = Integer.parseInt(form.getChildId());
			} else if (Integer.parseInt(form.getParentId()) > 0) {
				id = Integer.parseInt(form.getParentId());
			}

		return itemsRepository.countTotalByForm(id, name, brand);

	}

	public List<Item> ShowListByForm(SerchItemsForm form, Integer thisPage) {

		Integer offset = 30 * (thisPage - 1);
		String name = form.getName();
		String brand = form.getBrand();
		Integer id = null;
		if (Integer.parseInt(form.getGrandChildId()) > 0) {
			id = Integer.parseInt(form.getGrandChildId());
		} else if (Integer.parseInt(form.getChildId()) > 0) {
			id = Integer.parseInt(form.getChildId());
		} else if (Integer.parseInt(form.getParentId()) > 0) {
			id = Integer.parseInt(form.getParentId());
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

}
