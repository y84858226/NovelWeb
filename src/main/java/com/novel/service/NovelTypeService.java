package com.novel.service;

import java.util.List;

import com.novel.pojo.NovelType;

public interface NovelTypeService {
	public void addNovelType();

	public List<NovelType> selectNovelType(int page, int limit);

	public int selectNovelTypeCount();
}
