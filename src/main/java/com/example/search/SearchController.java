package com.example.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
	private SearchService searchService;

	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@RequestMapping("/search/{searchType}")
	public ModelAndView search(@PathVariable String searchType, @MatrixVariable List<String> keywords) {
		List<LightTweet> lightTweet = searchService.search(searchType, keywords);
		ModelAndView modelAndView = new ModelAndView("resultPage");
		modelAndView.addObject("tweets", lightTweet);
		modelAndView.addObject("search", String.join(",", keywords));
		return modelAndView;
	}
}
