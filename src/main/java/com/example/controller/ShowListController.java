package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.CategoryLevel;
import com.example.common.IntegerConstants;
import com.example.common.PasingConstants;
import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SerchItemsForm;
import com.example.service.ShowListService;

import jakarta.servlet.http.HttpSession;

/**
 * 商品一覧画面を操作するクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Controller
@RequestMapping("")
public class ShowListController {

	@Autowired
	private ShowListService service;
	@Autowired
	private HttpSession session;

	/**
	 * 商品一覧画面を表示する.
	 * 
	 * @param model モデル
	 * @param form  商品検索フォーム
	 * @param page  表示ページ
	 * @return
	 */
	@GetMapping("")
	public String showList(Model model, SerchItemsForm form, Integer page) {

		// sessionにフォームを追加
		session.setAttribute("form", form);

		Integer total = service.countTotalByForm(form);

		// ページ数の遷移の処理
		Integer totalPage = total / PasingConstants.SIZE.getPage(); // 途中
		totalPage++;
		model.addAttribute("totalPage", totalPage);

		page = checkPage(page, totalPage);
		session.setAttribute("page", page);

		List<Item> itemlist = service.ShowListByForm(form, page);
		model.addAttribute("itemList", itemlist);

		// 子カテゴリ・孫カテゴリの処理
		if (form.getParentId() != IntegerConstants.CATEGORY_ID_IS_NULL.getValue()) {
			List<Category> childCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(form.getParentId(),
					CategoryLevel.CHILD.getLevel());
			model.addAttribute("childCategoryList", childCategoryList);
		}
		if (form.getChildId() != IntegerConstants.CATEGORY_ID_IS_NULL.getValue()) {
			List<Category> grandChildCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(form.getChildId(),
					CategoryLevel.GRAND_CHILD.getLevel());
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
		}

		// 親カテゴリの処理
		List<Category> parentCategoryList = service.pickUpCategoryListByLevel(CategoryLevel.PARENT.getLevel());
		model.addAttribute("parentCategoryList", parentCategoryList);

		return "list";
	}

	@GetMapping("/prev-next")
	public String nextPage(Model model, SerchItemsForm form, Integer page) {

		form = (SerchItemsForm) session.getAttribute("form");

		return showList(model, form, page);
	}

	@GetMapping("/back")
	public String backPage(Model model, SerchItemsForm form, Integer page) {

		form = (SerchItemsForm) session.getAttribute("form");
		if ((Integer) session.getAttribute("page") != null) {
			page = (Integer) session.getAttribute("page");
		} else {
			page = 1;
		}
		return showList(model, form, page);
	}

	@GetMapping("/category")
	public String serchByCategory(Model model, Integer categoryId) {
		SerchItemsForm form = new SerchItemsForm();
		List<Category> categoryList = service.pickUpCategoryListByDescendantId(categoryId);
		for (Category category : categoryList) {
			if (category.getLevel() == CategoryLevel.PARENT.getLevel()) {
				form.setParentId(category.getId());
			} else if (category.getLevel() == CategoryLevel.CHILD.getLevel()) {
				form.setChildId(category.getId());
			} else if (category.getLevel() == CategoryLevel.GRAND_CHILD.getLevel()) {
				form.setGrandChildId(category.getId());
			}
		}

		return showList(model, form, null);
	}

	@GetMapping("/brand")
	public String serchByBrand(Model model, String brand) {
		SerchItemsForm form = new SerchItemsForm();
		form.setBrand(brand);

		return showList(model, form, null);
	}

	@GetMapping("/jump")
	public String jumpPage(Model model, String page) {

		SerchItemsForm form = (SerchItemsForm) session.getAttribute("form");
		Integer integerPage = null;
		try {
			integerPage = Integer.parseInt(page);
		} catch (Exception e) {
			integerPage = 1;
		}

		return showList(model, form, integerPage);
	}

	/**
	 * 表示するページを取得.
	 * 
	 * @param page      文字列型の指定ページ
	 * @param totalPage 総ページ数
	 * @return 表示するページ
	 */
	public Integer checkPage(Integer page, Integer totalPage) {

		if (page == null || page < 1 || page > totalPage) {
			page = 1;
		}
		return page;
	}
}
