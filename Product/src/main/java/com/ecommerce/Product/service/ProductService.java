package com.ecommerce.Product.service;

import com.ecommerce.Product.dto.ProductRequest;
import com.ecommerce.Product.dto.ProductResponse;
import com.ecommerce.Product.model.Product;
import com.ecommerce.Product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        makeProductFromProductRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapProductToProductResponse(savedProduct);
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        response.setActive(product.getActive());
        return response;

    }

    private void makeProductFromProductRequest(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
    }

    public Optional<ProductResponse> updateProduct(ProductRequest productRequest, Long id) {
        return productRepository.findById(id)
                .map(searchedUser -> { makeProductFromProductRequest(searchedUser,productRequest);
                    Product savedproduct = productRepository.save(searchedUser);
                    return mapProductToProductResponse(savedproduct);
                    });
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapProductToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(foundedproduct -> {foundedproduct.setActive(false);
                productRepository.save(foundedproduct);
                return true;}).orElse(false);
    }

    public List<ProductResponse> searchAProduct(String keyword) {

        return productRepository.searchProduct(keyword).stream().map(this::mapProductToProductResponse).collect(Collectors.toList());

    }

    public Optional<ProductResponse> getProductById(Long id) {
        return productRepository.findByIdAndActiveTrue(id).map(this::mapProductToProductResponse);
    }
}
