package com.guanjunhuanyige.service;

import com.guanjunhuanyige.EasyUIDataGridResult;
import com.guanjunhuanyige.pojo.TbItem;
import com.guanjunhuanyige.pojo.TbItemDesc;
import com.guanjunhuanyige.utils.TaojinResult;

public interface ItemService {
	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page, int rows);
	TaojinResult addItem(TbItem item,String desc);
	TbItemDesc getItemDescById(long itemId);
}
