package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cart")
    public String viewCart(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        List<Order> orders = orderRepository.findAllByUser(user);
        model.addAttribute("orders", orders);
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Order order = new Order();
        order.setUser(user);
        // Здесь добавьте продукты в заказ, возможно из сессии
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        List<Order> orders = orderRepository.findAllByUser(user);
        model.addAttribute("orders", orders);
        return "orders";
    }
}
