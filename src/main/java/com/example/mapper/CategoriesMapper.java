package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Category;

@Mapper
public interface CategoriesMapper {


	List<Category> findByAncestorIdAndLevel(@Param("ancestorId") int ancestorId, @Param("level") int level);

	List<Category> findByDescendantId(int descendantId);

}
