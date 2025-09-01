package com.flavourfly.dto;
import lombok.Data;
import java.util.Map;

@Data
public class CartMutationRequest {
  private Long productId;
  private Integer quantity; // null => remove fully
}
