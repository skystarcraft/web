package com.guanjunhuanyige.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guanjunhuanyige.SearchResult;
import com.guanjunhuanyige.search.service.SearchService;

/**
 * 商品搜索
 * @author 落雪封尘
 *
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;
	
	@RequestMapping("/search")
	public String searchItemList(String keyword, @RequestParam(defaultValue="1") Integer page,Model model) throws Exception {
		keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
		SearchResult result = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
		model.addAttribute("query",keyword);
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", result.getRecordCount());
		model.addAttribute("itemList", result.getItemList());
		return "search";
	}
	
}
