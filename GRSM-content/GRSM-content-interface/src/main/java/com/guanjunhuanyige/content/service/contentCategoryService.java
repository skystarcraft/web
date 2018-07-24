package com.guanjunhuanyige.content.service;

import java.util.List;

import com.guanjunhuanyige.EasyUITreeNode;
import com.guanjunhuanyige.utils.TaojinResult;

public interface contentCategoryService {

	List<EasyUITreeNode> getContentCatList(long parentId);
	
	TaojinResult addContentCategory(long parentId, String name);
	
}
