package com.logiclegends.MerryMeal.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.logiclegends.MerryMeal.entities.Meal;
import com.logiclegends.MerryMeal.entities.PartnerEntity;
import com.logiclegends.MerryMeal.repository.MealRepo;
import com.logiclegends.MerryMeal.repository.PartnerRepo;

@Service
public class MealServiceImpl implements MealService{

	@Autowired
	private PartnerRepo partnerRepo;
	
	@Autowired
	private MealRepo mealRepo;
	
	@Autowired
	private EmailService emailService;
	
	
	@Override
	public Meal saveMeal(Long partnerId, Meal meal, MultipartFile file) throws IOException {
		PartnerEntity partner = partnerRepo.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
		
		meal.setPartnerEntity(partner);
		
		meal.setStatus("pending");
		
		if (!file.isEmpty()) {
            meal.setMealImg(file.getBytes());
        }
		
		return mealRepo.save(meal);
	}

//	@Override
//	public Meal saveMeal(Long partnerId, Meal meal) throws IOException {
//		PartnerEntity partner = partnerRepo.findById(partnerId)
//                .orElseThrow(() -> new RuntimeException("Partner not found"));
//		
//		meal.setPartnerEntity(partner);
//		
//		meal.setStatus("pending");
//		
//		
//		return mealRepo.save(meal);
//	}
	@Override
	public List<Meal> retrieveMealToAdmin() {	
		return mealRepo.findByStatus("pending");
//		return mealRepo.searchMealByPending();
//		return mealRepo.findAll();
	}

	
	@Override
	public List<Meal> retrieveMealToMember() {
		return mealRepo.findByStatus("approved");
	}


	@Override
	public void approveMeal(Long id) {
		Meal meal = mealRepo.findById(id).get();
		meal.setStatus("approved");
		
		mealRepo.save(meal);
		
		String partnerEmail = meal.getPartnerEntity().getPartnerEmail();
		String subject = "Approval Confirmation";
        String text = "Your meal registration request has been approved. Thank you for providing healthy meal.";
        
        emailService.sendEmail(partnerEmail,subject, text);
	}

	@Override
	public void rejectMeal(Long id) {
		Meal meal = mealRepo.findById(id).get();
		String partnerEmail = meal.getPartnerEntity().getPartnerEmail();
		String subject = "Meal Rejection";
        String text = "Your meal registration request has been rejected. Please provide healthy meal";
        
        emailService.sendEmail(partnerEmail,subject, text);
		mealRepo.deleteById(id);
	}

}
