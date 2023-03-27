package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * 「items」テーブルを操作するクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Repository
public class ItemsRepository {

	private static final String TABLE_NAME = "items";
	private static final Integer LIMIT = 30;
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setItemId(rs.getInt("i_item_id"));
		item.setName(rs.getString("i_name"));
		item.setCondition(rs.getInt("i_condition"));
		item.setBrand(rs.getString("i_brand"));
		item.setPrice(rs.getDouble("i_price"));
		item.setShipping(rs.getInt("i_shipping"));
		item.setDescription(rs.getString("i_description"));
		item.setPath(rs.getString("c_path"));
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 全件検索(id昇順)を行う.
	 * 
	 * @return 全商品情報(id昇順)
	 */
	public List<Item> findAll(Integer offset) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" i.item_id AS i_item_id");
		sql.append(",i.name AS i_name");
		sql.append(",i.condition AS i_condition");
		sql.append(",i.brand AS i_brand");
		sql.append(",i.price AS i_price");
		sql.append(",i.shipping AS i_shipping");
		sql.append(",i.description AS i_description");
		sql.append(",c.path AS c_path");
		sql.append(" FROM " + TABLE_NAME + " AS i");
		sql.append(" LEFT OUTER JOIN categories AS c");
		sql.append(" ON i.category_id = c.id");
//		sql.append(" where i.id <= 100");
		sql.append(" GROUP BY i.item_id,i.name,i.condition,i.brand,i.price,i.shipping,i.description,c.path");
		sql.append(" ORDER BY item_id");
		sql.append(" LIMIT :limit");
		sql.append(" OFFSET :offset");

		SqlParameterSource param = new MapSqlParameterSource().addValue("limit", LIMIT).addValue("offset", offset);

		List<Item> itemList = template.query(sql.toString(),param, ITEM_ROW_MAPPER);
		return itemList;

	}

	public Integer countTotal() {
		String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
		return template.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
	}

}
