package com.novel.background.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.background.common.JsoupUtil;
import com.novel.background.common.ParseNum;
import com.novel.background.dao.CrawlerConfigDao;
import com.novel.background.dao.CrawlerDao;
import com.novel.background.dao.NovelChapterListDao;
import com.novel.background.dao.NovelDao;
import com.novel.background.pojo.Crawler;
import com.novel.background.pojo.CrawlerConfig;
import com.novel.background.pojo.Novel;
import com.novel.background.pojo.NovelChapterList;
import com.novel.background.service.CrawlerService;

@Service
public class CrawlerServiceImpl implements CrawlerService {
	@Autowired
	CrawlerDao crawlerDao;
	
	@Autowired
	CrawlerConfigDao crawlerConfigDao;
	
	@Autowired
	NovelDao novelDao;
	
	@Autowired
	NovelChapterListDao chapterListDao;
	
	@Override
	public void crawlerNovelData(Crawler crawler) {
		/**
		 * 查询配置结果
		 */
		CrawlerConfig crawlerConfig=new CrawlerConfig();
		crawlerConfig.setConfigId(String.valueOf(crawler.getId()));
		List<CrawlerConfig> list=crawlerConfigDao.selectCrawlerConfig(crawlerConfig);
		
		/**
		 * 对配置结果排序
		 */
		Map<Integer,CrawlerConfig> config=new HashMap<Integer,CrawlerConfig>();
		for (CrawlerConfig cc : list) {
			config.put(cc.getSort(), cc);
		}
		
		ParseNum parseNum = new ParseNum();
		JsoupUtil util = new JsoupUtil();
		/**
		 * 第一步 查找小说列表页
		 * 
		 */
		// 存放类型页的link
		Set<String> novelTypeSet = util.getHtmlAttr(crawler.getCrawlerUrl(), null,config.get(1).getSelect(), config.get(1).getNum(), config.get(1).getAttrName(), config.get(1).getReg(),
				config.get(1).getAppendResult(), config.get(1).getRegGroupNum());
		/**
		 * 第二步 查找小说页
		 *
		 */
		// 存放小说的link
		for (String novelTypeLink : novelTypeSet) {
			Set<String> novelSet = util.getHtmlAttr(crawler.getCrawlerUrl(), null,config.get(2).getSelect(), config.get(2).getNum(), config.get(2).getAttrName(), config.get(2).getReg(),
					config.get(2).getAppendResult(), config.get(2).getRegGroupNum());
			System.out.println("类型页:" + novelTypeLink + ",共抓取小说：" + novelSet.size() + "本");

			// 访问每一个小说页面
			for (String novelLink : novelSet) {
				JsoupUtil jsoupUtil = new JsoupUtil(novelLink);
				Document doc = jsoupUtil.getDoc();
				Novel novel=new Novel();
				// 小说地址
				System.out.println("url:" + novelLink);
				novel.setUrl(novelLink);
				
				// 小说名称
				Set<String> nameSet = util.getHtmlAttr(null, doc, config.get(3).getSelect(),config.get(3).getNum(),config.get(3).getAttrName(),config.get(3).getReg(),config.get(3).getAppendResult(), config.get(3).getRegGroupNum());
				for (String name : nameSet) {
					System.out.println("小说:" + name);
					novel.setName(name);
				}

				// 作者
				Set<String> authorSet = util.getHtmlAttr(null, doc, config.get(4).getSelect(),config.get(4).getNum(),config.get(4).getAttrName(),config.get(4).getReg(),config.get(4).getAppendResult(), config.get(4).getRegGroupNum());
				for (String author : authorSet) {
					System.out.println("作者:" + author);
					novel.setAuthor(author);
				}

				// 类型名称
				Set<String> typeNameSet = util.getHtmlAttr(null, doc, config.get(5).getSelect(),config.get(5).getNum(),config.get(5).getAttrName(),config.get(5).getReg(),config.get(5).getAppendResult(), config.get(5).getRegGroupNum());;
				for (String typeName : typeNameSet) {
					System.out.println("类型名称:" + typeName);
					novel.setTypeName(typeName);
				}

				// 表述
				Set<String> descriptionSet = util.getHtmlAttr(null, doc, config.get(6).getSelect(),config.get(6).getNum(),config.get(6).getAttrName(),config.get(6).getReg(),config.get(6).getAppendResult(), config.get(6).getRegGroupNum());;
				for (String description : descriptionSet) {
					System.out.println("描述:" + description);
					novel.setDescription(description);
				}

				// 主图地址
				Set<String> mainImageSet = util.getHtmlAttr(null, doc, config.get(7).getSelect(),config.get(7).getNum(),config.get(7).getAttrName(),config.get(7).getReg(),config.get(7).getAppendResult(), config.get(7).getRegGroupNum());;
				for (String mainImage : mainImageSet) {
					System.out.println("主图地址:" + mainImage);
					novel.setMainImage(mainImage);
				}
				// 创建时间
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					novel.setCreateTime(format.format(new Date()));
				// 更新时间
					novel.setUpdateTime(format.format(new Date()));
				// 状态
					novel.setStatus("1");
				//执行数据库添加	返回id号
					novelDao.addNovel(novel);
				Integer novelId=novel.getId();
				// 全部章节列表  起一步过滤
				Set<String> chapterListSet = util.getHtmlAttr(null, doc, config.get(8).getSelect(),config.get(8).getNum(),config.get(8).getAttrName(),config.get(8).getReg(),config.get(8).getAppendResult(), config.get(8).getRegGroupNum());;
				for (String chapterListHtml : chapterListSet) {
					System.out.println(chapterListHtml);
					Document chapterListDoc = Jsoup.parse(chapterListHtml);
					//全部章节
					Set<String> chapterSet = util.getHtmlAttr(null, chapterListDoc, config.get(9).getSelect(),config.get(9).getNum(),config.get(9).getAttrName(),config.get(9).getReg(),config.get(9).getAppendResult(), config.get(9).getRegGroupNum());;
					for (String chapterA : chapterSet) {
						NovelChapterList chapterList=new NovelChapterList();
						chapterList.setNovelId(String.valueOf(novelId));
						Document chapterDoc = Jsoup.parse(chapterA);
						Set<String> chapterLinkSet = util.getHtmlAttr(null, chapterDoc, config.get(10).getSelect(),config.get(10).getNum(),config.get(10).getAttrName(),config.get(10).getReg(),config.get(10).getAppendResult(), config.get(10).getRegGroupNum());
						for (String chapterLink : chapterLinkSet) {
							System.out.println("章节地址:" + chapterLink);
							chapterList.setChapterLink(chapterLink);
						}
	
						Set<String> chapterNameSet = util.getHtmlAttr(null, chapterDoc, config.get(11).getSelect(),config.get(11).getNum(),config.get(11).getAttrName(),config.get(11).getReg(),config.get(11).getAppendResult(), config.get(11).getRegGroupNum());;
						for (String chapterName : chapterNameSet) {
							System.out.println("章节名称:" + chapterName);
							chapterList.setChapterName(chapterName);
						}
						//执行添加数据库
						chapterListDao.addNovelChapterList(chapterList);
					}
				}
			}
		}

	}

	@Override
	public void addCrawler(Crawler crawler) {
		crawlerDao.addCrawler(crawler);
	}

	@Override
	public List<Crawler> selectCrawlerByPage(int page,int limit) {
		int start=(page-1)*limit;
		int end=limit;
		return crawlerDao.selectCrawlerByPage(start,end);
	}

	@Override
	public void deleteCrawler(Crawler crawler) {
		crawlerDao.deleteCrawler(crawler);
	}

	@Override
	public int selectCrawlerCount() {
		return crawlerDao.selectCrawlerCount();
	}

	@Override
	public List<Crawler> selectCrawler(Crawler crawler) {
		return crawlerDao.selectCrawler(crawler);
	}

}