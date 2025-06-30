package com.project.code.Controller;


import com.project.code.Model.Customer;
import com.project.code.Model.Review;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable Long storeId, @PathVariable Long productId) {
        Map<String, Object> map = new HashMap<>();
        List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);
        List<Map<String, Object>> reviewsWithCustomerNames = new ArrayList<>();

        for (Review review : reviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("review", review.getComment());
            reviewMap.put("rating", review.getRating());

            Customer customer = customerRepository.findByid(review.getCustomerId());
            if (customer != null) {
                reviewMap.put("customerName", customer.getName());
            } else {
                reviewMap.put("customerName", "Unknown");
            }
            reviewsWithCustomerNames.add(reviewMap);
        }
        map.put("reviews", reviewsWithCustomerNames);
        return map;
    }

    // get all reviews
    @GetMapping
    public Map<String, Object> getAllReviews() {
        Map<String, Object> map = new HashMap<>();
        map.put("reviews", reviewRepository.findAll());
        return map;
    }

}