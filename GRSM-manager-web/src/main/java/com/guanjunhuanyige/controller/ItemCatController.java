package com.guanjunhuanyige.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.EasyUITreeNode;
import com.guanjunhuanyige.service.ItemCatService;

/**
 * 商品分类管理
 * @author 落雪封尘
 *
 */
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id",defaultValue="0") Long parentId){
		
		List<EasyUITreeNode> itemCatList = itemCatService.getItemCatlist(parentId);
		return itemCatList;
	}
	
}
