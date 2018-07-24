package TestSolr;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestCase {

	@Test
	public void addDoc() throws SolrServerException, IOException {
		SolrServer solr = new HttpSolrServer("http://139.199.197.143:8088/solr/");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "doc01");
		doc.addField("item_title", "小米X6");
		doc.addField("item_price", 1599);
		solr.add(doc);
		solr.commit();
	}
	
	@Test
	public void delDoc() throws Exception {
		SolrServer solr = new HttpSolrServer("http://139.199.197.143:8088/solr/");
		solr.deleteById("doc01");
		solr.commit();
	}
	
	@Test
	public void querySolr() throws SolrServerException {
		SolrServer solr = new HttpSolrServer("http://139.199.197.143:8088/solr/");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse res = solr.query(query);
		SolrDocumentList list = res.getResults();
		System.out.println("总记录:" + list.getNumFound());
		for (SolrDocument s : list) {
			System.out.println(s.get("id"));
			System.out.println(s.get("item_title"));
			System.out.println(s.get("item_sell_point"));
			System.out.println(s.get("item_price"));
			System.out.println(s.get("item_image"));
			System.out.println(s.get("item_category_name"));
		}
	}
	
	@Test
	public void query() throws Exception{
		SolrServer solr = new HttpSolrServer("http://139.199.197.143:8088/solr/");
		SolrQuery query = new SolrQuery();
		query.setQuery("手机");
		query.setStart(0);
		query.setRows(20);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("</em>");
		query.setHighlightSimplePost("</em>");
		QueryResponse res = solr.query(query);
		SolrDocumentList list = res.getResults();
		System.out.println("总记录:" + list.getNumFound());
		for (SolrDocument s : list) {
			System.out.println(s.get("id"));
			Map<String,Map<String, List<String>>> highlight = res.getHighlighting();
			List<String> l = highlight.get(s.get("id")).get("item_title");
			String title = "";
			if (l != null && l.size() > 0) {
				title = l.get(0);
			} else {
				title = s.get("item_title").toString();
			}
			System.out.println(title);
			System.out.println(s.get("item_sell_point"));
			System.out.println(s.get("item_price"));
			System.out.println(s.get("item_image"));
			System.out.println(s.get("item_category_name"));
		}
	}
}
