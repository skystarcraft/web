package com.guanjunhuanyige.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.search.service.SearchItemService;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 导入商品到索引库
 * @author 落雪封尘
 *
 */
@Controller
public class SearchItemController {

	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public TaojinResult importItemList() {
		TaojinResult result = searchItemService.importAllItems();
		return result;
	}
	
	
}
