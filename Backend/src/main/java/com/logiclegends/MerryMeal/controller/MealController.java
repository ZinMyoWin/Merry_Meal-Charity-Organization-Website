package com.logiclegends.MerryMeal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.logiclegends.MerryMeal.entities.Meal;
import com.logiclegends.MerryMeal.service.MealServiceImpl;
import com.logiclegends.MerryMeal.service.UserPrincipal;

@RestController
@RequestMapping("/logic")
public class MealController {

	@Autowired
	private MealServiceImpl mealService;

	//Meal Registration 
// 	@PreAuthorize("hasRole('ROLE_PARTNER')")
    @PostMapping("/registerMeal")
    public ResponseEntity<Meal> registerMeal(@CurrentUser UserPrincipal userPrincipal, 
    		@RequestParam("name") String name, 
    		@RequestParam("mealDescription") String description, 
    		@RequestParam("ingredient") String ingredient, 
    		@RequestParam("mealImg") MultipartFile file, 
    		@RequestParam("price") Long price) {
    	
    	System.out.println("name:" + name);
    	System.out.println("des:" + description);
    	System.out.println("ing:" + ingredient);
    	System.out.println("price:" + price);
    	System.out.println("UserPrincipal; "+ userPrincipal.getUsername());
 		
        Meal registerMeal = new Meal();
        registerMeal.setName(name);
        registerMeal.setMealDescription(description);
        registerMeal.setIngredient(ingredient);
        registerMeal.setPrice(price);
        
        try {
			mealService.saveMeal(userPrincipal.getId(), registerMeal, file);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
 	
//	@PreAuthorize("hasRole('ROLE_PARTNER')")
//    @PostMapping("/registerMeal")
//    public ResponseEntity<Meal> registerMeal(@CurrentUser UserPrincipal userPrincipal, @RequestParam("name") String name, @RequestParam("mealDescription") String description, @RequestParam("ingredient") String ingredient, @RequestParam("price") Long price) {
// 		
// 		
//        Meal registerMeal = new Meal();
//        registerMeal.setName(name);
//        registerMeal.setMealDescription(description);
//        registerMeal.setIngredient(ingredient);
//        registerMeal.setPrice(price);
//        
//        System.out.println(userPrincipal.getId()+"partner Id");
//        try {
//			mealService.saveMeal(userPrincipal.getId(), registerMeal);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
	
 	@PreAuthorize("hasRole('ROLE_ADMIN')")
 	@PostMapping("/approveMeal/{id}")
 	public ResponseEntity<String> approveMeal(@PathVariable Long id){
 		
 		mealService.approveMeal(id);
		return ResponseEntity.ok("Meal Approved");
 		
 	}
 	@PreAuthorize("hasRole('ROLE_ADMIN')")
 	@PostMapping("/rejectMeal/{id}")
 	public ResponseEntity<String> rejectMeal(@PathVariable Long id){
 		
 		mealService.rejectMeal(id);
 		
 		return ResponseEntity.ok("Meal Rejected");
 		
 	}
 	
 	@PreAuthorize("hasRole('ROLE_ADMIN')")
 	@GetMapping("/retrieveMealToAdmin")
 	public List<Meal> retrieveMealToAdmin(){
 		
 		return mealService.retrieveMealToAdmin();
 	}
 	
 	@PreAuthorize("hasRole('ROLE_MEMBER')")
 	@GetMapping("/retrieveMealToMember")
 	public List<Meal> retrieveMealToMember(){
 		
 		return mealService.retrieveMealToMember();
 	}
 		
 	
}
