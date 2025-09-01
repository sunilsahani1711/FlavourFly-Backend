package com.flavourfly.repository;

import com.flavourfly.entity.Order;
import com.flavourfly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUser(User user);
}
