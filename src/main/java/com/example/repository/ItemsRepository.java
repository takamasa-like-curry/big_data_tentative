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
		item.setNameAll(rs.getString("c_name_all"));
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
		sql.append(",c.name_all AS c_name_all");
		sql.append(" FROM " + TABLE_NAME + " AS i");
		sql.append(" LEFT OUTER JOIN categories AS c");
		sql.append(" ON i.category_id = c.id");
		sql.append(" GROUP BY i.item_id,i.name,i.condition,i.brand,i.price,i.shipping,i.description,c.name_all");
		sql.append(" ORDER BY item_id");
		sql.append(" LIMIT :limit");
		sql.append(" OFFSET :offset");

		SqlParameterSource param = new MapSqlParameterSource().addValue("limit", LIMIT).addValue("offset", offset);

		List<Item> itemList = template.query(sql.toString(), param, ITEM_ROW_MAPPER);
		return itemList;

	}

	public Item load(Integer itemId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" i.item_id AS i_item_id");
		sql.append(",i.name AS i_name");
		sql.append(",i.condition AS i_condition");
		sql.append(",i.brand AS i_brand");
		sql.append(",i.price AS i_price");
		sql.append(",i.shipping AS i_shipping");
		sql.append(",i.description AS i_description");
		sql.append(",c.name_all AS c_name_all");
		sql.append(" FROM " + TABLE_NAME + " AS i");
		sql.append(" LEFT OUTER JOIN categories AS c");
		sql.append(" ON i.category_id = c.id");
		sql.append(" WHERE i.item_id = :itemId");
		sql.append(" GROUP BY i.item_id,i.name,i.condition,i.brand,i.price,i.shipping,i.description,c.name_all");

		SqlParameterSource param = new MapSqlParameterSource("itemId", itemId);

		return template.queryForObject(sql.toString(), param, ITEM_ROW_MAPPER);

	}

	public Integer countTotal() {
		String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
		return template.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
	}

}
