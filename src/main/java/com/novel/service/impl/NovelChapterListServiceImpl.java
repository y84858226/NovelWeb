package com.novel.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.common.JsoupUtil;
import com.novel.dao.CrawlerConfigDao;
import com.novel.dao.NovelChapterListDao;
import com.novel.dao.NovelDao;
import com.novel.pojo.CrawlerConfig;
import com.novel.pojo.Novel;
import com.novel.pojo.NovelChapterList;
import com.novel.service.CrawlerService;
import com.novel.service.NovelChapterListService;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@Service
public class NovelChapterListServiceImpl implements NovelChapterListService {
	@Autowired
	NovelChapterListDao chapterListDao;

	@Autowired
	CrawlerConfigDao crawlerConfigDao;

	@Autowired
	NovelDao novelDao;

	@Autowired
	CrawlerService crawlerService;

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

	@Override
	public String selectChapter(HttpServletRequest request, NovelChapterList novelChapterList) {
		String webappPath = request.getSession().getServletContext().getRealPath("/");
		List<NovelChapterList> list = chapterListDao.selectNovelChapterList(novelChapterList);
		novelChapterList = list.get(0);
		// 爬取章节内容
		String content = crawlerNovelChapter(novelChapterList);
		// 获取小说内容
		Novel novel = new Novel();
		novel.setId(novelChapterList.getNovelId());
		novel = novelDao.selectNovel(novel).get(0);
		// 模板文件路径
		String path = request.getSession().getServletContext().getRealPath("/") + "novelSee" + File.separator
				+ "template";
		Configuration cfg = new Configuration();
		// 保存路径
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String outputPath = "data" + File.separator + novel.getId() + File.separator + "chapter" + File.separator
				+ novelChapterList.getChapterNum() + ".html";
		
		try {
			// 从哪里加载模板文件
			cfg.setDirectoryForTemplateLoading(new File(path));

			// 定义模版的位置，从类路径中，相对于FreemarkerManager所在的路径加载模版
			// cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkerManager.class,
			// "templates"))

			// 设置对象包装器
			cfg.setObjectWrapper(new DefaultObjectWrapper());

			// 设置异常处理器
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

			// 定义数据模型
			Map root = new HashMap();
			root.put("keywords", novel.getName() + "," + novelChapterList.getChapterName());
			root.put("description", novel.getAuthor() + "创作的" + novel.getTypeName() + "《" + novel.getName()
					+ "》干净清爽无错字的文字章节:" + novelChapterList.getChapterName());
			root.put("canonical", "/novel" + File.separator + outputPath);
			root.put("title", novelChapterList.getChapterName());
			root.put("chapterName", novelChapterList.getChapterName());
			root.put("novelName", novel.getName());
			root.put("chapterContent", content);
			root.put("novelId", novel.getId());
			root.put("chapterNum", novelChapterList.getChapterNum());
			root.put("webName", "待定内容");
			root.put("index", "待定内容");

			// 通过freemarker解释模板，首先需要获得Template对象
			Template template = cfg.getTemplate("chapterdetailtemplate.html");

			// 定义模板解释完成之后的输出
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(realPath + outputPath)));
			try {
				// 解释模板
				template.process(root, out);
				// 创建成功后更新数据库
				novelChapterList.setFilePath("/novel/"+outputPath);
				updateFilePath(novelChapterList);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			/*
			 * 更新chapter的json文件
			 */
			NovelChapterList chapterList = new NovelChapterList();
			chapterList.setNovelId(novelChapterList.getNovelId());
			List<NovelChapterList> novelChapterLists = chapterListDao.selectNovelChapterList(chapterList);
			String novelPath = "data" + File.separator + novelChapterList.getNovelId();
			crawlerService.createJson(novelChapterLists, webappPath + novelPath + File.separator + "chapter.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputPath;
	}

}
