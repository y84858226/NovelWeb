package com.novel.service;

import java.util.List;

import com.novel.pojo.NovelChapterList;

public interface NovelChapterListService {

	List<NovelChapterList> selectNovelChapterList(NovelChapterList chapterList);

}
