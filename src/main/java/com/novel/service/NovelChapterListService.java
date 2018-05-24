package com.novel.service;

import java.util.List;

import com.novel.pojo.NovelChapterList;

public interface NovelChapterListService {

	public List<NovelChapterList> selectNovelChapterList(NovelChapterList chapterList);

	public String crawlerNovelChapter(NovelChapterList chapterList);

	public void updateFilePath(NovelChapterList novelChapterList);

}
