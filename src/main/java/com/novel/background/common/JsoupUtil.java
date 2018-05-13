package com.novel.background.common;

import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JsoupUtil {
	String url;
	Document doc;

	/**
	 * 
	 * @param url
	 *            地址
	 */
	public JsoupUtil(String url) {
		this.url = url;
		init();
	}

	/**
	 * 初始化Jsoup
	 */
	public void init() {
		try {
			//构造一个webClient 模拟Chrome 浏览器
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
			//屏蔽日志信息
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
			        "org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
			//支持JavaScript
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setActiveXNative(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setTimeout(5000);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
			webClient.getOptions().setUseInsecureSSL(true);
			HtmlPage rootPage = webClient.getPage(url);
			
			//设置一个运行JavaScript的时间
			webClient.waitForBackgroundJavaScript(5000);
			String html = rootPage.asXml();
			doc = Jsoup.parse(html);
//			doc = Jsoup.connect(url)
//					.header("Accept-Encoding", "gzip, deflate")
//					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
//					.maxBodySize(0)
//					.timeout(600000)
//					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Document getDoc() {
		return doc;
	}
	
	/**
	 * 通过正则表达式获取需要的link
	 */
	public void getRegLink() {
		
	}
}
