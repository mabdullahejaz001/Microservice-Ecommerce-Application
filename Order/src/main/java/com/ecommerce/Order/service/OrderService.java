package com.ecommerce.Order.service;

import com.ecommerce.Order.dto.OrderItemDTO;
import com.ecommerce.Order.dto.OrderResponse;
import com.ecommerce.Order.model.CartItem;
import com.ecommerce.Order.model.Order;
import com.ecommerce.Order.model.OrderItem;
import com.ecommerce.Order.model.OrderStatus;
import com.ecommerce.Order.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
//    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrderFromOrderResponse(String userID) {
        List<CartItem> cartItemList = cartService.getCartItemsForUser(userID);
        if(cartItemList.isEmpty()){
            return Optional.empty();

        }

//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userID));
//        if(userOptional.isEmpty()){
//            return Optional.empty();
//        }
//        User user = userOptional.get();

       BigDecimal totalPrice = cartItemList.stream()
               .map(CartItem::getPrice)
               .reduce(BigDecimal.ZERO,BigDecimal::add);

        Order order  = new Order();
        order.setUserId(userID);
        order.setTotalAmount(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItem = cartItemList.stream()
                        .map(item -> new OrderItem(
                                null,
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice(),
                                order
                                ))
                .toList();

        order.setOrderItemsList(orderItem);

        Order savedOrder  = orderRepository.save(order);

        cartService.ClearCart(String.valueOf(userID));

        return Optional.of(mapToOrderResponse(savedOrder));


    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                savedOrder.getOrderItemsList().stream()
                        .map(item -> new OrderItemDTO(
                                item.getId(),
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                        ) )
                        .toList(),
                savedOrder.getCreatedAt()
                );
    }
}
