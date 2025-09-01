package com.flavourfly.repository;

import com.flavourfly.entity.CartItem;
import com.flavourfly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findByUser(User user);
  Optional<CartItem> findByUserAndProductId(User user, Long productId);
}
