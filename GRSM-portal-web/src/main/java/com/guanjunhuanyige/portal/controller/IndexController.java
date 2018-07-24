package com.guanjunhuanyige.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guanjunhuanyige.content.service.contentService;
import com.guanjunhuanyige.pojo.TbContent;

/**
 * 首页
 * @author 落雪封尘
 *
 */
@Controller
public class IndexController {
	
	@Value("${CONTENT_ID}")
	private Long CONTENT_ID;
	
	@Autowired
	private contentService cs;

	@RequestMapping("/index")
	public String showIndexPage(Model model) {
		//查询内容列表
		List<TbContent> ad1List = cs.getContentListByCid(CONTENT_ID);
		model.addAttribute("ad1List",ad1List);
		return "index";
	}
	
}
