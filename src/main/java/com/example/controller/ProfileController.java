package com.example.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.date.USLocalDateFormatter;
import com.example.doman.ProfileForm;

@Controller
public class ProfileController {
	
	@ModelAttribute("profileForm")
	@RequestMapping("/profile")
	public String displayProfile() {
		return "profile/profilePage";
	}

//	@RequestMapping(value = "/profile", method = RequestMethod.POST)
//	public String saveProfile(ProfileForm profileForm) {
//		System.out.println("save ok" + profileForm);
//		return "redirect:/profile";
//	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "profile/profilePage";
		}
		System.out.println("save ok" + profileForm);
		return "redirect:/profile";
	}
	
	@ModelAttribute("dateFormat")
	public String localeFormat(Locale locale) {
		return USLocalDateFormatter.getPattern(locale);
	}
}
