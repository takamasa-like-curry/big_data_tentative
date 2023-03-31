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

	private static final RowMapper<Category> CATEGORY_ROW_MAPPER_JOINT_VER = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("c_id"));
		category.setName(rs.getString("c_name"));
		category.setLevel(rs.getInt("c_level"));
		return category;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	public Category pickUpCategory(String nameAll) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name FROM " + TABLE_NAME);
		sql.append(" WHERE name_all = :nameAll");

		SqlParameterSource param = new MapSqlParameterSource("nameAll", nameAll);

		List<Category> categoryList = template.query(sql.toString(), param, CATEGORY_ROW_MAPPER);
		if (categoryList.size() == 0) {
			return null;
		}
		return categoryList.get(0);

	}

	public List<Category> pickUpCategoryListByLevel(Integer level) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name FROM " + TABLE_NAME);
		sql.append(" WHERE level = :level");
		sql.append(" ORDER BY name");

		SqlParameterSource param = new MapSqlParameterSource("level", level);

		List<Category> categoryList = template.query(sql.toString(), param, CATEGORY_ROW_MAPPER);
		if (categoryList.size() == 0) {
			return null;
		}
		return categoryList;

	}

	public List<Category> pickUpCategoryListByAncestorIdAndLevel(Integer ancestorId, Integer level) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" c.id AS c_id");
		sql.append(",c.name AS c_name");
		sql.append(",c.level AS c_level");
		sql.append(" FROM category_tree_paths AS p");
		sql.append(" LEFT OUTER JOIN " + TABLE_NAME);
		sql.append(" AS c");
		sql.append(" ON c.id = p.descendant_id");
		sql.append(" WHERE");
		sql.append(" p.ancestor_id = :ancestorId");
		sql.append(" AND");
		sql.append(" c.level = :level");
		sql.append(" GROUP BY c.id,c.name,c.level");
		sql.append(" ORDER BY c.name");

		SqlParameterSource param = new MapSqlParameterSource().addValue("ancestorId", ancestorId).addValue("level",
				level);

		List<Category> categoryList = template.query(sql.toString(), param, CATEGORY_ROW_MAPPER_JOINT_VER);
		return categoryList;

	}

	public List<Category> pickUpCategoryListByDescendantId(Integer descendantId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" c.id AS c_id");
		sql.append(",c.name AS c_name");
		sql.append(",c.level AS c_level");
		sql.append(" FROM category_tree_paths AS p");
		sql.append(" LEFT OUTER JOIN " + TABLE_NAME);
		sql.append(" AS c");
		sql.append(" ON c.id = p.ancestor_id");
		sql.append(" WHERE");
		sql.append(" p.descendant_id = :descendantId");
		sql.append(" GROUP BY c.id,c.name,c.level");
		sql.append(" ORDER BY c.level");

		SqlParameterSource param = new MapSqlParameterSource("descendantId", descendantId);

		List<Category> categoryList = template.query(sql.toString(), param, CATEGORY_ROW_MAPPER_JOINT_VER);
		return categoryList;

	}

}
