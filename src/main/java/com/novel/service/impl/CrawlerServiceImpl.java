package com.novel.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.novel.common.ImageUtil;
import com.novel.common.JsoupUtil;
import com.novel.dao.CrawlerConfigDao;
import com.novel.dao.CrawlerDao;
import com.novel.dao.NovelChapterListDao;
import com.novel.dao.NovelDao;
import com.novel.pojo.Crawler;
import com.novel.pojo.CrawlerConfig;
import com.novel.pojo.Novel;
import com.novel.pojo.NovelChapterList;
import com.novel.service.CrawlerService;

@Service
public class CrawlerServiceImpl implements CrawlerService {
	@Autowired
	CrawlerDao crawlerDao;

	@Autowired
	CrawlerConfigDao crawlerConfigDao;

	@Autowired
	NovelDao novelDao;

	@Autowired
	NovelChapterListDao novelChapterListDao;

	@Override
	public void crawlerNovelData(HttpServletRequest request, Crawler crawler) {
		String webappPath = request.getSession().getServletContext().getRealPath("/");
		/*
		 * 查询配置
		 */
		CrawlerConfig crawlerConfig = new CrawlerConfig();
		crawlerConfig.setCrawlerId(String.valueOf(crawler.getId()));
		List<CrawlerConfig> list = crawlerConfigDao.selectCrawlerConfig(crawlerConfig);

		/*
		 * 对配置按照sort序号排序
		 */
		Map<Integer, CrawlerConfig> config = new HashMap<Integer, CrawlerConfig>();
		for (CrawlerConfig cc : list) {
			config.put(cc.getSort(), cc);
		}

		JsoupUtil util = new JsoupUtil();
		/*
		 * 查找小说类型页
		 * 
		 * 遍历每一个小说页
		 */
		Set<String> novelTypeSet = util.getHtmlAttr(crawler.getCrawlerUrl(), null, config.get(1).getSelector(),
				config.get(1).getNum(), config.get(1).getAttrName(), config.get(1).getReg(),
				config.get(1).getHeadAppendResult(), config.get(1).getTailAppendResult(),
				config.get(1).getReplaceResult(), config.get(1).getRegGroupNum());
		for (String novelTypeLink : novelTypeSet) {
			/*
			 * 查找小说页
			 * 
			 * 遍历每一个小说页
			 */
			Set<String> novelSet = util.getHtmlAttr(novelTypeLink, null, config.get(2).getSelector(),
					config.get(2).getNum(), config.get(2).getAttrName(), config.get(2).getReg(),
					config.get(2).getHeadAppendResult(), config.get(2).getTailAppendResult(),
					config.get(2).getReplaceResult(), config.get(2).getRegGroupNum());
			for (String novelLink : novelSet) {
				Integer chapterNum = 0;
				JsoupUtil jsoupUtil = new JsoupUtil(novelLink);
				Document doc = jsoupUtil.getDoc();
				Novel novel = new Novel();
				/*
				 * 小说名称
				 */
				Set<String> nameSet = util.getHtmlAttr(null, doc, config.get(3).getSelector(), config.get(3).getNum(),
						config.get(3).getAttrName(), config.get(3).getReg(), config.get(3).getHeadAppendResult(),
						config.get(3).getTailAppendResult(), config.get(3).getReplaceResult(),
						config.get(3).getRegGroupNum());
				for (String name : nameSet) {
					novel.setName(name);
				}
				/*
				 * 作者
				 */
				Set<String> authorSet = util.getHtmlAttr(null, doc, config.get(4).getSelector(), config.get(4).getNum(),
						config.get(4).getAttrName(), config.get(4).getReg(), config.get(4).getHeadAppendResult(),
						config.get(4).getTailAppendResult(), config.get(4).getReplaceResult(),
						config.get(4).getRegGroupNum());
				for (String author : authorSet) {
					novel.setAuthor(author);
				}
				/*
				 * 根据小说名称和作者判断数据库是否有这本小说
				 */
				List<Novel> novelList = novelDao.selectNovel(novel);
				if (novelList.size() > 0) {
					/**
					 * 小说已经存在
					 * 
					 * 更新章节
					 */
					Integer novelId = novelList.get(0).getId();
					/*
					 * 检查小说 目录的文件是否存在，不存在进行创建
					 * 
					 * 图片、小说json、目录json、章节目录文件
					 */
					File file = new File(
							webappPath + "data" + File.separator + novelId + File.separator + "novel.json");
					if (!file.exists()) {
						createJson(novelList.get(0),
								webappPath + "data" + File.separator + novelId + File.separator + "novel.json");
					}
					file = new File(webappPath + "data" + File.separator + novelId + File.separator + "chapter.json");
					if (!file.exists()) {
						NovelChapterList validList = new NovelChapterList();
						validList.setNovelId(novelId);
						List<NovelChapterList> validLists = novelChapterListDao.selectChapterNameAndPath(validList);
						createJson(validLists,
								webappPath + "data" + File.separator + novelId + File.separator + "chapter.json");
					}
					file = new File(webappPath + "data" + File.separator + novelId + File.separator + novelId + ".jpg");
					if (!file.exists()) {
						/*
						 * 主图地址
						 */
						Set<String> mainImageSet = util.getHtmlAttr(null, doc, config.get(7).getSelector(),
								config.get(7).getNum(), config.get(7).getAttrName(), config.get(7).getReg(),
								config.get(7).getHeadAppendResult(), config.get(7).getTailAppendResult(),
								config.get(7).getReplaceResult(), config.get(7).getRegGroupNum());
						for (String mainImage : mainImageSet) {
							ImageUtil imageUtil = new ImageUtil();
							/*
							 * 下载图片
							 */
							imageUtil.download(mainImage,
									webappPath + "data" + File.separator + novelId + File.separator + novelId + ".jpg");
						}
					}
					file = new File(webappPath + "data" + File.separator + novelId + File.separator + "chapter");
					if (!file.exists()) {
						file.mkdirs();
					}
					/*
					 * 查询小说的最新章节
					 */
					NovelChapterList novelChapterList = new NovelChapterList();
					novelChapterList.setNovelId(novelId);
					novelChapterList = novelChapterListDao.selectMaxchapter(novelChapterList);
					/*
					 * 最新章节名称
					 */
					String maxChapterName = novelChapterList.getChapterName();
					/*
					 * 最新章节编号
					 */
					chapterNum = novelChapterList.getChapterNum();
					/*
					 * 
					 */
					Set<String> chapterListSet = util.getHtmlAttr(null, doc, config.get(9).getSelector(),
							config.get(9).getNum(), config.get(9).getAttrName(), config.get(9).getReg(),
							config.get(9).getHeadAppendResult(), config.get(9).getTailAppendResult(),
							config.get(9).getReplaceResult(), config.get(9).getRegGroupNum());
					for (String chapterListHtml : chapterListSet) {
						/*
						 * 设置更新开关
						 */
						boolean searchLastChapter = false;
						/*
						 * 判断是否有更新操作，如果有，则更新最新章节和更新日期
						 */
						boolean updateFlag = false;

						/*
						 * 获取全部章节
						 * 
						 * 遍历每个章节
						 */
						Document chapterListDoc = Jsoup.parse(chapterListHtml);
						Set<String> chapterSet = util.getHtmlAttr(null, chapterListDoc, config.get(10).getSelector(),
								config.get(10).getNum(), config.get(10).getAttrName(), config.get(10).getReg(),
								config.get(10).getHeadAppendResult(), config.get(10).getTailAppendResult(),
								config.get(10).getReplaceResult(), config.get(10).getRegGroupNum());
						for (String chapterA : chapterSet) {
							/*
							 * 创建章节
							 */
							NovelChapterList chapterList = new NovelChapterList();
							chapterList.setNovelId(novelId);
							Document chapterDoc = Jsoup.parse(chapterA);
							Set<String> chapterNameSet = util.getHtmlAttr(null, chapterDoc,
									config.get(12).getSelector(), config.get(12).getNum(), config.get(12).getAttrName(),
									config.get(12).getReg(), config.get(12).getHeadAppendResult(),
									config.get(12).getTailAppendResult(), config.get(12).getReplaceResult(),
									config.get(12).getRegGroupNum());
							for (String chapterName : chapterNameSet) {
								/*
								 * 匹配上最新章节
								 */
								if (maxChapterName.equals(chapterName)) {
									/*
									 * 开启更新开关
									 */
									searchLastChapter = true;
								}
								chapterList.setChapterName(chapterName);
							}
							if (searchLastChapter) {
								/*
								 * 查询章节是否存在
								 */
								List<NovelChapterList> chapterLists = novelChapterListDao
										.selectNovelChapterList(chapterList);
								if (chapterLists.size() > 0) {
									/*
									 * 已经存在章节
									 */
								} else {
									/*
									 * 章节不存在
									 * 
									 * 更新章节信息
									 */
									updateFlag = true;
									Set<String> chapterLinkSet = util.getHtmlAttr(null, chapterDoc,
											config.get(11).getSelector(), config.get(11).getNum(),
											config.get(11).getAttrName(), config.get(11).getReg(),
											config.get(11).getHeadAppendResult(), config.get(11).getTailAppendResult(),
											config.get(11).getReplaceResult(), config.get(11).getRegGroupNum());
									for (String chapterLink : chapterLinkSet) {
										/*
										 * 章节地址
										 */
										chapterList.setChapterLink(chapterLink);
									}
									/*
									 * 章节编号
									 */
									chapterNum = chapterNum + 1;
									chapterList.setChapterNum(chapterNum);
									/*
									 * crawlerConfigId
									 * 
									 * 详情页的爬虫规则id
									 */
									chapterList.setCrawlerConfigId(config.get(13).getId());
									/*
									 * 执行添加数据库
									 */
									novelChapterListDao.addNovelChapterList(chapterList);
								}
							}
						}
						if (updateFlag) {
							/*
							 * 更新novel时间 和最新章节
							 */
							Novel novel2 = new Novel();
							novel2.setId(novelId);
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							novel2.setUpdateTime(format.format(new Date()));
							novel2.setLastChapterNum(chapterNum);
							novelDao.updateTimeAndLastChapterNum(novel2);
							/*
							 * 创建json文件
							 */
							novel2 = new Novel();
							novel2.setId(novelId);
							novel2 = novelDao.selectNovel(novel2).get(0);
							String novelPath = "data" + File.separator + novelId;
							createJson(novel2, webappPath + novelPath + File.separator + "novel.json");
							/*
							 * 创建chapter的json文件
							 */
							NovelChapterList chapterList = new NovelChapterList();
							chapterList.setNovelId(novelId);
							List<NovelChapterList> novelChapterLists = novelChapterListDao
									.selectChapterNameAndPath(chapterList);
							createJson(novelChapterLists, webappPath + novelPath + File.separator + "chapter.json");
						}
					}
				} else {
					/**
					 * 添加小说
					 */

					/*
					 * 类型名称
					 */
					Set<String> typeNameSet = util.getHtmlAttr(null, doc, config.get(5).getSelector(),
							config.get(5).getNum(), config.get(5).getAttrName(), config.get(5).getReg(),
							config.get(5).getHeadAppendResult(), config.get(5).getTailAppendResult(),
							config.get(5).getReplaceResult(), config.get(5).getRegGroupNum());
					for (String typeName : typeNameSet) {
						novel.setTypeName(typeName);
					}

					/*
					 * 描述
					 */
					Set<String> descriptionSet = util.getHtmlAttr(null, doc, config.get(6).getSelector(),
							config.get(6).getNum(), config.get(6).getAttrName(), config.get(6).getReg(),
							config.get(6).getHeadAppendResult(), config.get(6).getTailAppendResult(),
							config.get(6).getReplaceResult(), config.get(6).getRegGroupNum());
					for (String description : descriptionSet) {
						novel.setDescription(description);
					}

					/*
					 * 状态
					 */
					Set<String> statusSet = util.getHtmlAttr(null, doc, config.get(8).getSelector(),
							config.get(8).getNum(), config.get(8).getAttrName(), config.get(8).getReg(),
							config.get(8).getHeadAppendResult(), config.get(8).getTailAppendResult(),
							config.get(8).getReplaceResult(), config.get(8).getRegGroupNum());
					for (String status : statusSet) {
						novel.setStatus(status);
					}
					/*
					 * 创建时间
					 */
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					novel.setCreateTime(format.format(new Date()));
					/*
					 * 更新时间
					 */
					novel.setUpdateTime(format.format(new Date()));
					/*
					 * 是否隐藏
					 */
					novel.setDisplay("0");
					/*
					 * 点击率
					 */
					novel.setClickView(0);
					/*
					 * 执行添加Novel到数据库
					 * 
					 * 返回id号
					 */
					novelDao.addNovel(novel);
					Integer novelId = novel.getId();

					/*
					 * 创建小说的目录
					 */
					String novelPath = "data" + File.separator + novelId;
					String chapterPath = novelPath + File.separator + "chapter";
					File chapterDir = new File(webappPath + chapterPath);
					if (!chapterDir.exists()) {
						chapterDir.mkdirs();
					}
					/*
					 * 主图地址
					 */
					Set<String> mainImageSet = util.getHtmlAttr(null, doc, config.get(7).getSelector(),
							config.get(7).getNum(), config.get(7).getAttrName(), config.get(7).getReg(),
							config.get(7).getHeadAppendResult(), config.get(7).getTailAppendResult(),
							config.get(7).getReplaceResult(), config.get(7).getRegGroupNum());
					for (String mainImage : mainImageSet) {
						ImageUtil imageUtil = new ImageUtil();
						String saveMainImage = novelPath + File.separator + novelId + ".jpg";
						/*
						 * 下载图片
						 */
						imageUtil.download(mainImage, webappPath + saveMainImage);
					}

					/*
					 * 获取过滤后的章节列表
					 */
					Set<String> chapterListSet = util.getHtmlAttr(null, doc, config.get(9).getSelector(),
							config.get(9).getNum(), config.get(9).getAttrName(), config.get(9).getReg(),
							config.get(9).getHeadAppendResult(), config.get(9).getTailAppendResult(),
							config.get(9).getReplaceResult(), config.get(9).getRegGroupNum());
					for (String chapterListHtml : chapterListSet) {
						/*
						 * 全部章节
						 * 
						 * 遍历每一个章节
						 */
						Document chapterListDoc = Jsoup.parse(chapterListHtml);
						Set<String> chapterSet = util.getHtmlAttr(null, chapterListDoc, config.get(10).getSelector(),
								config.get(10).getNum(), config.get(10).getAttrName(), config.get(10).getReg(),
								config.get(10).getHeadAppendResult(), config.get(10).getTailAppendResult(),
								config.get(10).getReplaceResult(), config.get(10).getRegGroupNum());
						for (String chapterA : chapterSet) {
							NovelChapterList chapterList = new NovelChapterList();
							chapterList.setNovelId(novelId);
							Document chapterDoc = Jsoup.parse(chapterA);
							/*
							 * 章节链接
							 */
							Set<String> chapterLinkSet = util.getHtmlAttr(null, chapterDoc,
									config.get(11).getSelector(), config.get(11).getNum(), config.get(11).getAttrName(),
									config.get(11).getReg(), config.get(11).getHeadAppendResult(),
									config.get(11).getTailAppendResult(), config.get(11).getReplaceResult(),
									config.get(11).getRegGroupNum());
							for (String chapterLink : chapterLinkSet) {
								chapterList.setChapterLink(chapterLink);
							}
							/*
							 * 章节编号
							 */
							chapterNum = chapterNum + 1;
							chapterList.setChapterNum(chapterNum);
							/*
							 * 章节名称
							 */
							Set<String> chapterNameSet = util.getHtmlAttr(null, chapterDoc,
									config.get(12).getSelector(), config.get(12).getNum(), config.get(12).getAttrName(),
									config.get(12).getReg(), config.get(12).getHeadAppendResult(),
									config.get(12).getTailAppendResult(), config.get(12).getReplaceResult(),
									config.get(12).getRegGroupNum());
							for (String chapterName : chapterNameSet) {
								chapterList.setChapterName(chapterName);
							}
							/*
							 * 章节详情页的配置id
							 */
							chapterList.setCrawlerConfigId(config.get(13).getId());
							/*
							 * 把章节信息添加到数据库
							 */
							novelChapterListDao.addNovelChapterList(chapterList);
						} // 遍历每个章节结束
					}
					/*
					 * 更新最新章节
					 */
					novel.setLastChapterNum(chapterNum);
					novelDao.updateTimeAndLastChapterNum(novel);
					/*
					 * 创建novel的json文件
					 */
					createJson(novel, webappPath + novelPath + File.separator + "novel.json");
					/*
					 * 创建chapter的json文件
					 */
					NovelChapterList chapterList = new NovelChapterList();
					chapterList.setNovelId(novelId);
					List<NovelChapterList> novelChapterLists = novelChapterListDao.selectChapterNameAndPath(chapterList);
					createJson(novelChapterLists, webappPath + novelPath + File.separator + "chapter.json");
				} // 添加小说结束

			} // 小说循环结束
		}
	}

