package com.novel.background.service;

import java.util.List;

import com.novel.background.pojo.NovelChapterList;

public interface NovelChapterListService {

	List<NovelChapterList> selectNovelChapterList(NovelChapterList chapterList);

}
