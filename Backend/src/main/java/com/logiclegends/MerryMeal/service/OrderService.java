package com.logiclegends.MerryMeal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiclegends.MerryMeal.entities.Meal;
import com.logiclegends.MerryMeal.entities.MemberEntity;
import com.logiclegends.MerryMeal.entities.Order;
import com.logiclegends.MerryMeal.entities.PartnerEntity;
import com.logiclegends.MerryMeal.entities.VolunteerEntity;
import com.logiclegends.MerryMeal.repository.MealRepo;
import com.logiclegends.MerryMeal.repository.MemberRepo;
import com.logiclegends.MerryMeal.repository.OrderRepository;
import com.logiclegends.MerryMeal.repository.PartnerRepo;
import com.logiclegends.MerryMeal.repository.VolunteerRepo;

@Service
public class OrderService {

    @Autowired
    private MealRepo mealRepository;
    
    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private PartnerRepo partnerRepo;
    
    
    @Autowired
    private VolunteerRepo volunteerRepo;

    public Order createOrder(Long mealId, Order orderDetails, Long memberId) {
    	MemberEntity memberEntity = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    	
    	Meal meal = mealRepository.findById(mealId)
                                  .orElseThrow(() -> new RuntimeException("Meal not found"));
        
        PartnerEntity partnerEntity = meal.getPartnerEntity();
        
        double distance = calculateDistance(partnerEntity.getLatitude(),partnerEntity.getLongitude(),orderDetails.getUserLatitude(),orderDetails.getUserLongitude());
        
        String mealType;
        if (distance > 10 || orderDetails.getDay().equalsIgnoreCase("Saturday") || orderDetails.getDay().equalsIgnoreCase("Sunday")) {
            mealType = "Frozen";
        } else {
            mealType = "Hot";
        }
        orderDetails.setMealType(mealType);
        orderDetails.setMealId(meal.getId());
        orderDetails.setMemberId(memberEntity.getMemberId());
        orderDetails.setMeal(meal);
        orderDetails.setMember(memberEntity);
        orderDetails.setStatus("To Deliver");
        
        memberEntity.setOrderHistory(memberEntity.getOrderHistory()+1);
        memberRepo.save(memberEntity);
        
        return orderRepository.save(orderDetails);
    }
    
    private static final double EARTH_RADIUS = 6371; // Earth radius in kilometers

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
    
    
    
    public List<Order> retriveOrderToVolunteers(){

		return orderRepository.findByStatus("To Deliver");
    	
    }
    
    
    public List<Order> retriveOrderToSpecitficVolunteers(Long id){

    	List<Order> order = orderRepository.findByVolunteerId(id);
    	
		return order;
    	
    }

    public Order takeOrder(Long id, Long order_id) {
    	VolunteerEntity volunteer = volunteerRepo.findById(id)
    			.orElseThrow(() -> new RuntimeException("Volunteer not found"));
    	
    	Order order = orderRepository.findById(order_id)
    			.orElseThrow(() -> new RuntimeException("Order not found"));
    	
    	order.setVolunteer(volunteer);
    	order.setStatus("Delivering");
    	orderRepository.save(order);
    	
    	return null;
    	
    }
    
    public Order deliveryFinished(Long orderId) {
    	    	
    	Order order = orderRepository.findById(orderId)
    			.orElseThrow(() -> new RuntimeException("Order not found"));
    	
    	order.setStatus("Delivered");
    	orderRepository.save(order);
		/* orderRepository.deleteById(order.getId()); */
		return null;
    }
}

