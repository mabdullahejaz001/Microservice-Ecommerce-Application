package com.ecommerce.Order.controller;

import com.ecommerce.Order.dto.CartItemRequest;
import com.ecommerce.Order.model.CartItem;
import com.ecommerce.Order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-USER-ID") String userId,
                                          @RequestBody CartItemRequest cartRequest) {
        if(!cartService.addToCart(userId, cartRequest)) {
            return ResponseEntity.badRequest().body("Product/User not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-USER-ID") String userId
            ,@PathVariable Long productId){

        Boolean isDeleted = cartService.deleteItemFromCart(userId, productId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().body("Invalid Request");

    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItemsForAUser(@RequestHeader("X-USER-ID") String userId)
    {
        List<CartItem> cartItem = cartService.getCartItemsForUser(userId);
        if (cartItem.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartItem);


}

}
