package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

@Repository
public class CategoriesRepository {

	private static final String TABLE_NAME = "categories";

	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = new BeanPropertyRowMapper<>(Category.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	public Category pickUpCategory(String path) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name FROM " + TABLE_NAME);
		sql.append(" WHERE path = :path");

		SqlParameterSource param = new MapSqlParameterSource("path", path);

		List<Category> categoryList = template.query(sql.toString(), param, CATEGORY_ROW_MAPPER);
		if (categoryList.size() == 0) {
			return null;
		}
		return categoryList.get(0);

	}
}
