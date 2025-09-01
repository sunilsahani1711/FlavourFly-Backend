package com.flavourfly.service;

import com.flavourfly.entity.Address;
import com.flavourfly.repository.AddressRepository;
import com.flavourfly.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
  private final AddressRepository repo; private final UserRepository users;
  public AddressService(AddressRepository repo, UserRepository users) { this.repo = repo; this.users = users; }
  private com.flavourfly.entity.User me(Authentication a) { return users.findByEmail(a.getName()).orElseThrow(); }
  public List<Address> list(Authentication a) { return repo.findByUser(me(a)); }
  public Address save(Authentication a, Address addr) { addr.setUser(me(a)); return repo.save(addr); }
  public void delete(Authentication a, Long id) { repo.deleteById(id); }
}
