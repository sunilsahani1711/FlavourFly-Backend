package com.flavourfly.service;

import com.flavourfly.dto.CartMutationRequest;
import com.flavourfly.entity.CartItem;
import com.flavourfly.entity.User;
import com.flavourfly.repository.CartItemRepository;
import com.flavourfly.repository.ProductRepository;
import com.flavourfly.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
  private final CartItemRepository cart;
  private final ProductRepository products;
  private final UserRepository users;

  public CartService(CartItemRepository cart, ProductRepository products, UserRepository users) { this.cart = cart; this.products = products; this.users = users; }

  private User me(Authentication auth) { return users.findByEmail(auth.getName()).orElseThrow(); }

  public List<CartItem> list(Authentication auth) { return cart.findByUser(me(auth)); }

  public List<CartItem> add(Authentication auth, CartMutationRequest req) {
    var user = me(auth);
    var p = products.findById(req.getProductId()).orElseThrow();
    var ci = cart.findByUserAndProductId(user, req.getProductId()).orElseGet(() -> {
      var x = new CartItem(); x.setUser(user); x.setProduct(p); x.setQuantity(0); return x;
    });
    ci.setQuantity(ci.getQuantity() + (req.getQuantity() == null ? 1 : req.getQuantity()));
    cart.save(ci);
    return cart.findByUser(user);
  }

  public List<CartItem> remove(Authentication auth, CartMutationRequest req) {
    var user = me(auth);
    var ci = cart.findByUserAndProductId(user, req.getProductId()).orElseThrow();
    if (req.getQuantity() == null || ci.getQuantity() <= req.getQuantity()) cart.delete(ci);
    else { ci.setQuantity(ci.getQuantity() - req.getQuantity()); cart.save(ci); }
    return cart.findByUser(user);
  }
}
