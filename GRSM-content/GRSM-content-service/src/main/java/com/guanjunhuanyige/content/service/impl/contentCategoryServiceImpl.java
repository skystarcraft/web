package com.guanjunhuanyige.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guanjunhuanyige.EasyUITreeNode;
import com.guanjunhuanyige.content.service.contentCategoryService;
import com.guanjunhuanyige.mapper.TbContentCategoryMapper;
import com.guanjunhuanyige.pojo.TbContentCategory;
import com.guanjunhuanyige.pojo.TbContentCategoryExample;
import com.guanjunhuanyige.pojo.TbContentCategoryExample.Criteria;
import com.guanjunhuanyige.utils.TaojinResult;

@Service
public class contentCategoryServiceImpl implements contentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCateoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<TbContentCategory> catList = contentCateoryMapper.selectByExample(example);
		List<EasyUITreeNode> list = new ArrayList<>();
		for (TbContentCategory tb : catList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tb.getId());
			node.setText(tb.getName());
			node.setState(tb.getIsParent() ? "closed" : "open");
			list.add(node);
		}
		return list;
	}

	@Override
	public TaojinResult addContentCategory(long parentId, String name) {
		TbContentCategory tb = new TbContentCategory();
		tb.setParentId(parentId);
		tb.setName(name);
		tb.setStatus(1); //1(正常)
		tb.setSortOrder(1);
		tb.setIsParent(false);
		tb.setCreated(new Date());
		tb.setUpdated(new Date());
		contentCateoryMapper.insert(tb);
		TbContentCategory parent =  contentCateoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			contentCateoryMapper.updateByPrimaryKey(parent);
		}
		return TaojinResult.ok(tb);
	}

}
