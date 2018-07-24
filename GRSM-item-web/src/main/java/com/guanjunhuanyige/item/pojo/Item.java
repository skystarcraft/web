package com.guanjunhuanyige.item.pojo;

import com.guanjunhuanyige.pojo.TbItem;

public class Item extends TbItem {
	
	public Item(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}
	
	public String[] getImages() {
		String img2 = this.getImage();
		if (img2 != null && "".equals(img2)) {
			String[] strings = img2.split(",");
			return strings;
		}
		return null;
	}
	
}
