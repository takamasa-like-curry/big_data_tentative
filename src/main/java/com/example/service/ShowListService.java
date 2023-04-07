package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.IntegerConstants;
import com.example.common.PasingConstants;
import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SerchItemsForm;
import com.example.mapper.CategoriesMapper;
import com.example.mapper.ItemsMapper;

@Service
@Transactional
public class ShowListService {

	@Autowired
	private ItemsMapper itemsMapper;
	@Autowired
	private CategoriesMapper categoriesMapper;

	public List<Category> pickUpCategoryListByLevel(Integer level) {
		List<Category> categoryList = categoriesMapper.findByLevel(level);

		return categoryList;
	}

	public List<Category> pickUpCategoryListByDescendantId(Integer descendantId) {
		List<Category> categoryList = categoriesMapper.findByDescendantId(descendantId);
		return categoryList;
	}

	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer ancestorId, Integer level) {
		List<Category> categoryList = categoriesMapper.findByAncestorIdAndLevel(ancestorId, level);
		return categoryList;
	}

	public Integer countTotalByForm(SerchItemsForm form) {

		String name = form.getName();
		String brand = form.getBrand();
		Integer id = null;

		if (form.getGrandChildId() != IntegerConstants.CATEGORY_ID_IS_NULL.getValue()) {
			id = form.getGrandChildId();
		} else if (form.getChildId() != IntegerConstants.CATEGORY_ID_IS_NULL.getValue()) {
			id = form.getChildId();
		} else if (form.getParentId() != IntegerConstants.CATEGORY_ID_IS_NULL.getValue()) {
			id = form.getParentId();
		}

		return itemsMapper.countTotalQuantity(id, name, brand);

	}

	public List<Item> ShowListByForm(SerchItemsForm form, Integer thisPage) {
		thisPage--; // -1をしている
		Integer offset = PasingConstants.SIZE.getPage() * thisPage;
		String name = form.getName();
		String brand = form.getBrand();

		Integer id = null;
		Integer grandChildId = form.getGrandChildId();
		Integer childId = form.getChildId();
		Integer parentId = form.getParentId();
		if (grandChildId != IntegerConstants.CATEGORY_ID_IS_NULL.getValue() && grandChildId != null) {
			id = grandChildId;
		} else if (childId != IntegerConstants.CATEGORY_ID_IS_NULL.getValue() && childId != null) {
			id = childId;
		} else if (parentId != IntegerConstants.CATEGORY_ID_IS_NULL.getValue() && parentId != null) {
			id = parentId;
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
