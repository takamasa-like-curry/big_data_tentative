package com.example.mapper;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import com.example.domain.Category;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/CategoriesMapperTest.sql")
class CategoriesMapperTest {

	@Autowired
	private CategoriesMapper categoriesMapper;

	@Test
	void findByAncestorIdAndLevel_先祖IDと階層で検索_1() {
		Integer level = 0;
		Integer ancestorId = 1;
		List<Category> answerCategoryList = categoriesMapper.findByAncestorIdAndLevel(ancestorId, level);
		int answerCategoryListSize = answerCategoryList.size();
		int lastIndex = answerCategoryListSize - 1;
		Category answerFirstCategory = answerCategoryList.get(0);
		Category answerLastCategory = answerCategoryList.get(lastIndex);

		assertEquals(answerCategoryListSize, 1, "取得したカテゴリリストのサイズが異なります。");
		assertEquals(answerFirstCategory.getId(), 1, "1件目のIDが異なります。");
		assertEquals(answerFirstCategory.getName(), "a", "1件目のカテゴリ名が異なります。");
		assertEquals(answerLastCategory.getId(), 1, "最後のIDが異なります。");
		assertEquals(answerLastCategory.getName(), "a", "最後のカテゴリ名が異なります。");
	}

	@Test
	void findByAncestorIdAndLevel_先祖IDと階層で検索_2() {
		Integer level = 2;
		Integer ancestorId = 2;
		List<Category> answerCategoryList = categoriesMapper.findByAncestorIdAndLevel(ancestorId, level);
		int answerCategoryListSize = answerCategoryList.size();
		int lastIndex = answerCategoryListSize - 1;
		Category answerFirstCategory = answerCategoryList.get(0);
		Category answerLastCategory = answerCategoryList.get(lastIndex);

		assertEquals(answerCategoryListSize, 10, "取得したカテゴリリストのサイズが異なります。");
		assertEquals(answerFirstCategory.getId(), 3, "1件目のIDが異なります。");
		assertEquals(answerFirstCategory.getName(), "c", "1件目のカテゴリ名が異なります。");
		assertEquals(answerFirstCategory.getLevel(), 2, "1件目のカテゴリ名が異なります。");
		assertEquals(answerLastCategory.getId(), 12, "最後のIDが異なります。");
		assertEquals(answerLastCategory.getName(), "l", "最後のカテゴリ名が異なります。");
		assertEquals(answerLastCategory.getLevel(), 2, "最後のカテゴリ名が異なります。");
	}

	@Test
	void findByDescendantId_子孫IDで検索_1() {
		Integer descendantId = 3;
		List<Category> answerCategoryList = categoriesMapper.findByDescendantId(descendantId);
		int answerCategoryListSize = answerCategoryList.size();
		int lastIndex = answerCategoryListSize - 1;
		Category answerFirstCategory = answerCategoryList.get(0);
		Category answerLastCategory = answerCategoryList.get(lastIndex);

		assertEquals(answerCategoryListSize, 3, "取得したカテゴリリストのサイズが異なります。");
		assertEquals(answerFirstCategory.getId(), 1, "1件目のIDが異なります。");
		assertEquals(answerFirstCategory.getName(), "a", "1件目のカテゴリ名が異なります。");
		assertEquals(answerLastCategory.getId(), 3, "最後のIDが異なります。");
		assertEquals(answerLastCategory.getName(), "c", "最後のカテゴリ名が異なります。");
	}

	@Test
	void findByDescendantId_子孫IDで検索_2() {
		Integer descendantId = 2;
		List<Category> answerCategoryList = categoriesMapper.findByDescendantId(descendantId);
		int answerCategoryListSize = answerCategoryList.size();
		int lastIndex = answerCategoryListSize - 1;
		Category answerFirstCategory = answerCategoryList.get(0);
		Category answerLastCategory = answerCategoryList.get(lastIndex);

		assertEquals(answerCategoryListSize, 2, "取得したカテゴリリストのサイズが異なります。");
		assertEquals(answerFirstCategory.getId(), 1, "1件目のIDが異なります。");
		assertEquals(answerFirstCategory.getName(), "a", "1件目のカテゴリ名が異なります。");
		assertEquals(answerLastCategory.getId(), 2, "最後のIDが異なります。");
		assertEquals(answerLastCategory.getName(), "b", "最後のカテゴリ名が異なります。");
	}

}
