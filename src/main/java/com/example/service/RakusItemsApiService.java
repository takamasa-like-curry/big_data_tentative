package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.CategoryLevel;
import com.example.mapper.CategoriesMapper;

@Service
@Transactional
public class RakusItemsApiService {

	@Autowired
	private CategoriesMapper categoriesMapper;

	public Boolean checkCategoryNameDuplication(String categoryName, Integer ancestorId) {
		int level;
		if (ancestorId == null) {
			level = CategoryLevel.PARENT.getLevel();
		} else {
			level = categoriesMapper.pickUpLevelById(ancestorId);
			level++;
		}
		return categoriesMapper.checkCategoryNameDuplication(categoryName, ancestorId, level);
	}
}
