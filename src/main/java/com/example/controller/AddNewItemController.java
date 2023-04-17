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
import com.example.domain.Category;
import com.example.form.ItemForm;
import com.example.service.AddNewItemService;

@Controller
@RequestMapping("/add")
public class AddNewItemController {

	@Autowired
	private AddNewItemService service;

	/**
	 * 追加商品入力画面を表示.
	 * 
	 * @param model モデル
	 * @param form  追加商品情報フォーム
	 * @return 追加商品入力画面
	 */
	@GetMapping("")
	public String showAddNewItemPage(Model model, ItemForm form) {
		// 親カテゴリの処理
		List<Category> parentCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(
				NullValue.CATEGORY_ID.getValue(), CategoryLevel.PARENT.getLevel());
		model.addAttribute("parentCategoryList", parentCategoryList);

		if (form.getParentId() != null) {
			List<Category> childCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(form.getParentId(),
					CategoryLevel.CHILD.getLevel());
			model.addAttribute("childCategoryList", childCategoryList);
		}
		if (form.getChildId() != null) {
			List<Category> grandChildCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(form.getChildId(),
					CategoryLevel.GRAND_CHILD.getLevel());
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
		}

		return "add";
	}

	@PostMapping("/insert")
	public String insert(Model model, @Validated ItemForm form, BindingResult br) {

		// カテゴリの入力値チェック
		if (form.getParentId() == NullValue.CATEGORY_ID.getValue()) {
			br.rejectValue("parentId", null, "選択必須項目です");
		} else if (form.getChildId() == NullValue.CATEGORY_ID.getValue()) {
			br.rejectValue("parentId", null, "選択必須項目です(子カテゴリ、孫カテゴリも選択必須)");
		} else if (form.getGrandChildId() == NullValue.CATEGORY_ID.getValue()) {
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
			return showAddNewItemPage(model, form);
		}

		service.insertItem(form);

		return "redirect:/";
	}
}
