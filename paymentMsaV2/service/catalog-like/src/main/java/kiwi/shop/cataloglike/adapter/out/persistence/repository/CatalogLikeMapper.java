package kiwi.shop.cataloglike.adapter.out.persistence.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CatalogLikeMapper {

	void insertCatalogLike(
			@Param("catalogLikeNo") long catalogLikeNo,
			@Param("productNo") long productNo,
			@Param("memberNo") long memberNo
	);

	void deleteCatalogLike(
			@Param("productNo") long productNo,
			@Param("memberNo") long memberNo
	);
}
