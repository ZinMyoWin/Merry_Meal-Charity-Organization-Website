package com.logiclegends.MerryMeal.controller;


import com.logiclegends.MerryMeal.entities.Order;
import com.logiclegends.MerryMeal.service.OrderService;
import com.logiclegends.MerryMeal.service.UserPrincipal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logic")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @PostMapping("/{mealId}")
    public ResponseEntity<String> createOrder(@PathVariable Long mealId, @RequestBody Order orderDetails, @CurrentUser UserPrincipal userPrincipal) {
        Order order = orderService.createOrder(mealId, orderDetails, userPrincipal.getId());
        String mealType= "Hot";
        
        if(order.getMealType()== mealType) {
        	return ResponseEntity.ok("{\"message\":\"We will provide you Hot Meal\"}");
        }else {
        	return ResponseEntity.ok("{\"message\":\"We will provide you Fronzen Meal\"}");
        }
    }
    
    @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
    @GetMapping("/retriveOrderToVolunteers")
    public List<Order> retriveOrderToVolunteers(){
    	return orderService.retriveOrderToVolunteers();
    }
    
    @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
    @GetMapping("/deliveringorder")
    public List<Order> retriveOrderToSpecitficVolunteers(@CurrentUser UserPrincipal userPrincipal){
    	return orderService.retriveOrderToSpecitficVolunteers(userPrincipal.getId());
    }
    
    @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
    @PostMapping("/takeOrder/{orderId}")
    public ResponseEntity<String> takeOrder(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long orderId){
		orderService.takeOrder(userPrincipal.getId(), orderId);
    	return ResponseEntity.ok("You has took the order");
    	
    }
    
    @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
    @PostMapping("/deliveryFinished/{orderId}")
    public ResponseEntity<String> deliveryFinished(@PathVariable Long orderId){
		orderService.deliveryFinished(orderId);
    	return ResponseEntity.ok("Order has been sucuessfully delivered");
    }
    
}

