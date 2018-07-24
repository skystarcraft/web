package com.guanjunhuanyige.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.EasyUIDataGridResult;
import com.guanjunhuanyige.pojo.TbItem;
import com.guanjunhuanyige.service.ItemService;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 商品管理Controller
 * @author 落雪封尘
 *
 */
@Controller
public class ItemController implements Serializable{

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	/*
	 * 商品添加
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult addItem(TbItem item, String desc) {
		TaojinResult result = itemService.addItem(item, desc);
		return result;
	}
	
	
	/*
	 * 商品编辑
	 */
	@RequestMapping(value="/rest/item/update",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult editItem() {
		return null;
	}
	
	/*
	 * 商品删除
	 */
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult delItem() {
		return null;
	}
	
	/*
	 * 商品下架
	 */
	@RequestMapping(value="/rest/item/instock",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult instockItem() {
		return null;
	}
	
	/*
	 * 商品上架
	 */
	@RequestMapping(value="/rest/item/reshelf",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult reshelf() {
		return null;
	}
}
