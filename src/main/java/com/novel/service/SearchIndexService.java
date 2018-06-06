package com.novel.service;

import java.util.List;

import com.novel.pojo.Novel;

public interface SearchIndexService {
	public void createIndex(String indexPath);

	public List<Novel> searchData(String queryStr, String indexPath);
}
