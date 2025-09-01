package com.flavourfly.repository;

import com.flavourfly.entity.Address;
import com.flavourfly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
  List<Address> findByUser(User user);
}
