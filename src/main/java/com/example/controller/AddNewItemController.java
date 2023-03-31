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

import com.example.domain.Category;
import com.example.form.ItemForm;
import com.example.service.AddNewItemService;

@Controller
@RequestMapping("/add")
public class AddNewItemController {

	private static final Integer PARENT_LEVEL = 0;
	private static final Integer CHILD_LEVEL = 1;
	private static final Integer GRAND_CHILD_LEVEL = 2;

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
		List<Category> parentCategoryList = service.pickUpCategoryListByLevel(PARENT_LEVEL);
		model.addAttribute("parentCategoryList", parentCategoryList);

		if (form.getParentId() != null) {
			List<Category> childCategoryList = service
					.pickUpCategoryListByAncestorIdAndLevel(Integer.parseInt(form.getParentId()), CHILD_LEVEL);
			model.addAttribute("childCategoryList", childCategoryList);
		}
		if (form.getChildId() != null) {
			List<Category> grandChildCategoryList = service
					.pickUpCategoryListByAncestorIdAndLevel(Integer.parseInt(form.getChildId()), GRAND_CHILD_LEVEL);
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
		}

		return "add";
	}

	@PostMapping("/insert")
	public String insert(Model model, @Validated ItemForm form, BindingResult br) {

		// カテゴリの入力値チェック
		if (Integer.parseInt(form.getParentId()) == -1) {
			br.rejectValue("parentId", null, "選択必須項目です");
		} else if (Integer.parseInt(form.getChildId()) == -1) {
			br.rejectValue("parentId", null, "選択必須項目です(子カテゴリ、孫カテゴリも選択必須)");
		} else if (Integer.parseInt(form.getGrandChildId()) == -1) {
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
