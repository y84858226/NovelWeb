package com.novel.background.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SystemMain {

	public static void main(String[] args) {
		/**
		 * 读取配置文件
		 */
		PropertiesUtil util = new PropertiesUtil("config.properties");
		// 线程数
		int threadNum = Integer.parseInt(util.read("threadNum"));
		// 抓取得页数
		int maxNum = Integer.parseInt(util.read("maxNum"));

		/**
		 * 读取全部漫画数据
		 */
		JsoupUtil jsoupUtil = new JsoupUtil(util.read("webIndex")+"/jsonp/allcomic.html");
		Document doc = jsoupUtil.getDoc();
		// get body
		Element body = doc.getElementsByTag("body").get(0);
		String html = body.html();
		// 获取全部漫画的json数据
		String data = html.substring(util.read("headJsonStr").length() + 2,
				html.length() - util.read("tailJsonStr").length() - 2);
		String datas[] = data.split("\\],\\[");
		System.out.println("漫画数量：" + datas.length);
		System.out.println("-----------------------------");
		/**
		 * 创建线程池 一个线程抓一个漫画
		 */
		if (threadNum != 0) {
			// 线程的原子性
			AtomicInteger countNum = new AtomicInteger();
			ExecutorService service = Executors.newFixedThreadPool(threadNum);
			for (int i = 0; i < threadNum; i++) {
				PageListThread thread = new PageListThread(countNum, maxNum, datas);
				service.submit(thread);
			}
			service.shutdown();
		}
	}

}
