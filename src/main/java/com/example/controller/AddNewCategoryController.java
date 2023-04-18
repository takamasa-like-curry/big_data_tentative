package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.CategoryLevel;
import com.example.common.NullValue;
import com.example.domain.Category;
import com.example.form.AddCategoryForm;
import com.example.service.AddNewCategoryService;

@Controller
@RequestMapping("/add-category")
public class AddNewCategoryController {

	@Autowired
	private AddNewCategoryService service;

	@GetMapping("")
	public String showAddNewCategoryPage(Model model, AddCategoryForm form) {

		// 親カテゴリの処理
		List<Category> parentCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(
				NullValue.CATEGORY_ID.getValue(), CategoryLevel.PARENT.getLevel());
		model.addAttribute("parentCategoryList", parentCategoryList);

//		if (form.getParentId() != null) {
//			List<Category> childCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(form.getParentId(),
//					CategoryLevel.CHILD.getLevel());
//			model.addAttribute("childCategoryList", childCategoryList);
//		}
//		if (form.getChildId() != null) {
//			List<Category> grandChildCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(form.getChildId(),
//					CategoryLevel.GRAND_CHILD.getLevel());
//			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
//		}
		return "add-category";
	}
}
