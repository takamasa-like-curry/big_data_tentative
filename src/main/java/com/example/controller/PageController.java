package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Item;
import com.example.domain.pageInfo;
import com.example.service.PageService;

@RestController
@RequestMapping("/page")
public class PageController {

	@Autowired
	private PageService service;

	@GetMapping("/base")
	public Map<String, List<Item>> pageNext(String name, String parentId, String childId, String grandChildId,
			String brand, String page) {
		Map<String, List<Item>> map = new HashMap<>();

		Integer integerPage = null;
		try {
			integerPage = Integer.parseInt(page);

		} catch (Exception e) {
			integerPage = 1;

		}

		List<Item> itemList = null;
		if (name == null && parentId == null && brand == null) {
			// フォームなし
			itemList = service.showList(integerPage);
		} else {
			// フォームあり
			itemList = service.ShowListByForm(name, parentId, childId, grandChildId, brand, integerPage);
		}

		map.put("itemList", itemList);
		return map;

	}

	@GetMapping("")
	public Map<String, pageInfo> pageNext2(String name, String parentId, String childId, String grandChildId,
			String brand, String page) {
		Map<String, pageInfo> map = new HashMap<>();
		pageInfo pageInfo = new pageInfo();
		Integer totalPage = null;
		if (name == null && parentId == null && brand == null) {
			// フォームなし
			totalPage = service.countTotal() / 30 + 1;
		} else {
			// フォームあり
			totalPage = service.countTotalByInfo(name, parentId, childId, grandChildId, brand) / 30 + 1;
		}

		Integer integerPage = null;
		try {
			integerPage = Integer.parseInt(page);
			if (integerPage == null || integerPage < 1 || integerPage > totalPage) {
				integerPage = 1;
			}

		} catch (Exception e) {
			integerPage = 1;

		}

		List<Item> itemList = null;
		if (name == null && parentId == null && brand == null) {
			// フォームなし
			itemList = service.showList(integerPage);
		} else {
			// フォームあり
			itemList = service.ShowListByForm(name, parentId, childId, grandChildId, brand, integerPage);
		}

		pageInfo.setItemList(itemList);
		pageInfo.setTotalPage(totalPage);
		pageInfo.setThisPage(integerPage);
		map.put("pageInfo", pageInfo);
		return map;

	}

}
