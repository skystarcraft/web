package com.guanjunhuanyige.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guanjunhuanyige.EasyUITreeNode;
import com.guanjunhuanyige.mapper.TbItemCatMapper;
import com.guanjunhuanyige.pojo.TbItemCat;
import com.guanjunhuanyige.pojo.TbItemCatExample;
import com.guanjunhuanyige.pojo.TbItemCatExample.Criteria;
import com.guanjunhuanyige.service.ItemCatService;

/**
 * 商品分类管理
 * @author 落雪封尘
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatlist(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tb : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tb.getId());
			node.setText(tb.getName());
			node.setState(tb.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		return resultList;
	}

}
