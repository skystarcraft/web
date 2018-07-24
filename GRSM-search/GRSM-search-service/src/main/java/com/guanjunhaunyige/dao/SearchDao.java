package com.guanjunhaunyige.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.guanjunhuanyige.SearchItem;
import com.guanjunhuanyige.SearchResult;

/**
 * 商品搜索
 * @author 落雪封尘
 *
 */
@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	/*
	 * 根据查询条件查询索引
	 */
	public SearchResult search(SolrQuery query) throws Exception {
		QueryResponse queryRes = solrServer.query(query);
		SolrDocumentList solrList = queryRes.getResults();
		long numFound = solrList.getNumFound();
		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		Map<String,Map<String, List<String>>> highlight = queryRes.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument s : solrList) {
			SearchItem item = new SearchItem();
			item.setId(s.get("id").toString());
			item.setCategory_name(s.get("item_category_name").toString());
			item.setImage(s.get("item_image").toString());
			item.setPrice((long) s.get("item_price"));
			item.setSell_point(s.get("item_sell_point").toString());
			List<String> list = highlight.get(s.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = s.get("item_title").toString();
			}
			item.setTitle(title);
			itemList.add(item);
		}
		result.setItemList(itemList);
		return result;
	}
	
}
