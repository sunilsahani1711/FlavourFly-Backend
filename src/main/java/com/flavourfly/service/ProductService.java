package com.flavourfly.service;

import com.flavourfly.dto.ProductDto;
import com.flavourfly.dto.ProductDto.PriceDto;
import com.flavourfly.entity.Product;
import com.flavourfly.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository products;

    public ProductService(ProductRepository products) {
        this.products = products;
    }

    // Return all products as DTOs
    public List<ProductDto> list() {
        return products.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Entity → DTO conversion
    private ProductDto toDto(Product p) {
        PriceDto price = PriceDto.builder()
                .org(p.getPriceOrg() != null ? p.getPriceOrg().doubleValue() : null)
                .mrp(p.getPriceMrp() != null ? p.getPriceMrp().doubleValue() : null)
                .off(p.getPriceOff())
                .build();

        return ProductDto.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(price)
                .categories(p.getCategories().stream().map(c -> c.getName()).toList())
                .ingredients(p.getIngredients().stream().map(i -> i.getName()).toList())
                .build();
    }
}
