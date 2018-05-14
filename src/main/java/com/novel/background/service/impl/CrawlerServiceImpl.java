package com.novel.background.service.impl;

import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.background.common.JsoupUtil;
import com.novel.background.common.ParseNum;
import com.novel.background.dao.CrawlerDao;
import com.novel.background.pojo.Crawler;
import com.novel.background.service.CrawlerService;

@Service
public class CrawlerServiceImpl implements CrawlerService {
	@Autowired
	CrawlerDao crawlerDao;

	@Override
	public void crawlerNovelData(String url) {
		ParseNum parseNum = new ParseNum();
		JsoupUtil util = new JsoupUtil();
		/**
		 * 第一步 查找小说列表页
		 */
		// 存放类型页的link
		Set<String> novelTypeSet = util.getHtmlAttr(url, null, "a", "all", "href", "www.biquge5200.cc/[A-Za-z]+/$",
				"https://", 0);
		/**
		 * 第二步 查找小说页
		 */
		// 存放小说的link
		for (String novelTypeLink : novelTypeSet) {
			Set<String> novelSet = util.getHtmlAttr(novelTypeLink, null, "a", "all", "href",
					"www.biquge5200.cc/\\d+_\\d+/$", "https://", 0);
			System.out.println("类型页:" + novelTypeLink + ",共抓取小说：" + novelSet.size() + "本");

			// 访问每一个小说页面
			for (String novelLink : novelSet) {
				JsoupUtil jsoupUtil = new JsoupUtil(novelLink);
				Document doc = jsoupUtil.getDoc();
				// 小说地址
				System.out.println("url:" + novelLink);

				// 小说名称
				Set<String> nameSet = util.getHtmlAttr(null, doc, "#info h1", "0", "html", "[\\s\\S]*", "", 0);
				for (String name : nameSet) {
					System.out.println("小说:" + name);
				}

				// 作者
				Set<String> authorSet = util.getHtmlAttr(null, doc, "#info p", "0", "html", "([^：]+)$", "", 0);
				for (String author : authorSet) {
					System.out.println("作者:" + author);
				}

				// 类型名称
				Set<String> typeNameSet = util.getHtmlAttr(null, doc, ".con_top a", "2", "html", "[\\s\\S]*", "", 0);
				for (String typeName : typeNameSet) {
					System.out.println("类型名称:" + typeName);
				}

				// 表述
				Set<String> descriptionSet = util.getHtmlAttr(null, doc, "#intro p", "0", "html", "[\\s\\S]*", "", 0);
				for (String description : descriptionSet) {
					System.out.println("描述:" + description);
				}

				// 主图地址
				Set<String> mainImageSet = util.getHtmlAttr(null, doc, "#fmimg img", "0", "src", "[\\s\\S]*", "", 0);
				for (String mainImage : mainImageSet) {
					System.out.println("主图地址:" + mainImage);
				}
				// 全部章节
				Set<String> chapterSet = util.getHtmlAttr(null, doc, "#list dd", "all", "html", "[\\s\\S]*", "", 0);
				for (String chapterA : chapterSet) {
					Document chapterDoc = Jsoup.parse(chapterA);
					Set<String> chapterLinkSet = util.getHtmlAttr(null, chapterDoc, "a", "0", "href", "[\\s\\S]*", "",
							0);
					for (String chapterLink : chapterLinkSet) {
						System.out.println("章节地址:" + chapterLink);
					}

					Set<String> chapterNumSet = util.getHtmlAttr(null, chapterDoc, "a", "0", "html", "第(.*)章", "", 1);
					for (String chapterNum : chapterNumSet) {
						int chapterNumber = 0;
						try {
							chapterNumber = parseNum.parse(chapterNum);
						} catch (Exception e) {
							chapterNumber = Integer.parseInt(chapterNum);
						}
						System.out.println("章节编号:" + chapterNumber);
					}

					Set<String> chapterNameSet = util.getHtmlAttr(null, chapterDoc, "a", "0", "html", "[\\s\\S]*", "",
							0);
					for (String chapterName : chapterNameSet) {
						System.out.println("章节名称:" + chapterName);
					}
				}
				// 创建时间

				// 更新时间

				// 状态
			}
		}

	}

	@Override
	public void addCrawler(Crawler crawler) {
		crawlerDao.addCrawler(crawler);
	}

	@Override
	public List<Crawler> selectCrawler(int page,int limit) {
		int start=(page-1)*limit;
		int end=limit;
		return crawlerDao.selectCrawler(start,end);
	}

	@Override
	public void deleteCrawler(Crawler crawler) {
		crawlerDao.deleteCrawler(crawler);
	}

	@Override
	public int selectCrawlerCount() {
		return crawlerDao.selectCrawlerCount();
	}

}