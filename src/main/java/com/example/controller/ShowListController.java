package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.CategoryLevel;
import com.example.common.NullValue;
import com.example.common.Page;
import com.example.common.PasingConstants;
import com.example.domain.Category;
import com.example.domain.FilterOfShowItems;
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
		System.out.println("==============================");
		System.out.println(form);
		System.out.println("==============================");

		// sessionにフォームを追加
		session.setAttribute("form", form);

		// 表示するページ
		if (page == null) {
			page = Page.FIRST_PAGE.getPage();
		}
		session.setAttribute("page", page);

		// formからfilterへ変換
		FilterOfShowItems filter = formAndPageToFilter(form, page);

		int total = service.countTotaQuantitylByFilter(filter);

		// 総ページ数
		int totalPage = total / PasingConstants.SIZE.getPage(); // 途中
		model.addAttribute("totalPage", ++totalPage);

		List<Item> itemlist = service.PickUpItemListByFilter(filter);
		model.addAttribute("itemList", itemlist);

		// 親カテゴリの処理
		List<Category> parentCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(
				NullValue.CATEGORY_ID.getValue(), CategoryLevel.PARENT.getLevel());
		model.addAttribute("parentCategoryList", parentCategoryList);

		// 子カテゴリ・孫カテゴリの処理
		if (form.getParentCategoryId() != null) {
			List<Category> childCategoryList = service
					.pickUpCategoryListByAncestorIdAndLevel(form.getParentCategoryId(), CategoryLevel.CHILD.getLevel());
			model.addAttribute("childCategoryList", childCategoryList);
		}
		if (form.getChildCategoryId() != null) {
			List<Category> grandChildCategoryList = service.pickUpCategoryListByAncestorIdAndLevel(
					form.getChildCategoryId(), CategoryLevel.GRAND_CHILD.getLevel());
			model.addAttribute("grandChildCategoryList", grandChildCategoryList);
		}

		return "list";
	}

	@GetMapping("/prev-next")
	public String nextPage(Model model, Integer page) {
		SerchItemsForm form = (SerchItemsForm) session.getAttribute("form");
		return showList(model, form, page);
	}

	@GetMapping("/back")
	public String backPage(Model model) {
		Integer page = (int) session.getAttribute("page");
		SerchItemsForm form = (SerchItemsForm) session.getAttribute("form");
		return showList(model, form, page);
	}

	@GetMapping("/category")
	public String serchByCategory(Model model, Integer categoryId) {
		List<Category> categoryList = service.pickUpCategoryListByDescendantId(categoryId);

		return showList(model, SerchItemsForm.createFormByCategoryList(categoryList), null);
	}

	@GetMapping("/brand")
	public String serchByBrand(Model model, String brand) {

		return showList(model, SerchItemsForm.createFormByBrand(brand), null);
	}

	@GetMapping("/jump")
	public String jumpPage(Model model, String page, Integer totalPage) {

		Integer integerPage;
		try {
			integerPage = Integer.valueOf(page);
			integerPage = checkPage(integerPage, totalPage);
		} catch (Exception e) {
			integerPage = Page.FIRST_PAGE.getPage();
		}
		SerchItemsForm form = (SerchItemsForm) session.getAttribute("form");

		return showList(model, form, integerPage);
	}

	/**
	 * 表示するページを取得.
	 * 
	 * @param page      文字列型の指定ページ
	 * @param totalPage 総ページ数
	 * @return 表示するページ
	 */
	private Integer checkPage(Integer page, Integer totalPage) {

		if (page == null || page < Page.FIRST_PAGE.getPage() || page > totalPage) {
			page = Page.FIRST_PAGE.getPage();
		}
		return page;
	}

	/**
	 * フォームとページを受け取り、対応する検索フィルタオブジェクトを返す.
	 * 
	 * @param form 商品検索フォーム
	 * @param page 表示ページ
	 * @return 検索フィルター
	 */
	private FilterOfShowItems formAndPageToFilter(SerchItemsForm form, Integer page) {
		FilterOfShowItems filter = new FilterOfShowItems();
		filter.setName(form.getName());
		filter.setBrand(form.getBrand());
		if (form.getGrandChildCategoryId() != null) {
			filter.setCategoryId(form.getGrandChildCategoryId());
		} else if (form.getChildCategoryId() != null) {
			filter.setCategoryId(form.getChildCategoryId());
		} else if (form.getParentCategoryId() != null) {
			filter.setCategoryId(form.getParentCategoryId());
		} else {
			filter.setCategoryId(NullValue.CATEGORY_ID.getValue());
		}
		filter.setPage(page);
		return filter;
	}
}
