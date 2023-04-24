package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.CategoryLevel;
import com.example.common.TentativeValue;
import com.example.domain.Category;
import com.example.form.AddCategoryForm;
import com.example.mapper.CategoriesMapper;
import com.example.mapper.CategoryTreePathsMapper;

/**
 * 新規カテゴリ追加に関する業務処理を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Service
@Transactional
public class AddNewCategoryService {

	@Autowired
	private CategoriesMapper categoriesMapper;
	@Autowired
	private CategoryTreePathsMapper categoryTreePathsMapper;

	/**
	 * 先祖IDと階層レベルからカテゴリリストを取得.
	 * 
	 * @param ancestorId
	 * @param level
	 * @return
	 */
	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer ancestorId, Integer level) {
		List<Category> categoryList = categoriesMapper.findByAncestorIdAndLevel(ancestorId, level);
		// 無名カテゴリ排除。新規追加商品には無名カテゴリを使用させないため。
		for (int i = 0; i < categoryList.size(); i++) {
			if ("".equals(categoryList.get(i).getName())) {
				categoryList.remove(i);
			}
		}
		return categoryList;
	}

	public Boolean checkCategoryNameDuplication(AddCategoryForm form) {
		Integer parentCategoryId = pickUpParentCategoryIdByForm(form);
		int level = pickUpAddCategoryLevel(parentCategoryId);
		return categoriesMapper.checkCategoryNameDuplication(form.getCategoryName(), parentCategoryId, level);
	}

	public synchronized void insert(AddCategoryForm form) {

		int addCategoryId = insertCategories(form);
		insertCategoryTreePaths(form, addCategoryId);

	}

	private Integer pickUpParentCategoryIdByForm(AddCategoryForm form) {
		Integer parentCategoryId;
		if (form.getParentCategoryId() == TentativeValue.CATEGORY_ID.getValue()) {
			parentCategoryId = null;
		} else if (form.getChildCategoryId() == TentativeValue.CATEGORY_ID.getValue()) {
			parentCategoryId = form.getParentCategoryId();
		} else {
			parentCategoryId = form.getChildCategoryId();
		}
		return parentCategoryId;
	}

	private int pickUpAddCategoryLevel(Integer parentCategoryId) {
		int level;
		if (parentCategoryId == null) {
			level = CategoryLevel.PARENT.getLevel();
		} else {
			level = categoriesMapper.pickUpLevelById(parentCategoryId);
			level++;
		}
		return level;
	}

	private int insertCategories(AddCategoryForm form) {
		Category category = new Category();
		category.setName(form.getCategoryName());
		Integer parentCategoryId = pickUpParentCategoryIdByForm(form);
		category.setLevel(pickUpAddCategoryLevel(parentCategoryId));
		category.setDescription(form.getCategoryName() + "の説明");

		// categoriesテーブルにインサート
		categoriesMapper.insert(category);
		return category.getId();
		
	}

	private void insertCategoryTreePaths(AddCategoryForm form, int addCategoryId) {
		List<int[]> ancestorIdList = new ArrayList<>();
		if (form.getParentCategoryId() != TentativeValue.CATEGORY_ID.getValue()) {
			ancestorIdList.add(new int[] { form.getParentCategoryId(), addCategoryId });
		}
		if (form.getChildCategoryId() != TentativeValue.CATEGORY_ID.getValue()) {
			ancestorIdList.add(new int[] { form.getChildCategoryId(), addCategoryId });
		}
		ancestorIdList.add(new int[] { addCategoryId, addCategoryId });

		// category/tree-pathsテーブルにインサート
		for (int[] pair : ancestorIdList) {
			categoryTreePathsMapper.insert(pair[0], pair[1]);
		}
	}
}
