package com.novel.service;

import java.util.List;

import com.novel.pojo.NovelType;

public interface NovelTypeService {
	public void addNovelType(NovelType novelType);

	public List<NovelType> selectNovelType(int page, int limit);

	public int selectNovelTypeCount();

	public void updateNovelType();

	public void deleteNovelType(NovelType novelType);
}
