package com.example.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryTreePathsMapper {

	void insert(@Param("ancestorId") int ancestorId,@Param("descendantId") int descendantId);
}
