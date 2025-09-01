package com.flavourfly.dto;

import com.flavourfly.entity.Product;
import com.flavourfly.entity.Category;
import com.flavourfly.entity.Ingredient;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private PriceDto price;              // nested object
    private List<String> categories;
    private List<String> ingredients;

    // --------- Nested Price DTO ----------
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PriceDto {
        private Double org;
        private Double mrp;
        private Integer off;
    }

    // --------- Entity → DTO ----------
    public static ProductDto fromEntity(Product p) {
        return ProductDto.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(
                    PriceDto.builder()
                        .org(p.getPriceOrg() != null ? p.getPriceOrg().doubleValue() : null)
                        .mrp(p.getPriceMrp() != null ? p.getPriceMrp().doubleValue() : null)
                        .off(p.getPriceOff())
                        .build()
                )
                .categories(
                    p.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toList())
                )
                .ingredients(
                    p.getIngredients().stream()
                        .map(Ingredient::getName)
                        .collect(Collectors.toList())
                )
                .build();
    }

    // --------- DTO → Entity ----------
    public Product toEntity() {
        Product p = new Product();
        p.setId(this.id);
        p.setName(this.name);
        p.setDescription(this.description);

        if (this.price != null) {
            if (this.price.getOrg() != null) {
                p.setPriceOrg(java.math.BigDecimal.valueOf(this.price.getOrg()));
            }
            if (this.price.getMrp() != null) {
                p.setPriceMrp(java.math.BigDecimal.valueOf(this.price.getMrp()));
            }
            p.setPriceOff(this.price.getOff());
        }

        // categories & ingredients should be mapped in Service layer
        // by fetching them from DB
        p.setCategories(new java.util.HashSet<>());
        p.setIngredients(new java.util.HashSet<>());

        return p;
    }
}
