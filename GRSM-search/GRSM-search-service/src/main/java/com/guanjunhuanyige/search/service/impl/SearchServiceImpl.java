package com.guanjunhuanyige.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guanjunhaunyige.dao.SearchDao;
import com.guanjunhuanyige.SearchResult;
import com.guanjunhuanyige.search.service.SearchService;

/**
 * 商品搜索
 * @author 落雪封尘
 *
 */
@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String keywords, int page, int rows) throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery(keywords);
		if (page <= 0) page = 1;
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		SearchResult result = searchDao.search(query);
		long recordCount = result.getRecordCount();
		int totalPage = (int) (recordCount / rows);
		if (recordCount % rows > 0) totalPage++;
		result.setPageCount(totalPage);
		return result;
	}

}
