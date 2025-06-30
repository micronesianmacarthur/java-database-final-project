package com.project.code.Controller;

import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.OrderItemRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServiceClass serviceClass;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // add product
    @PostMapping
    public Map<String, Object> addProduct(@RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();

        if (!serviceClass.validateProduct(product)) {
            map.put("message", "Product already exist in database");
            return map;
        }
        try {
            productRepository.save(product);
            map.put("message", "Product successfully added");
        } catch (DataIntegrityViolationException dive) {
            map.put("message", "SKU must be unique");
        } catch (Exception e) {
            map.put("message", "An error occurred while saving the product");
        }
        return map;
    }

    // get product by id
    @GetMapping("/product/{id}")
    public Map<String, Object> getProductById(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>();
        Product result = productRepository.findByid(id);
        System.out.println("Result: " + result);
        map.put("products", result);
        return map;
    }

    // update product
    @PutMapping
    public Map<String, String> updateProduct(@RequestBody Product product) {
        Map<String, String> map = new HashMap<>();
        try {
            productRepository.save(product);
            map.put("message", "Product updated successfully");
        } catch (Error e) {
            map.put("message", "Error occurred");
        }
        return map;
    }

    // filter by category
    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterByCategoryProduct(@PathVariable String name, @PathVariable String category) {
        Map<String, Object> map = new HashMap<>();

        if (name.equals("null")) {
            map.put("product", productRepository.findByCategory(category));
            return map;
        } else if (category.equals("null")) {
            map.put("product", productRepository.findProductBySubName(name));
            return map;
        }
        map.put("product", productRepository.findProductBySubNameAndCategory(name, category));
        return map;
    }

    // list product
    @GetMapping
    public Map<String, Object> listProduct() {
        Map<String, Object> map = new HashMap<>();
        map.put("products", productRepository.findAll());
        return map;
    }

    // get product by category and store id
    @GetMapping("/filter/{category}/{storeId}")
    public Map<String, Object> getProductCategoryAndStoreId(@PathVariable String category, @PathVariable Long storeId) {
        Map<String, Object> map = new HashMap<>();
        List<Product> result = productRepository.findProductByCategory(category, storeId);
        map.put("product", result);
        return map;
    }

    // delete product
    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id) {
        Map<String, String> map = new HashMap<>();

        if(!serviceClass.validateProductId(id)) {
            map.put("message", "Id " + id + " not present in database");
            return map;
        }
        inventoryRepository.deleteByProductId(id);
        orderItemRepository.deleteById(id);
        productRepository.deleteById(id);

        map.put("message", "Deleted product with Id " + id);
        return map;
    }

    // search product
    @GetMapping("/searchProduct/{name}")
    public Map<String, Object> searchProduct(@PathVariable String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("products",productRepository.findProductBySubName(name));
        return map;
    }

}