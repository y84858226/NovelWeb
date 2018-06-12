package com.novel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		String url=chapterListService.selectChapter(request, novelChapterList);
		// 返回静态文件地址
		return "redirect:"+url;
	}

	@ResponseBody
	@RequestMapping("currentPage")
	public Map<String, NovelChapterList> currentPage(NovelChapterList chapterList) {
		Map<String, NovelChapterList> map = new HashMap<String, NovelChapterList>();
		/*
		 * 上一页
		 */
		NovelChapterList lastChaptyer = new NovelChapterList();
		lastChaptyer.setNovelId(chapterList.getNovelId());
		lastChaptyer.setChapterNum(chapterList.getChapterNum() - 1);
		List<NovelChapterList> lastList = chapterListService.selectNovelChapterList(lastChaptyer);
		if (lastList.size() > 0) {
			lastChaptyer = lastList.get(0);
		}
		map.put("last", lastChaptyer);
		/*
		 * 下一页
		 */
		NovelChapterList nextChaptyer = new NovelChapterList();
		nextChaptyer.setNovelId(chapterList.getNovelId());
		nextChaptyer.setChapterNum(chapterList.getChapterNum() + 1);
		List<NovelChapterList> nextList = chapterListService.selectNovelChapterList(nextChaptyer);
		if (nextList.size() > 0) {
			nextChaptyer = nextList.get(0);
		}
		map.put("next", nextChaptyer);
		return map;
	}
	
	/*
	 * 更新小说的章节json文件
	 */
	@ResponseBody
	@RequestMapping("updateChapterListJsonFile")
	public void updateChapterListJsonFile(HttpServletRequest request,NovelChapterList novelChapterList) {
		chapterListService.updateChapterListJsonFile(request,novelChapterList);
	}
	
}