	public Map<String, Object> testCrawlerNovelData(HttpServletRequest request, Crawler crawler) {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 查询配置结果
		 */
		CrawlerConfig crawlerConfig = new CrawlerConfig();
		crawlerConfig.setCrawlerId(String.valueOf(crawler.getId()));
		List<CrawlerConfig> list = crawlerConfigDao.selectCrawlerConfig(crawlerConfig);

		/**
		 * 对配置结果排序
		 */
		Map<Integer, CrawlerConfig> config = new HashMap<Integer, CrawlerConfig>();
		for (CrawlerConfig cc : list) {
			config.put(cc.getSort(), cc);
		}

		JsoupUtil util = new JsoupUtil();
		/**
		 * 第一步 查找小说列表页
		 * 
		 */
		// 存放类型页的link
		Set<String> novelTypeSet = util.getHtmlAttr(crawler.getCrawlerUrl(), null, config.get(1).getSelector(),
				config.get(1).getNum(), config.get(1).getAttrName(), config.get(1).getReg(),
				config.get(1).getHeadAppendResult(), config.get(1).getTailAppendResult(),
				config.get(1).getReplaceResult(), config.get(1).getRegGroupNum());
		map.put("novelTypeNumber", novelTypeSet.size());
		/**
		 * 第二步 查找小说页
		 *
		 */
		// 存放小说的link
		for (String novelTypeLink : novelTypeSet) {
			Set<String> novelSet = util.getHtmlAttr(crawler.getCrawlerUrl(), null, config.get(2).getSelector(),
					config.get(2).getNum(), config.get(2).getAttrName(), config.get(2).getReg(),
					config.get(2).getHeadAppendResult(), config.get(2).getTailAppendResult(),
					config.get(2).getReplaceResult(), config.get(2).getRegGroupNum());
			map.put("novelNumber", novelSet.size());
			// System.out.println("类型页:" + novelTypeLink + ",共抓取小说：" + novelSet.size() +
			// "本");
			// 访问每一个小说页面
			for (String novelLink : novelSet) {
				JsoupUtil jsoupUtil = new JsoupUtil(novelLink);
				Document doc = jsoupUtil.getDoc();
				Novel novel = new Novel();
				// 小说名称
				Set<String> nameSet = util.getHtmlAttr(null, doc, config.get(3).getSelector(), config.get(3).getNum(),
						config.get(3).getAttrName(), config.get(3).getReg(), config.get(3).getHeadAppendResult(),
						config.get(3).getTailAppendResult(), config.get(3).getReplaceResult(),
						config.get(3).getRegGroupNum());
				for (String name : nameSet) {
					novel.setName(name);
				}

				// 作者
				Set<String> authorSet = util.getHtmlAttr(null, doc, config.get(4).getSelector(), config.get(4).getNum(),
						config.get(4).getAttrName(), config.get(4).getReg(), config.get(4).getHeadAppendResult(),
						config.get(4).getTailAppendResult(), config.get(4).getReplaceResult(),
						config.get(4).getRegGroupNum());
				for (String author : authorSet) {
					novel.setAuthor(author);
				}

				// 类型名称
				Set<String> typeNameSet = util.getHtmlAttr(null, doc, config.get(5).getSelector(),
						config.get(5).getNum(), config.get(5).getAttrName(), config.get(5).getReg(),
						config.get(5).getHeadAppendResult(), config.get(5).getTailAppendResult(),
						config.get(5).getReplaceResult(), config.get(5).getRegGroupNum());
				for (String typeName : typeNameSet) {
					novel.setTypeName(typeName);
				}

				// 描述
				Set<String> descriptionSet = util.getHtmlAttr(null, doc, config.get(6).getSelector(),
						config.get(6).getNum(), config.get(6).getAttrName(), config.get(6).getReg(),
						config.get(6).getHeadAppendResult(), config.get(6).getTailAppendResult(),
						config.get(6).getReplaceResult(), config.get(6).getRegGroupNum());
				for (String description : descriptionSet) {
					novel.setDescription(description);
				}

				// 主图地址
				Set<String> mainImageSet = util.getHtmlAttr(null, doc, config.get(7).getSelector(),
						config.get(7).getNum(), config.get(7).getAttrName(), config.get(7).getReg(),
						config.get(7).getHeadAppendResult(), config.get(7).getTailAppendResult(),
						config.get(7).getReplaceResult(), config.get(7).getRegGroupNum());
				for (String mainImage : mainImageSet) {
					novel.setMainImage(mainImage);
				}

				// 状态
				Set<String> statusSet = util.getHtmlAttr(null, doc, config.get(8).getSelector(), config.get(8).getNum(),
						config.get(8).getAttrName(), config.get(8).getReg(), config.get(8).getHeadAppendResult(),
						config.get(8).getTailAppendResult(), config.get(8).getReplaceResult(),
						config.get(8).getRegGroupNum());
				for (String status : statusSet) {
					novel.setStatus(status);
				}

				// 全部章节列表 过滤
				Set<String> chapterListSet = util.getHtmlAttr(null, doc, config.get(9).getSelector(),
						config.get(9).getNum(), config.get(9).getAttrName(), config.get(9).getReg(),
						config.get(9).getHeadAppendResult(), config.get(9).getTailAppendResult(),
						config.get(9).getReplaceResult(), config.get(9).getRegGroupNum());
				for (String chapterListHtml : chapterListSet) {
					Document chapterListDoc = Jsoup.parse(chapterListHtml);
					// 全部章节
					Set<String> chapterSet = util.getHtmlAttr(null, chapterListDoc, config.get(10).getSelector(),
							config.get(10).getNum(), config.get(10).getAttrName(), config.get(10).getReg(),
							config.get(10).getHeadAppendResult(), config.get(10).getTailAppendResult(),
							config.get(10).getReplaceResult(), config.get(10).getRegGroupNum());
					for (String chapterA : chapterSet) {
						NovelChapterList chapterList = new NovelChapterList();
						Document chapterDoc = Jsoup.parse(chapterA);
						Set<String> chapterLinkSet = util.getHtmlAttr(null, chapterDoc, config.get(11).getSelector(),
								config.get(11).getNum(), config.get(11).getAttrName(), config.get(11).getReg(),
								config.get(11).getHeadAppendResult(), config.get(11).getTailAppendResult(),
								config.get(11).getReplaceResult(), config.get(11).getRegGroupNum());
						for (String chapterLink : chapterLinkSet) {
							chapterList.setChapterLink(chapterLink);
						}

						Set<String> chapterNameSet = util.getHtmlAttr(null, chapterDoc, config.get(12).getSelector(),
								config.get(12).getNum(), config.get(12).getAttrName(), config.get(12).getReg(),
								config.get(12).getHeadAppendResult(), config.get(12).getTailAppendResult(),
								config.get(12).getReplaceResult(), config.get(12).getRegGroupNum());
						for (String chapterName : chapterNameSet) {
							chapterList.setChapterName(chapterName);
						}
						map.put("chapterList", chapterList);
						break;
					}
				}
				map.put("novel", novel);
				break;
			}
			break;
		}
		return map;
	}

