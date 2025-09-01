package com.flavourfly.service;

import com.flavourfly.entity.Favourite;
import com.flavourfly.repository.FavouriteRepository;
import com.flavourfly.repository.ProductRepository;
import com.flavourfly.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteService {
  private final FavouriteRepository fav; private final ProductRepository products; private final UserRepository users;
  public FavouriteService(FavouriteRepository fav, ProductRepository products, UserRepository users) { this.fav=fav; this.products=products; this.users=users; }

  private com.flavourfly.entity.User me(Authentication a) { return users.findByEmail(a.getName()).orElseThrow(); }

  public List<Favourite> list(Authentication a) { return fav.findByUser(me(a)); }

  public List<Favourite> add(Authentication a, Long productId) {
    var u = me(a); fav.findByUserAndProductId(u, productId).ifPresent(x-> { throw new RuntimeException("Already added"); });
    var f = new Favourite(); f.setUser(u); f.setProduct(products.findById(productId).orElseThrow());
    fav.save(f); return fav.findByUser(u);
  }

  public List<Favourite> remove(Authentication a, Long productId) {
    var u = me(a); fav.findByUserAndProductId(u, productId).ifPresent(fav::delete);
    return fav.findByUser(u);
  }
}
