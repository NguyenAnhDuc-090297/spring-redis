package com.ducnguyen.sbredis.repository;

import com.ducnguyen.sbredis.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN Category c ON p.categoryId = c.id WHERE p.name = :name and c.categoryType = :category")
    List<Product> searchByNameAndCategory(String name, String category);
}
