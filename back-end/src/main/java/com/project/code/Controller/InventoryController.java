package com.project.code.Controller;


import com.project.code.Model.CombinedRequest;
import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    // update inventory
    @PutMapping
    public Map<String, String> updateInventory(@RequestBody CombinedRequest request) {
        Product product = request.getProduct();
        Inventory inventory = request.getInventory();

        Map<String, String> map = new HashMap<>();
        System.out.println("Stock level: " + inventory.getStockLevel());

        if (!serviceClass.validateProductId(product.getId())) {
            map.put("message", "Id " + product.getId() + " not present in database");
            return map;
        }

        productRepository.save(product);
        map.put("message", "Successfully update product with Id " + product.getId());

        if (inventory != null) {
            try {
                Inventory result = serviceClass.getInventoryId(inventory);
                if (result != null) {
                    inventory.setId(result.getId());
                    inventoryRepository.save(inventory);
                } else {
                    map.put("message", "No data available for this product or store Id");
                    return map;
                }
            } catch (DataIntegrityViolationException dive) {
                map.put("message", "Error: " + dive);
                System.out.println(dive);
                return map;
            } catch (Exception e) {
                map.put("message", "Error: " + e);
                System.out.println(e);
                return map;
            }
        }
        return map;
    }

    // save inventory
    @PostMapping
    public Map<String, String> saveInventory(@RequestBody Inventory inventory) {
        Map<String, String> map = new HashMap<>();
        try {
            if (serviceClass.validateInventory(inventory)) {
                inventoryRepository.save(inventory);
            } else {
                map.put("message", "Data already exist in inventory");
                return map;
            }
        } catch (DataIntegrityViolationException dive) {
            map.put("message", "Error: " + dive);
            System.out.println(dive);
            return map;
        } catch (Exception e) {
            map.put("message", "Error: " + e);
            System.out.println(e);
            return map;
        }
        map.put("message", "Product successfully added to inventory");
        return map;
    }

    // get all products
    @GetMapping("/{storeId}")
    public Map<String, Object> getAllProducts(@PathVariable Long storeId) {
        Map<String, Object> map = new HashMap<>();
        List<Product> result = productRepository.findProductsByStoreId(storeId);
        map.put("products", result);
        return map;
    }

    // get product name
    @GetMapping("/filter/{category}/{name}/{storeId}")
    public Map<String, Object> getProductName(@PathVariable String category, @PathVariable String name, @PathVariable Long storeId) {
        Map<String, Object> map = new HashMap<>();

        if (category.equals("null")) {
            map.put("product", productRepository.findByNameLike(storeId, name));
            return map;
        } else if (name.equals("null")) {
            System.out.println("Name is null");
            map.put("product", productRepository.findByCategoryAndStoreId(storeId, category));
            return map;
        }
        map.put("product", productRepository.findByNameAndCategory(storeId, name, category));
        return map;
    }

    // search product
    @GetMapping("/search/{name}/{storeId}")
    public Map<String, Object> searchProduct(@PathVariable String name, @PathVariable Long storeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("product", productRepository.findByNameLike(storeId, name));
        return map;
    }

    // remove product
    @DeleteMapping("/{id}")
    public Map<String, String> removeProduct(@PathVariable Long id) {
        Map<String, String> map = new HashMap<>();

        if (!serviceClass.validateProductId(id)) {
            map.put("message", "Id " + id + " not present in database");
            return map;
        }
        inventoryRepository.deleteByProductId(id);
        map.put("message", "Product deleted successfully");
        return map;
    }

    // validate quantity
    @GetMapping("/validate/{quantity}/{storeId}/{productId}")
    public boolean validateQuantity(@PathVariable Integer quantity, @PathVariable Long storeId, @PathVariable Long productId) {
        Inventory result = inventoryRepository.findByProductIdandStoreId(productId, storeId);
        return result.getStockLevel() >= quantity;
    }

}