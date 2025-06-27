package com.ecommerce.Order.repository;

import com.ecommerce.Order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserIdAndProductId(String userId, String productId);

    void deleteByUserIdAndProductId(String userId, String productId);

    List<CartItem> findByUserId(String userId);

    void deleteByUserId(String userId);







//    CartItem findByUserAndProduct(User user, Product product);
//
//    void deleteByUserAndProduct(User user, Product product);
//
//    List<CartItem> findByUser(User user);
//
//    void deleteByUser(User user);
}