	@Override
	public void addCrawler(Crawler crawler) {
		crawlerDao.addCrawler(crawler);
		String configNames[] = { "小说列表页", "小说页", "小说名称", "小说作者", "小说类型", "小说描述", "小说主图地址", "小说状态", "小说列表结构", "小说章节标签",
				"小说章节地址", "小说章节名称", "小说章节详情" };
		int i = 1;
		for (String configName : configNames) {
			CrawlerConfig config = new CrawlerConfig();
			config.setCrawlerId(crawler.getId() + "");
			config.setConfigName(configName);
			config.setSort(i);
			// 执行添加数据库
			crawlerConfigDao.addCrawlerConfig(config);
			i++;
		}
	}

	@Override
	public List<Crawler> selectCrawlerByPage(int page, int limit) {
		int start = (page - 1) * limit;
		int end = limit;
		return crawlerDao.selectCrawlerByPage(start, end);
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

	@Override
	public void updateCrawler(Crawler crawler) {
		crawlerDao.updateCrawler(crawler);
	}

	@Override
	public void createJson(Object object, String savePath) {
		JSONArray jsonArray = new JSONArray();
		if (object instanceof List) {
			jsonArray.addAll((List) object);
		} else {
			jsonArray.add(object);
		}
		BufferedWriter bufferedWriter = null;
		String dirPath = savePath.substring(0, savePath.lastIndexOf(File.separator));
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(new File(savePath)));
			bufferedWriter.write(jsonArray.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}