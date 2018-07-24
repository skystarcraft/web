package com.guanjunhuanyige.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guanjunhuanyige.SearchItem;
import com.guanjunhuanyige.mapper.ItemMapper;
import com.guanjunhuanyige.search.service.SearchItemService;
import com.guanjunhuanyige.utils.TaojinResult;


@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer solr;

	@Override
	public TaojinResult importAllItems() {
		List<SearchItem> list = itemMapper.getItemList();
		try {
			for (SearchItem s : list) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", s.getId());
				doc.addField("item_title", s.getTitle());
				doc.addField("item_sell_point", s.getSell_point());
				doc.addField("item_price", s.getPrice());
				doc.addField("item_image", s.getImage());
				doc.addField("item_category_name", s.getCategory_name());
				solr.add(doc);
			}
			solr.commit();
			return TaojinResult.ok();
		} catch (Exception e){
			e.printStackTrace();
			return TaojinResult.build(500, "数据导入失败！");
		}
	}
	
}
