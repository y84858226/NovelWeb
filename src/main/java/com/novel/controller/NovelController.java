package com.novel.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novel.dao.NovelDao;
import com.novel.pojo.Novel;
import com.novel.pojo.NovelChapterList;
import com.novel.service.NovelChapterListService;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@Controller // 证明是controller层并且返回json
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class NovelController {
	// 依赖注入
	@Autowired
	NovelChapterListService chapterListService;

	@Autowired
	NovelDao novelDao;

	/**
	 * 传入id
	 * 
	 * 通过调用本方法，生成静态文件
	 * 
	 * @param chapterList
	 */
	@RequestMapping("selectChapter")
	public String selectChapter(HttpServletRequest request, NovelChapterList novelChapterList) {
		List<NovelChapterList> list = chapterListService.selectNovelChapterList(novelChapterList);
		novelChapterList = list.get(0);
		// 爬取章节内容
		String content = chapterListService.crawlerNovelChapter(novelChapterList);
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
		String outputPath = "novelSee" + File.separator + "data" + File.separator + novel.getId() + File.separator
				+ novelChapterList.getId() + ".html";
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
			root.put("lastChapter", "lastChapter");
			root.put("nextChapter", "nextChapter");
			root.put("chapterName", novelChapterList.getChapterName());
			root.put("novelName", novel.getName());
			root.put("chapterContent", content);
			root.put("webName", "小说网");
			root.put("index", "域名");

			// 通过freemarker解释模板，首先需要获得Template对象
			Template template = cfg.getTemplate("chapterdetailtemplate.html");

			// 定义模板解释完成之后的输出
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(realPath + outputPath)));
			try {
				// 解释模板
				template.process(root, out);
				// 创建成功后更新数据库
				novelChapterList.setFilePath(outputPath);
				chapterListService.updateFilePath(novelChapterList);
			} catch (TemplateException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回静态文件地址
		return outputPath;
	}
}