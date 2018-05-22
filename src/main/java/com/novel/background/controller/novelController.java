package com.novel.background.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novel.background.pojo.NovelChapterList;
import com.novel.background.service.NovelChapterListService;

@RestController // 证明是controller层并且返回json
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class novelController {
	// 依赖注入
	@Autowired
	NovelChapterListService chapterListService;

	@RequestMapping("selectChapter")
	public void selectChapter(NovelChapterList chapterList) {
		List<NovelChapterList> chapterLists = chapterListService.selectNovelChapterList(chapterList);
		// 查询章节原地址
		// 爬取章节内容
		// 生成静态文件

		// 返回静态文件地址
	}
}