package com.ll.cafeservice.entity.product.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @Query("SELECT i FROM ProductDetail i WHERE i.status = 'ACTIVE'")
    List<ProductDetail> findAllActivateProduct();
}
