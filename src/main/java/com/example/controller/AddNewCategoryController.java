package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.CategoryLevel;
import com.example.common.NullValue;
import com.example.common.TentativeValue;
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

		// 親カテゴリリストの取得
		List<Category> parentCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(
				NullValue.CATEGORY_ID.getValue(), CategoryLevel.PARENT.getLevel());
		model.addAttribute("parentCategoryList", parentCategoryList);

		//子カテゴリリストの取得
		if (form.getParentCategoryId() != null && form.getParentCategoryId() != TentativeValue.CATEGORY_ID.getValue()) {
			List<Category> childCategoryList = service
					.pickUpCategoryListByAncestorIdAndLevel(form.getParentCategoryId(), CategoryLevel.CHILD.getLevel());
			model.addAttribute("childCategoryList", childCategoryList);
		}
		model.addAttribute("tentativeCategoryId", TentativeValue.CATEGORY_ID.getValue());

		return "add-category";
	}

	@PostMapping("/insert")
	public String insert(Model model, @Validated AddCategoryForm form, BindingResult result) {
		if (result.hasErrors()) {
			return showAddNewCategoryPage(model, form);
		}

		if (!service.checkCategoryNameDuplication(form)) {
			result.rejectValue("categoryName", null, "このカテゴリ名は使用できません。[既に使用済みのカテゴリ名です]");
			return showAddNewCategoryPage(model, form);
		}

		service.insert(form);
		return "redirect:/";
	}
}
