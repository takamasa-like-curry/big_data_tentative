package com.example.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "items";
	private static final Integer LIMIT = 30;
	private static final RowMapper<Item> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);
	private static final RowMapper<Item> ITEM_ROW_MAPPER_JOINT_VER = (rs, i) -> {
		Item item = new Item();
		item.setItemId(rs.getInt("i_item_id"));
		item.setName(rs.getString("i_name"));
		item.setCondition(rs.getInt("i_condition"));
		item.setCategoryId(rs.getInt("i_category_id"));
		item.setBrand(rs.getString("i_brand"));
		item.setPrice(rs.getDouble("i_price"));
		item.setShipping(rs.getInt("i_shipping"));
		item.setDescription(rs.getString("i_description"));
		return item;
	};


	/**
	 * 全件検索(id昇順)を行う.
	 * 
	 * @return 全商品情報(id昇順)
	 */
	public List<Item> findAll(Integer offset) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" item_id,name,condition,category_id,brand,price,shipping,description");
		sql.append(" FROM " + TABLE_NAME);
		sql.append(" ORDER BY item_id");
		sql.append(" LIMIT :limit");
		sql.append(" OFFSET :offset");

		SqlParameterSource param = new MapSqlParameterSource().addValue("limit", LIMIT).addValue("offset", offset);

		List<Item> itemList = template.query(sql.toString(), param, ITEM_ROW_MAPPER);
		return itemList;

	}

	public List<Item> findByform(Integer id, String name, String brand, Integer offset) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append(" i.item_id AS i_item_id");
		sql.append(",i.name AS i_name");
		sql.append(",i.condition AS i_condition");
		sql.append(",i.category_id AS i_category_id");
		sql.append(",i.brand AS i_brand");
		sql.append(",i.price AS i_price");
		sql.append(",i.shipping AS i_shipping");
		sql.append(",i.description AS i_description");
		sql.append(" FROM category_tree_paths AS p");
		sql.append(" LEFT OUTER JOIN items AS i");
		sql.append(" ON p.descendant_id = i.category_id");
		sql.append(" WHERE");
		sql.append(" i.name ILIKE :name");
		if (id != null) {
			sql.append(" AND");
			sql.append(" p.ancestor_id = :id");
		}
		sql.append(" AND");
		sql.append(" i.brand ILIKE :brand");
		sql.append(" GROUP BY i.item_id,i.name,i.condition,i.category_id,i.brand,i.price,i.shipping,i.description");
		sql.append(" ORDER BY item_id");
		sql.append(" LIMIT :limit");
		sql.append(" OFFSET :offset");

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("id", id)
				.addValue("brand", "%" + brand + "%").addValue("limit", LIMIT).addValue("offset", offset);

		List<Item> itemList = template.query(sql.toString(), param, ITEM_ROW_MAPPER_JOINT_VER);
		return itemList;

	}

	public Item load(Integer itemId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" item_id,name,condition,category_id,brand,price,shipping,description");
		sql.append(" FROM " + TABLE_NAME);
		sql.append(" WHERE item_id = :itemId");

		SqlParameterSource param = new MapSqlParameterSource("itemId", itemId);

		return template.queryForObject(sql.toString(), param, ITEM_ROW_MAPPER);

	}

	public Integer countTotal() {
		String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
		return template.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
	}

	public Integer countTotalByForm(Integer id, String name, String brand) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*)");
		sql.append(" FROM category_tree_paths As p");
		sql.append(" LEFT OUTER JOIN items AS i");
		sql.append(" ON p.descendant_id = i.category_id");
		sql.append(" LEFT OUTER JOIN categories AS c");
		sql.append(" ON i.category_id = c.id");
		sql.append(" WHERE");
		sql.append(" i.name ILIKE :name");
		sql.append(" AND");
		sql.append(" i.brand ILIKE :brand");
		sql.append(" AND");
		if (id == null) {
			sql.append(" p.ancestor_id IN (");
			sql.append(" SELECT MIN(ancestor_id)");
			sql.append(" FROM category_tree_paths");
			sql.append(" GROUP BY descendant_id");
			sql.append(")");
		} else {
			sql.append(" p.ancestor_id = :id");
		}

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("id", id)
				.addValue("brand", "%" + brand + "%");

		Integer total = template.queryForObject(sql.toString(), param, Integer.class);

		return total;

	}

	public Integer pickUpLatestItemId() {
		StringBuilder sql = new StringBuilder("SELECT MAX(item_id) from items");
		Integer latestItemId = template.queryForObject(sql.toString(), new MapSqlParameterSource(), Integer.class);
		return latestItemId;
	}

	
	public synchronized void insertItem(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERt INTO " + TABLE_NAME);
		sql.append(
				" (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)");
		sql.append(" VALUES");
		sql.append(
				" (:itemId,:name,:condition,:categoryId,:brand,:price,:shipping,:description,:registeredDateTime,:registeredName,:updatedDateTime,:updatedName)");

		SqlParameterSource param = new MapSqlParameterSource()//
				.addValue("itemId", item.getItemId())//
				.addValue("name", item.getName())//
				.addValue("condition", item.getCondition())//
				.addValue("categoryId", item.getCategoryId())//
				.addValue("brand", item.getBrand())//
				.addValue("price", item.getPrice())//
				.addValue("shipping", item.getShipping())//
				.addValue("description", item.getDescription())//
				.addValue("registeredDateTime", Timestamp.valueOf(LocalDateTime.now()))//
				.addValue("registeredName", "ユーザーが登録(後ほどログイン処理を実装時に変更)")//
				.addValue("updatedDateTime", Timestamp.valueOf(LocalDateTime.now()))//
				.addValue("updatedName", "ユーザーが登録(後ほどログイン処理を実装時に変更)");//

		template.update(sql.toString(), param);

	}
	
	public void updateItem(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE " + TABLE_NAME);
		sql.append(" SET");
		sql.append(" name = :name");
		sql.append(",condition = :condition");
		sql.append(",category_id = :categoryId");
		sql.append(",brand = :brand");
		sql.append(",price = :price");
		sql.append(",shipping = :shipping");
		sql.append(",description = :description");
		sql.append(",registered_date_time = :registeredDateTime");
		sql.append(",registered_name = :registeredName");
		sql.append(",updated_date_time = :updatedDateTime");
		sql.append(",updated_name = :updatedName");
		sql.append(" WHERE");
		sql.append(" item_id = :itemId");
		
		SqlParameterSource param = new MapSqlParameterSource()//
				.addValue("itemId", item.getItemId())//
				.addValue("name", item.getName())//
				.addValue("condition", item.getCondition())//
				.addValue("categoryId", item.getCategoryId())//
				.addValue("brand", item.getBrand())//
				.addValue("price", item.getPrice())//
				.addValue("shipping", item.getShipping())//
				.addValue("description", item.getDescription())//
				.addValue("registeredDateTime", Timestamp.valueOf(LocalDateTime.now()))//
				.addValue("registeredName", "ユーザーが登録(後ほどログイン処理を実装時に変更)")//
				.addValue("updatedDateTime", Timestamp.valueOf(LocalDateTime.now()))//
				.addValue("updatedName", "ユーザーが登録(後ほどログイン処理を実装時に変更)");//
		
		template.update(sql.toString(), param);
	}
	
	public void createIndexForItemId() {
		String sql = "CREATE UNIQUE INDEX idx_item_id ON items(item_id)";
		template.update(sql,new MapSqlParameterSource());
	}
	
	public void deleteIndexForItemId() {
		String sql = "DROP INDEX IF EXISTS idx_item_id";
		template.update(sql,new MapSqlParameterSource());
		
	}

}
