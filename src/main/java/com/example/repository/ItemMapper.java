package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Item;

/**
 * Mybatisを使用し、itemsテーブルの操作を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Mapper
public interface ItemMapper {

	List<Item> findAll(Integer offset);

	List<Item> findByform(Integer id, String name, String brand, Integer offset);
	
	Integer countTotal();
	

	Item load(Integer itemId);
}
