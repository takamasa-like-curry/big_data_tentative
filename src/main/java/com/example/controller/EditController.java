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

import com.example.common.IntegerConstants;
import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.ItemForm;
import com.example.service.EditService;

@Controller
@RequestMapping("/edit")
public class EditController {

	private static final Integer PARENT_LEVEL = 0;
	private static final Integer CHILD_LEVEL = 1;
	private static final Integer GRAND_CHILD_LEVEL = 2;

	@Autowired
	private EditService service;

	@GetMapping("")
	public String showEditPage(Model model, ItemForm form, Integer itemId, BindingResult br) {

		model.addAttribute("itemId", itemId);
		Item item = service.load(itemId);

		if (!br.hasErrors()) {
			form.setInputName(item.getName());
			form.setPrice(item.getPrice().toString());
			form.setParentId(item.getCategoryList().get(PARENT_LEVEL).getId());
			form.setChildId(item.getCategoryList().get(CHILD_LEVEL).getId());
			form.setGrandChildId(item.getCategoryList().get(GRAND_CHILD_LEVEL).getId());
			form.setBrand(item.getBrand());
			form.setCondition(item.getCondition());
			form.setDescription(item.getDescription());

		}

		// 親カテゴリの処理
		List<Category> parentCategoryList = service.pickUpCategoryListByLevel(PARENT_LEVEL);
		model.addAttribute("parentCategoryList", parentCategoryList);

		if (form.getParentId() != null) {
			List<Category> childCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(form.getParentId(),
					CHILD_LEVEL);
			model.addAttribute("childCategoryList", childCategoryList);
		}
		if (form.getChildId() != null) {
			List<Category> grandChildCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(form.getChildId(),
					GRAND_CHILD_LEVEL);
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
		}

		return "edit";
	}

	@PostMapping("/update")
	public String insert(Model model, @Validated ItemForm form, BindingResult br, Integer itemId) {

		// カテゴリの入力値チェック
		if (form.getParentId() == IntegerConstants.CATEGORY_ID_IS_NULL.getValue()) {
			br.rejectValue("parentId", null, "選択必須項目です");
		} else if (form.getChildId() == IntegerConstants.CATEGORY_ID_IS_NULL.getValue()) {
			br.rejectValue("parentId", null, "選択必須項目です(子カテゴリ、孫カテゴリも選択必須)");
		} else if (form.getGrandChildId() == IntegerConstants.CATEGORY_ID_IS_NULL.getValue()) {
			br.rejectValue("parentId", null, "選択必須項目です(孫カテゴリも選択必須)");
		}

		// 金額のチェック
		if (!("".equals(form.getPrice()))) {
			try {
				Double.parseDouble(form.getPrice());
			} catch (Exception e) {
				br.rejectValue("price", null, "価格を数値で入力してください(単位は$です)");
			}
		}

		// エラーがあれば入力画面に遷移
		if (br.hasErrors()) {
			return showEditPage(model, form, itemId, br);
		}

		service.upDateItem(form, itemId); // updateに書き換え

		return "redirect:/";
	}
}
