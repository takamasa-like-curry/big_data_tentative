package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	private static final Integer PARENT_LEVEL = 0;
	private static final Integer CHILD_LEVEL = 1;
	private static final Integer GRAND_CHILD_LEVEL = 2;

	/**
	 * 商品一覧画面を表示する.
	 * 
	 * @param model モデル
	 * @param form 商品検索フォーム
	 * @param page 表示ページ
	 * @return
	 */
	@GetMapping("")
	public String showList(Model model, SerchItemsForm form, Integer page) {


		// sessionにフォームを追加
		session.setAttribute("form", form);
		

		Integer total = null;
		if (form.getName() == null && form.getParentId() == null && form.getBrand() == null) {
			// フォームなし
			total = service.countTotal();
		} else {
			// フォームあり
			model.addAttribute("serchItemsForm", form);
			total = service.countTotalByForm(form);
		}

		// ページ数の遷移の処理
		Integer totalPage = total / 30 + 1;
		model.addAttribute("totalPage", totalPage);

		page = checkPage(page, totalPage);
		session.setAttribute("page", page);

		List<Item> itemlist = null;
		if (form.getName() == null && form.getParentId() == null && form.getBrand() == null) {
			// フォームなし
			itemlist = service.showList(page);
		} else {
			// フォームあり
			itemlist = service.ShowListByForm(form, page);

			// 子カテゴリ・孫カテゴリの処理
			if (Integer.parseInt(form.getParentId()) > 0) {
				List<Category> childCategoryList = service
						.pickUpCategoryListByAncestorIdAndLevel(Integer.parseInt(form.getParentId()), CHILD_LEVEL);
				model.addAttribute("childCategoryList", childCategoryList);
			}
			if (Integer.parseInt(form.getChildId()) > 0) {
				List<Category> grandChildCategoryList = service
						.pickUpCategoryListByAncestorIdAndLevel(Integer.parseInt(form.getChildId()), GRAND_CHILD_LEVEL);
				model.addAttribute("grandChildCategoryList", grandChildCategoryList);
			}
		}
		model.addAttribute("itemList", itemlist);

		List<Category> parentCategoryList = service.pickUpCategoryListByLevel(PARENT_LEVEL);
		model.addAttribute("parentCategoryList", parentCategoryList);

		return "list";
	}

	@GetMapping("/prev-next")
	public String nextPage(Model model, SerchItemsForm form, Integer page, Integer categoryId, String selectBrand) {

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
	public String serchByCategory(Model model, SerchItemsForm form, Integer categoryId) {

		form.setName("");
		form.setBrand("");
		form.setParentId("-1");
		form.setChildId("-1");
		form.setGrandChildId("-1");
		List<Category> categoryList = service.pickUpCategoryListByDescendantId(categoryId);
		for (Category category : categoryList) {
			if (category.getLevel() == PARENT_LEVEL) {
				form.setParentId(category.getId().toString());
			} else if (category.getLevel() == CHILD_LEVEL) {
				form.setChildId(category.getId().toString());
			} else if (category.getLevel() == GRAND_CHILD_LEVEL) {
				form.setGrandChildId(category.getId().toString());
			}
		}

		return showList(model, form, null);
	}
	
	@GetMapping("/brand")
	public String serchByBrand(Model model, SerchItemsForm form, String brand) {

		form.setName("");
		form.setBrand(brand);
		form.setParentId("-1");
		form.setChildId("-1");
		form.setGrandChildId("-1");
		
		return showList(model, form, null);
	}
	

	@GetMapping("/jump")
	public String jumpPage(Model model, SerchItemsForm form, String page, Integer categoryId, String selectBrand) {

		form = (SerchItemsForm) session.getAttribute("form");
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
