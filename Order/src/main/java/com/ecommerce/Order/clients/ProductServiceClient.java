package com.ecommerce.Order.clients;

import com.ecommerce.Order.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClient {

    @GetExchange("/api/product/{id}")
    ProductResponse getDetailsOfAPrroduct(@PathVariable String id);
}
