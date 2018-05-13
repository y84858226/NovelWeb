package com.novel.background.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PageListThread implements Runnable {
	AtomicInteger countNum;
	int maxNum;
	String[] datas;
	PropertiesUtil util;
	ImageUtil imageUtil;
	AtomicInteger chapterNum;
	String mxPath;
	public PageListThread(AtomicInteger countNum, int maxNum, String[] datas) {
		this.countNum = countNum;
		this.maxNum = maxNum;
		this.datas = datas;
		this.util = new PropertiesUtil("config.properties");
		this.imageUtil=new ImageUtil();
	}

	public void run() {
		while (true) {
			int mxNum = countNum.incrementAndGet();
			if (mxNum <= maxNum) {
				try {
					chapterNum=new AtomicInteger(0);
					String mhAttr[] = datas[mxNum - 1].split("'");
					String mhNumber = mhAttr[0].substring(0, mhAttr[0].length() - 1);
					String mhName = mhAttr[1];
					String newChaperName = mhAttr[3];
					String mhType = mhAttr[5];
					System.out.println(Thread.currentThread().getName() + "抓取编号：" + mhNumber + " 名称：" + mhName
							+ " 最新章节：" + newChaperName + " 分类：" + mhType);
					// 漫画的img封面
					String coverImageUrl = util.read("coverImageUrl") + mhNumber + ".jpg";
					System.out.println("漫画封面Image:" + coverImageUrl);
					imageUtil.downloadPicture(coverImageUrl, util.read("mhPath")+File.separator+mhName+File.separator+mhName+".jpg");
					// 网站首页
					String webUrl = util.read("webIndex");

					// 网站下面的漫画页
					String indexUrl = webUrl + "/" + mhNumber;

					// 获取漫画主页面
					JsoupUtil jsoupUtil = new JsoupUtil(indexUrl);
					Document doc = jsoupUtil.getDoc();

					// 漫画页的开始阅读
					Element readLinkElement = doc.select("li.read a").first();
					String readLink = readLinkElement.attr("href");
					readLink=readLink.substring(readLink.lastIndexOf(mhNumber)+mhNumber.length(), readLink.length());
					// 获取章节信息
					loadChapter(indexUrl,readLink,mhName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
	}

	public void loadChapter(String indexUrl,String chapLink,String mhName) {
		int chapNum=chapterNum.incrementAndGet();
		// 读取漫画的章页
		JsoupUtil j2 = new JsoupUtil(indexUrl+ chapLink);
		Document doc2 = j2.getDoc();
		//章节名称
		String chapterName=doc2.select(".mh_readtitle strong").first().html();
		System.out.println("第"+chapNum+"章："+chapterName);
		// 获取第一页的图片链接
		String indexImgUrl = doc2.select(".mh_comicpic img").first().attr("src");
//		System.out.println(indexImgUrl);
		String headImageUrl = indexImgUrl.substring(0, indexImgUrl.lastIndexOf("%2F")+3);
//		System.out.println("图片头链接:" + headImageUrl);
		String currentPage = indexImgUrl.substring(indexImgUrl.lastIndexOf("%2F") + 3, indexImgUrl.lastIndexOf(".jpg"));
//		System.out.println("当前页:" + currentPage);
		String tailImageUrl = indexImgUrl.substring(indexImgUrl.lastIndexOf(".jpg"), indexImgUrl.length());
//		System.out.println("图片尾链接:" + tailImageUrl);
		// 获取总页数
		String allPage = doc2.select(".mh_comicpic").first().html();
		allPage = allPage.substring(allPage.lastIndexOf("共") + 1, allPage.lastIndexOf("页")).trim();
//		System.out.println("总页数:" + allPage);
		// 获取全部图片
		for (int i = Integer.parseInt(currentPage); i <= Integer.parseInt(allPage); i++) {
			String chapterImageUrl=headImageUrl+i+tailImageUrl;
			try {
				chapterImageUrl = URLDecoder.decode(chapterImageUrl,"utf-8");
				System.out.println("第"+i+"页:"+chapterImageUrl);
				imageUtil.downloadPicture(chapterImageUrl,util.read("mhPath")+File.separator+mhName+File.separator+chapterName+File.separator+i+".jpg");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}  
		}
		// 获取下一章的链接
		Element nextElement = doc2.select(".mh_nextbook").first();
		if (nextElement != null) {
			String nextChapLink = "/"+nextElement.attr("href");
//			System.out.println("下一章链接:" + nextChapLink);
			loadChapter(indexUrl,nextChapLink,mhName);
		}
	}
}
