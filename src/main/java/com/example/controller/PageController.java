package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Item;
import com.example.service.PageService;

@RestController
@RequestMapping("/page")
public class PageController {

	@Autowired
	private PageService service;

	@GetMapping("/next")
	public Map<String, List<Item>> pageNext(String name, String parentId, String childId, String grandChildId,
			String brand, String page) {
		Map<String, List<Item>> map = new HashMap<>();
		Integer nextPage = null;
		try {
			nextPage = Integer.parseInt(page);
		} catch (Exception e) {
			nextPage = 1;
		}

		List<Item> itemList = null;
		if (name == null && parentId == null && brand == null) {
			// フォームなし
			itemList = service.showList(nextPage);
		} else {
			// フォームあり
			itemList = service.ShowListByForm(name, parentId, childId, grandChildId, brand, nextPage);
		}

		map.put("itemList", itemList);
		return map;

	}
}
