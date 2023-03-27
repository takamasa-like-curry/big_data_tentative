package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.repository.CategoriesRepository;
import com.example.repository.ItemsRepository;

@Service
@Transactional
public class ShowListService {

	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private CategoriesRepository categoriesRepository;

	public List<Item> showList(Integer thisPage) {

		Integer offset = 30 * (thisPage - 1);

		List<Item> itemList = itemsRepository.findAll(offset);
		Map<String, Category> categoryMap = new HashMap<>();
		for (Item item : itemList) {
			List<Category> categoryList = new ArrayList<>();
			String path = item.getPath();
			String[] pathPiecies = path.split("/");
			String pathPiece = "";
			for (int i = 0; i < pathPiecies.length; i++) {

				if (i == 0) {
					pathPiece += pathPiecies[i];
				} else {
					pathPiece += "/" + pathPiecies[i];
				}
				Category category;
				if (categoryMap.get(pathPiece) == null) {
					category = categoriesRepository.pickUpCategory(pathPiece);
					if (category == null) {
						System.out.println("該当カテゴリがありませんでした。");
						continue;
					}
					categoryMap.put(pathPiece, category);

				} else {
					category = categoryMap.get(pathPiece);
				}
				categoryList.add(category);
			}
			item.setCategoryList(categoryList);

		}
		return itemList;

	}

	public Integer countTotal() {
		return itemsRepository.countTotal();
	}
}
