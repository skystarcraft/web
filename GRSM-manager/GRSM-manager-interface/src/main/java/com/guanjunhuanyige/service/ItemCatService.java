package com.guanjunhuanyige.service;

import java.util.List;

import com.guanjunhuanyige.EasyUITreeNode;

public interface ItemCatService {

	List<EasyUITreeNode> getItemCatlist(long parentId);
	
}
