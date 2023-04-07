package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Category;

@Mapper
public interface CategoriesMapper {

	List<Category> findByLevel(Integer level);

	List<Category> findByAncestorIdAndLevel(@Param("ancestorId") Integer ancestorId, @Param("level") Integer level);

	List<Category> findByDescendantId(Integer descendantId);

}
