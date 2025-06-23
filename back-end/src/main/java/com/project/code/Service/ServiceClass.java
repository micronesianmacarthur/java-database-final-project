package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class ServiceClass {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // validate inventory exists
    public boolean validateInventory(Inventory inventory) {
        Inventory result = inventoryRepository.findByProductIdandStoreId(
                inventory.getProduct().getId(),
                inventory.getStore().getId()
        );
        return result == null;
    }

    // validate product name exists
    public boolean validateProduct(Product product) {
        Product result = productRepository.findByName(product.getName());
        return result == null;
    }

    // validate product id
    public boolean validateProductId(Long id) {
        Product result = productRepository.findByid(id);
        return result != null;
    }

    // get inventory id
    public Inventory getInventoryId(Inventory inventory) {
        return inventoryRepository.findByProductIdandStoreId(
                inventory.getProduct().getId(),
                inventory.getStore().getId()
        );
    }

}