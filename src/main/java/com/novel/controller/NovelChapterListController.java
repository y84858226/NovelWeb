package com.novel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novel.pojo.NovelChapterList;
import com.novel.service.NovelChapterListService;

@Controller // 证明是controller层并且返回json
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class NovelChapterListController {
	// 依赖注入
	@Autowired
	NovelChapterListService chapterListService;

	/**
	 * 传入novelId 和chapterNum
	 * 
	 * 通过调用本方法，生成静态文件
	 * 
	 * @param chapterList
	 */
	@RequestMapping("selectChapter")
	public String selectChapter(HttpServletRequest request, NovelChapterList novelChapterList) {
		// 返回静态文件地址
		return chapterListService.selectChapter(request, novelChapterList);
	}
}