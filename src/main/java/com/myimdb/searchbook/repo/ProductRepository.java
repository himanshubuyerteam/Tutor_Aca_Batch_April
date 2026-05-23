package com.myimdb.searchbook.repo;

import com.myimdb.searchbook.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

//    Optional<Product> findByName(String name);
    Optional<Product> findByTitleIgnoreCase(String title);
    Optional<List<Product>> findByTitleContaining(String title);

    Optional<List<Product>> findByTitleContainingAndPriceGreaterThan(String title, double priceIsGreaterThan);


    Product save(Product product);
}
