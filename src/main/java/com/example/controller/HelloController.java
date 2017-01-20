package com.example.controller;

import java.util.List;
//import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.social.twitter.api.SearchResults;
//import org.springframework.social.twitter.api.Tweet;
//import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.profile.UserProfileSession;

@Controller /* @Controller只能修饰类,表示该类是一个控制器类,用一解析用户请求的URI */
public class HelloController {
	// @Autowired
	// private Twitter twitter;

	private UserProfileSession userProfileSession;

	@Autowired
	public HelloController(UserProfileSession userProfileSession) {
		this.userProfileSession = userProfileSession;
	}

	@RequestMapping("/")
	public String home() {
		List<String> tastes = userProfileSession.getTastes();
		if (tastes.isEmpty()) {
			return "redirect:/profile";
		}
		return "redirect:/search/mixed;keywords=" + String.join(",", tastes);
	}

	// @RequestMapping("/")
	// public String hello(@RequestParam(defaultValue = "subway") String search,
	// Model model) {
	// SearchResults searchResults = twitter.searchOperations().search(search);
	// List<String> tweets =
	// searchResults.getTweets().stream().map(Tweet::getText).collect(Collectors.toList());
	// model.addAttribute("tweets", tweets);
	// return "resultPage";
	// }

	// @RequestMapping("/")
	// public String home() {
	// return "searchPage";
	// }
	//
	// @RequestMapping("/result")
	// public String hello(@RequestParam(defaultValue = "subway") String search,
	// Model model) {
	// SearchResults searchResults = twitter.searchOperations().search(search);
	// List<Tweet> tweets = searchResults.getTweets();
	// model.addAttribute("tweets", tweets);
	// model.addAttribute("search", search);
	// return "resultPage";
	// }

	// @RequestMapping(value = "/postSearch", method = RequestMethod.POST)
	// public String postSearch(HttpServletRequest request, RedirectAttributes
	// redirectAttributes) {
	// String search = request.getParameter("search");
	// redirectAttributes.addAttribute("search", search);
	// return "redirect:result";
	// }

	// @RequestMapping(value = "/postSearch", method = RequestMethod.POST)
	// public String postSearch(HttpServletRequest request, RedirectAttributes
	// redirectAttributes) {
	// String search = request.getParameter("search");
	// if (search.toLowerCase().contains("struts")) {
	// redirectAttributes.addFlashAttribute("error", "Try using spring
	// instead!");
	// return "redirect:/";
	// }
	// redirectAttributes.addAttribute("search", search);
	// return "redirect:result";
	// }
}
