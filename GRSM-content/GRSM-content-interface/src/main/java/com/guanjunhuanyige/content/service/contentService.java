package com.guanjunhuanyige.content.service;

import java.util.List;

import com.guanjunhuanyige.pojo.TbContent;
import com.guanjunhuanyige.utils.TaojinResult;

public interface contentService {

	TaojinResult addContent(TbContent content);
	
	List<TbContent> getContentListByCid(long cid);
	
}
