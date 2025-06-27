package com.ecommerce.Order.service;



import com.ecommerce.Order.clients.ProductServiceClient;
import com.ecommerce.Order.dto.CartItemRequest;
import com.ecommerce.Order.dto.ProductResponse;
import com.ecommerce.Order.model.CartItem;
import com.ecommerce.Order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {
//    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;

    public Boolean addToCart(String userId, CartItemRequest cartItemRequest) {

       ProductResponse searchedProduct =  productServiceClient.getDetailsOfAPrroduct(cartItemRequest.getProductId())

       if(searchedProduct==null)
           return false;

       if(searchedProduct.getStockQuantity() < cartItemRequest.getQuantity())
           return false;


//       Optional<User> useropt = userRepository.findById(Long.valueOf(userId));
//       if(useropt.isEmpty())
//           return false;
//
//       User user = useropt.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(String.valueOf(userId), String.valueOf(cartItemRequest.getProductId()));
        if(existingCartItem!=null)
        {
            existingCartItem.setQuantity(cartItemRequest.getQuantity() + existingCartItem.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        }
        else {

            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000));
            cartItemRepository.save(cartItem);
        }
        return true;


    }

    public Boolean deleteItemFromCart(String userId, Long productId) {
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, String.valueOf(productId));
        if(item!=null)
        {
            cartItemRepository.delete(item);
            return true;
        }
        return false;


    }

    public List<CartItem> getCartItemsForUser(String userId) {
        return cartItemRepository.findByUserId(userId);

    }

    public void ClearCart(String userID) {

        cartItemRepository.deleteByUserId(userID);
    }
}
