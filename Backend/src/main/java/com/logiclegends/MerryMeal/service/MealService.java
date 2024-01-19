package com.logiclegends.MerryMeal.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.logiclegends.MerryMeal.entities.Meal;

@Service
public interface MealService {

	public Meal saveMeal(Long partnerId, Meal meal, MultipartFile file) throws IOException;
	
//	public Meal saveMeal(Long partnerId, Meal meal) throws IOException;
	
	public List<Meal> retrieveMealToAdmin();
	public List<Meal> retrieveMealToMember();
	
	public void approveMeal(Long id);
	public void rejectMeal(Long id);


}
