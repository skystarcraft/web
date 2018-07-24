package com.guanjunhuanyige.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 内容分类管理
 * @author 落雪封尘
 *
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.EasyUITreeNode;
import com.guanjunhuanyige.content.service.contentCategoryService;
import com.guanjunhuanyige.utils.TaojinResult;
@Controller
public class contentCateController {

	@Autowired
	public contentCategoryService cs;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id",defaultValue="0") Long parentId){
		
		List<EasyUITreeNode> list = cs.getContentCatList(parentId);
		
		return list;
		
	}
	
	/*
	 * 添加分类节点
	 */
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult createContentCategory(Long parentId, String name) {
		TaojinResult result = cs.addContentCategory(parentId,name);
		return result;
	}
	
	/*
	 * 删除分类节点
	 */
	
	
	/*
	 * 修改分类节点
	 */
}
