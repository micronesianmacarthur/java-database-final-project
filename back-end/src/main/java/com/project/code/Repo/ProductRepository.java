package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // find by id
    Product findByid(Long id);

    // retrieve all products
    List<Product> findAll();

    // find product by category
    List<Product> findByCategory(String category);

    // find product by price range
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // find product by sku
    Product findBySKU(String sku);

    // find by name
    Product findByName(String name);

    // find by name match
    @Query("SELECT DISTINCT p FROM Product p JOIN Inventory i ON p.id = i.product.id WHERE i.store.id = :storeId AND p.name LIKE %:productName%")
    List<Product> findByNameLike(Long storeId, String productName);

    // find by name and category
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId and LOWER(i.product.name) LIKE LOWER(CONCAT('%', :productName, '%')) AND i.product.category = :category")
    List<Product> findByNameAndCategory(Long storeId, String productName, String category);

    // find by category and store id
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND i.product.category = :category")
    List<Product> findByCategoryAndStoreId(Long storeId, String category);

    // find by product name match
    @Query("SELECT i FROM Product i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :productName, '%'))")
    List<Product> findProductBySubName(String productName);

    // find by store id
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId")
    List<Product> findProductsByStoreId(Long storeId);

    // find by category
    @Query("SELECT i.product FROM Inventory i WHERE i.product.category = :category AND i.store.id = :storeId")
    List<Product> findProductByCategory(String category, Long storeId);

    // find by subname and category
    @Query("SELECT i FROM Product i WHERE LOWER(i.name) LIKE(CONCAT('%', :productName, '%')) AND i.category = :category")
    List<Product> findProductBySubNameAndCategory(String productName, String category);

}