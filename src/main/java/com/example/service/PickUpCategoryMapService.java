package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.repository.CategoriesRepository;

@Service
@Transactional
public class PickUpCategoryMapService {

	@Autowired
	private CategoriesRepository categoriesRepository;

	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer parentId, Integer level) {

		return categoriesRepository.pickUpCategoryListByAncestorIdAndLevel(parentId, level);
	}
}
