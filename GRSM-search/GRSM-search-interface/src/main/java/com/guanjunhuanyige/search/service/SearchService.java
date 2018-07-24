package com.guanjunhuanyige.search.service;

import com.guanjunhuanyige.SearchResult;

public interface SearchService {

	SearchResult search(String keywords, int page, int rows) throws Exception;
	
}
