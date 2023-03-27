package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowListService;

@Controller
@RequestMapping("")
public class ShowListController {

	@Autowired
	private ShowListService service;

	@GetMapping("")
	public String showList(Model model, String page) {
		Integer total = service.countTotal();
		Integer totalPage = total / 30 + 1;
		model.addAttribute("totalPage", totalPage);

		Integer thisPage;
		Integer integerPage;
		try {
			integerPage = Integer.parseInt(page);
		} catch (Exception e) {
			integerPage = null;
		}
		if (integerPage == null || integerPage < 1 || integerPage > totalPage) {
			thisPage = 1;
		} else {
			thisPage = integerPage;
		}

		model.addAttribute("thisPage", thisPage);

		List<Item> itemlist = service.showList(thisPage);
		model.addAttribute("itemList", itemlist);

		return "list";
	}
}
