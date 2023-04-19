package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.CategoryLevel;
import com.example.service.RakusItemsApiService;

@RestController
@RequestMapping("/api")
public class RakusItemsApi {

	@Autowired
	private RakusItemsApiService service;

	@GetMapping("/check-child-category")
	public ResponseEntity<Boolean> checkChildCategory(String categoryName) {
		Boolean result = service.checkCategoryNameDuplication(categoryName, CategoryLevel.CHILD.getLevel());
		return ResponseEntity.ok(result);
	}
}
