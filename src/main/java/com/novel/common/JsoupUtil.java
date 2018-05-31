package com.novel.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JsoupUtil {
	private String url;
	private Document doc;

	public JsoupUtil() {

	}

	public JsoupUtil(String url) {
		this.url = url;
		init();
	}

	/**
	 * 初始化Jsoup
	 */
	public void init() {
		try {
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
					"org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setActiveXNative(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setTimeout(5000);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 很重要，设置支持AJAX
			webClient.getOptions().setUseInsecureSSL(true);
			HtmlPage rootPage = webClient.getPage(url);

			// 设置一个运行JavaScript的时间
			webClient.waitForBackgroundJavaScript(5000);
			String html = rootPage.asXml();
			doc = Jsoup.parse(html);
			// doc = Jsoup.connect(url)
			// .header("Accept-Encoding", "gzip, deflate")
			// .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101
			// Firefox/23.0")
			// .maxBodySize(0)
			// .timeout(600000)
			// .get();
		} catch (ScriptException e) {
			// e.printStackTrace();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Document getDoc() {
		return doc;
	}

	/**
	 * 
	 * @param url
	 *            不为空就创建新的爬虫
	 * @param document
	 *            可以传入doc作为爬虫数据
	 * @param select
	 *            选择器
	 * @param num
	 *            all为全部标签，也可以单独指定第num个标签
	 * @param attrName
	 *            标签的属性，可以设定html属性
	 * @param reg
	 *            获取自己想要的结果
	 * @param appendResult
	 *            为自己的结果做一个格式化，弥补正则的不足
	 * @param regGroupNum
	 *            设置读取哪一组的数据
	 * @return
	 */
	public Set<String> getHtmlAttr(String url, Document document, String select, String num, String attrName,
			String reg, String headAppendResult, String tailAppendResult, String replaceResult, int regGroupNum) {
		// 存放正则后的的attr
		Set<String> set = new LinkedHashSet<String>();
		try {
			if (url != null) {
				JsoupUtil jsoupUtil = new JsoupUtil(url);
				document = jsoupUtil.getDoc();
			}
			/**
			 * 获取全部属性
			 */
			if (num.equals("all")) {
				// 获取全部的attr
				Elements elements = document.select(select);
				// 设置正则
				Pattern pattern = Pattern.compile(reg);
				// 循环访问全部attr标签
				for (Element element : elements) {
					String attr = null;
					if (attrName.equals("html")) {
						attr = element.html();
					} else {
						attr = element.attr(attrName);
					}
					attr = attr.replaceAll("\n", "");
					attr = attr.replaceAll("\r", "");
					attr = attr.trim();
					Matcher matcher = pattern.matcher(attr);
					if (matcher.find()) {
						String result = headAppendResult + matcher.group(regGroupNum) + tailAppendResult;
						if (!replaceResult.equals("")) {
							String replaces[] = replaceResult.split(",");
							for (String groupReplace : replaces) {
								String replace[] = groupReplace.split("&&&&");
								result = result.replaceAll(replace[0], replace[1]);
							}
						}
						set.add(result);
					}
				}
			} else {
				/**
				 * 获取第num个属性
				 */
				Element element = document.select(select).get(Integer.parseInt(num));
				String attr = null;
				if (attrName.equals("html")) {
					attr = element.html();
				} else {
					attr = element.attr(attrName);
				}
				attr = attr.replaceAll("\n", "");
				attr = attr.replaceAll("\r", "");
				attr = attr.trim();
				Pattern pattern = Pattern.compile(reg);
				Matcher matcher = pattern.matcher(attr);
				if (matcher.find()) {
					String result = headAppendResult + matcher.group(regGroupNum) + tailAppendResult;
					if (!replaceResult.equals("")) {
						String replaces[] = replaceResult.split(",");
						for (String groupReplace : replaces) {
							String replace[] = groupReplace.split("&&&&");
							result = result.replaceAll(replace[0], replace[1]);
						}
					}
					set.add(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return set;
	}
}
