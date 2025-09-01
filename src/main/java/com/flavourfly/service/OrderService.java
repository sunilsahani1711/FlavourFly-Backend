package com.flavourfly.service;

import com.flavourfly.dto.PlaceOrderRequest;
import com.flavourfly.entity.Order;
import com.flavourfly.entity.OrderItem;
import com.flavourfly.entity.User;
import com.flavourfly.repository.AddressRepository;
import com.flavourfly.repository.CartItemRepository;
import com.flavourfly.repository.OrderRepository;
import com.flavourfly.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
  private final OrderRepository orders; private final CartItemRepository cart; private final AddressRepository addresses; private final UserRepository users;
  public OrderService(OrderRepository orders, CartItemRepository cart, AddressRepository addresses, UserRepository users) {
    this.orders = orders; this.cart = cart; this.addresses = addresses; this.users = users;
  }

  private User me(Authentication a) { return users.findByEmail(a.getName()).orElseThrow(); }

  public List<Order> list(Authentication a) { return orders.findByUser(me(a)); }

  public Order place(Authentication a, PlaceOrderRequest req) {
    var user = me(a);
    var cartItems = cart.findByUser(user);
    if (cartItems.isEmpty()) throw new RuntimeException("Cart is empty");

    var order = new Order(); order.setUser(user); order.setPaymentMethod(null);
    var d = req.getDeliveryDetails();
    order.setDeliveryName((d.getFirstName()+" "+d.getLastName()).trim());
    order.setDeliveryStreet(d.getAddressLine1()); order.setDeliveryCity(d.getCity()); order.setDeliveryState(d.getState()); order.setDeliveryZip(d.getZip()); order.setDeliveryPhone(d.getPhoneNumber()); order.setDeliveryEmail(d.getEmailAddress());

    BigDecimal total = BigDecimal.ZERO;
    for (var ci : cartItems) {
      var oi = new OrderItem(); oi.setOrder(order); oi.setProduct(ci.getProduct()); oi.setQuantity(ci.getQuantity());
      oi.setUnitPrice(ci.getProduct().getPriceOrg()); oi.setLineTotal(ci.getProduct().getPriceOrg().multiply(java.math.BigDecimal.valueOf(ci.getQuantity())));
      total = total.add(oi.getLineTotal()); order.getItems().add(oi);
    }
    order.setTotalAmount(total);

    orders.save(order);
    cartItems.forEach(ci -> cart.delete(ci));
    return order;
  }
}
