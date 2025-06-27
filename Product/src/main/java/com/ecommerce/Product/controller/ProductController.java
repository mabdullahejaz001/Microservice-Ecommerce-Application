package com.ecommerce.Product.controller;

import com.ecommerce.Product.dto.ProductRequest;
import com.ecommerce.Product.dto.ProductResponse;
import com.ecommerce.Product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Long id) {
        return productService.updateProduct(productRequest, id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return productService.getProductById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchAProduct(@RequestParam String keyword)
    {
        return ResponseEntity.ok(productService.searchAProduct(keyword));
    }

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteAProduct(@PathVariable Long id)
{
    return productService.deleteProduct(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

}
}