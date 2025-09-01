package com.flavourfly.repository;

import com.flavourfly.entity.Favourite;
import com.flavourfly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
  List<Favourite> findByUser(User user);
  Optional<Favourite> findByUserAndProductId(User user, Long productId);
}
