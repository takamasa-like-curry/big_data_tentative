package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.common.NullValue;
import com.example.domain.Category;
import com.example.mapper.CategoriesMapper;
import com.example.mapper.ItemsMapper;

@SpringBootTest
class ShowListServiceTest {

	@Mock
	private ItemsMapper itemsMapper;
	@Mock
	private CategoriesMapper categoriesMapper;
	@InjectMocks
	private ShowListService showListService;

	@Test
	void pickUpCategoryListByLevelのテスト() {
		Integer level = 0;

		List<Category> categoryList = new ArrayList<>();
		Category category = new Category();
		category.setId(1);
		category.setLevel(level);
		category.setName("test category");

		doReturn(categoryList).when(categoriesMapper).findByAncestorIdAndLevel(NullValue.CATEGORY_ID.getValue(), level);

		List<Category> anserCategoryList = categoriesMapper.findByAncestorIdAndLevel(NullValue.CATEGORY_ID.getValue(),
				level);
		assertEquals(anserCategoryList, categoryList, "戻り値のカテゴリリストが異なります。");
	}

}
