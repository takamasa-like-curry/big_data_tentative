package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mapper.CategoriesMapper;

@Service
@Transactional
public class RakusItemsApiService {

	@Autowired
	private CategoriesMapper categoriesMapper;
	
	public Boolean checkCategoryNameDuplication(String categoryName,int level) {
		return categoriesMapper.checkCategoryNameDuplication(categoryName, level);
	}
}
