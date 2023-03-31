package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Category;
import com.example.service.PickUpCategoryMapService;

@RestController
@RequestMapping("/pick-up-category-list")
public class PickUpCategoryListController {

	@Autowired
	private PickUpCategoryMapService service;

	private static final Integer CHILD_LEVEL = 1;
	private static final Integer GRAND_CHILD_LEVEL = 2;

	@GetMapping("/child-category")
	public Map<String, List<Category>> pickUpChildCategoryList(Integer parentId) {

		Map<String, List<Category>> map = new HashMap<>();
		List<Category> categoryList = service.pickUpCategoryListByAncestorIdAndLevel(parentId, CHILD_LEVEL);
		map.put("childCategoryList", categoryList);
		return map;

	}
	
	@GetMapping("/grand-child-category")
	public Map<String, List<Category>> pickUpGrandChildCategoryList(Integer childId) {

		Map<String, List<Category>> map = new HashMap<>();
		List<Category> categoryList = service.pickUpCategoryListByAncestorIdAndLevel(childId, GRAND_CHILD_LEVEL);
		map.put("grandChildCategoryList", categoryList);
		return map;

	}
}
