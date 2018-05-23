package com.novel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novel.pojo.NovelChapterList;
import com.novel.service.NovelChapterListService;

@RestController // 证明是controller层并且返回json
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class NovelController {
	// 依赖注入
	@Autowired
	NovelChapterListService chapterListService;

	/**
	 * 传入id
	 * 
	 * 通过调用本方法，生成静态文件
	 * 
	 * @param chapterList
	 */
	@RequestMapping("selectChapter")
	public String selectChapter(NovelChapterList novelChapterList) {
		List<NovelChapterList> list = chapterListService.selectNovelChapterList(novelChapterList);
		novelChapterList = list.get(0);
		// 爬取章节内容
		String content= chapterListService.crawlerNovelChapter(novelChapterList);
		// 生成静态文件

		// 返回静态文件地址
		
		return "https://www.baidu.com";
	}
}