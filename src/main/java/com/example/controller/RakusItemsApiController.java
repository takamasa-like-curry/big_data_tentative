package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.RakusItemsApiService;

@RestController
@RequestMapping("/api")
public class RakusItemsApiController {

	@Autowired
	private RakusItemsApiService service;



	@GetMapping("/check-category-name")
	public ResponseEntity<Boolean> checkChildCategory(String categoryName, Integer categoryId) {
		Boolean result = service.checkCategoryNameDuplication(categoryName, categoryId);
		return ResponseEntity.ok(result);
	}

}
