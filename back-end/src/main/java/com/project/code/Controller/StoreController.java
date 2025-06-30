package com.project.code.Controller;


import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    // add new store
    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {
        Map<String, String> map = new HashMap<>();
        Store savedStore = storeRepository.save(store);
        map.put("message", "Store added successfully with Id " + savedStore.getId());
        return map;
    }

    // validate store exists
    @GetMapping("/validate/{storeId}")
    public boolean validateStore(@PathVariable Long storeId) {
        Store store = storeRepository.findByid(storeId);
        return store != null;
    }

    // place order
    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequest) {
        Map<String, String> map = new HashMap<>();
        try {
            orderService.saveOrder(placeOrderRequest);
            map.put("message", "Order placed successfully");
        } catch (Error e) {
            map.put("Error", "" + e);
        }
        return map;
    }

}