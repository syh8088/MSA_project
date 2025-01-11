package kiwi.shop.cataloglike.adapter.out.persistence.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Mapper
@Repository
public interface ProductLikeMapper {

	void insertProductLike(
			@Param("productLikeNo") long productLikeNo,
			@Param("productNo") long productNo,
			@Param("memberNo") long memberNo,
			@Param("createdAt") LocalDateTime createdAt
			);

	void deleteProductLike(
			@Param("productNo") long productNo,
			@Param("memberNo") long memberNo
	);
}
