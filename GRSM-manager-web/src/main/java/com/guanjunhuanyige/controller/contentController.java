package com.guanjunhuanyige.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.content.service.contentService;
import com.guanjunhuanyige.pojo.TbContent;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 内容管理
 * @author 落雪封尘
 *
 */
@Controller
public class contentController {
	
	@Autowired
	private contentService cs;
	
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult addContent(TbContent content) {
		TaojinResult result = cs.addContent(content);
		return result;
	}
	
}
