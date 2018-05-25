package com.novel.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.common.JsoupUtil;
import com.novel.dao.CrawlerConfigDao;
import com.novel.dao.NovelChapterListDao;
import com.novel.pojo.CrawlerConfig;
import com.novel.pojo.NovelChapterList;
import com.novel.service.NovelChapterListService;

@Service
public class NovelChapterListServiceImpl implements NovelChapterListService {
	@Autowired
	NovelChapterListDao chapterListDao;

	@Autowired
	CrawlerConfigDao crawlerConfigDao;

	@Override
	public List<NovelChapterList> selectNovelChapterList(NovelChapterList chapterList) {
		return chapterListDao.selectNovelChapterList(chapterList);
	}

	@Override
	public String crawlerNovelChapter(NovelChapterList novelChapterList) {
		// 章节地址
		String chapterLink = novelChapterList.getChapterLink();
		// 配置文件
		Integer crawlerConfigId = novelChapterList.getCrawlerConfigId();
		CrawlerConfig crawlerConfig = new CrawlerConfig();
		crawlerConfig.setId(crawlerConfigId);
		List<CrawlerConfig> configs = crawlerConfigDao.selectCrawlerConfig(crawlerConfig);
		crawlerConfig = configs.get(0);
		// 爬虫文章内容
		JsoupUtil util = new JsoupUtil();
		Set<String> chapterContentSet = util.getHtmlAttr(chapterLink, null, crawlerConfig.getSelector(),
				crawlerConfig.getNum(), crawlerConfig.getAttrName(), crawlerConfig.getReg(),
				crawlerConfig.getHeadAppendResult(), crawlerConfig.getTailAppendResult(),
				crawlerConfig.getReplaceResult(), crawlerConfig.getRegGroupNum());
		String chapterContent = null;
		for (String content : chapterContentSet) {
			chapterContent = content;
		}
		return chapterContent;
	}

	@Override
	public void updateFilePath(NovelChapterList novelChapterList) {
		chapterListDao.updateFilePath(novelChapterList);		
	}

}
