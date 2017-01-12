package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller /* @Controller只能修饰类,表示该类是一个控制器类,用一解析用户请求的URI */
public class HelloController {
	@Autowired
	private Twitter twitter;

	@RequestMapping("/")
	public String hello(@RequestParam(defaultValue = "subway") String search, Model model) {
		SearchResults searchResults = twitter.searchOperations().search(search);
		List<String> tweets = searchResults.getTweets().stream().map(Tweet::getText).collect(Collectors.toList());
		model.addAttribute("tweets", tweets);
		return "resultPage";
	}
}
