package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.mapper.CategoriesMapper;

@Service
@Transactional
public class PickUpCategoryMapService {

	@Autowired
	private CategoriesMapper categoriesMapper;

	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer parentId, Integer level) {

		return categoriesMapper.findByAncestorIdAndLevel(parentId, level);
	}
}
