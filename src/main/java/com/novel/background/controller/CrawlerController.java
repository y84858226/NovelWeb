package com.novel.background.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.novel.background.common.JsoupUtil;
import com.novel.background.pojo.User;
import com.novel.background.service.UserService;

/**
 * 爬虫系统
 * 
 * @author Yan Hua
 *
 */
// 证明是controller层并且返回json
@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.novel.background.service" }) // 添加的注解
public class CrawlerController {
	// 依赖注入
	@Autowired
	UserService userService;

	/**
	 * @RestController代表这个类是用Restful风格来访问的，如果是普通的WEB页面访问跳转时，我们通常会使用@Controller value
	 *                                                                         =
	 *                                                                         "/users/{username}"
	 *                                                                         代表访问的URL是"http://host:PORT/users/实际的用户名"
	 *                                                                         method
	 *                                                                         =
	 *                                                                         RequestMethod.GET
	 *                                                                         代表这个HTTP请求必须是以GET方式访问
	 *                                                                         consumes="application/json"
	 *                                                                         代表数据传输格式是json
	 * @PathVariable将某个动态参数放到URL请求路径中
	 * @RequestParam指定了请求参数名称
	 */
	@RequestMapping(value = "queryU/{username}", method = RequestMethod.GET)
	public List<User> queryProduct(@PathVariable String username, HttpServletResponse httpServletResponse) {
		System.out.println(username);
		List<User> ulist = userService.queryUserByUserName(username);
		return ulist;
	}

	@ResponseBody
	@RequestMapping("hello")
	public String hello() {
		return "hello";
	}

	@ResponseBody
	@RequestMapping("runCrawler")
	public void runCrawler() {

	}

	public static void main(String[] args) {
		getNovelLink("https://www.biquge5200.cc/");
	}

	/**
	 * 获取小说的全部信息
	 */
	public static void getNovelLink(String url) {
		/**
		 * 第一步 查找小说列表页
		 */
		// 存放类型页的link
		Set<String> novelTypeSet = getHtmlAttr(url, null, "a", "all", "href", "www.biquge5200.cc/[A-Za-z]+/$",
				"https://",0);
		/**
		 * 第二步 查找小说页
		 */
		// 存放小说的link
		for (String novelTypeLink : novelTypeSet) {
			Set<String> novelSet = getHtmlAttr(novelTypeLink, null, "a", "all", "href", "www.biquge5200.cc/\\d+_\\d+/$",
					"https://",0);
			System.out.println("类型页:" + novelTypeLink + ",共抓取小说：" + novelSet.size() + "本");

			// 访问每一个小说页面
			for (String novelLink : novelSet) {
				JsoupUtil jsoupUtil = new JsoupUtil(novelLink);
				Document doc = jsoupUtil.getDoc();
				// 小说地址
				System.out.println("url:" + novelLink);

				// 小说名称
				Set<String> nameSet = getHtmlAttr(null, doc, "#info h1", "0", "html", "[\\s\\S]*", "",0);
				for (String name : nameSet) {
					System.out.println("小说:" + name);
				}

				// 作者
				Set<String> authorSet = getHtmlAttr(null, doc, "#info p", "0", "html", "([^：]+)$", "",0);
				for (String author : authorSet) {
					System.out.println("作者:" + author);
				}

				// 类型名称
				Set<String> typeNameSet = getHtmlAttr(null, doc, ".con_top a", "2", "html", "[\\s\\S]*", "",0);
				for (String typeName : typeNameSet) {
					System.out.println("类型名称:" + typeName);
				}

				// 表述
				Set<String> descriptionSet = getHtmlAttr(null, doc, "#intro p", "0", "html", "[\\s\\S]*", "",0);
				for (String description : descriptionSet) {
					System.out.println("描述:" + description);
				}

				// 主图地址
				Set<String> mainImageSet = getHtmlAttr(null, doc, "#fmimg img", "0", "src", "[\\s\\S]*", "",0);
				for (String mainImage : mainImageSet) {
					System.out.println("主图地址:" + mainImage);
				}
				// 全部章节
				Set<String> chapterSet = getHtmlAttr(null, doc, "#list dd", "all", "html", "[\\s\\S]*", "",0);
				for (String chapterA : chapterSet) {
					Document chapterDoc = Jsoup.parse(chapterA);
					Set<String> chapterLinkSet = getHtmlAttr(null, chapterDoc, "a", "0", "href", "[\\s\\S]*", "",0);
					for (String chapterLink : chapterLinkSet) {
						System.out.println("章节地址:" + chapterLink);
					}

					Set<String> chapterNumSet = getHtmlAttr(null, chapterDoc, "a", "0", "html", "第(.*)章", "",1);
					for (String chapterNum : chapterNumSet) {
						System.out.println("章节编号:" + chapterNum);
					}

					Set<String> chapterNameSet = getHtmlAttr(null, chapterDoc, "a", "0", "html", "[\\s\\S]*", "",0);
					for (String chapterName : chapterNameSet) {
						System.out.println("章节名称:" + chapterName);
					}
					break;
				}

				break;
			}
			break;
		}
	}

	/**
	 * 
	 * @param url
	 *            不为空就创建新的爬虫
	 * @param doc
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
	 * @param groupNum
	 *            设置读取哪一组的数据
	 * @return
	 */
	public static Set<String> getHtmlAttr(String url, Document doc, String select, String num, String attrName,
			String reg, String appendResult, int groupNum) {
		if (url != null) {
			JsoupUtil jsoupUtil = new JsoupUtil(url);
			doc = jsoupUtil.getDoc();
		}
		// 存放正则后的的attr
		Set<String> set = new HashSet<String>();
		/**
		 * 获取全部属性
		 */
		if (num.equals("all")) {
			// 获取全部的attr
			Elements elements = doc.select(select);
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
				Matcher matcher = pattern.matcher(attr);
				if (matcher.find()) {
					set.add(appendResult + matcher.group(groupNum));
				}
			}
		} else {
			/**
			 * 获取第num个属性
			 */
			Element element = doc.select(select).get(Integer.parseInt(num));
			String attr = null;
			if (attrName.equals("html")) {
				attr = element.html();
			} else {
				attr = element.attr(attrName);
			}
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(attr);
			if (matcher.find()) {
				set.add(appendResult + matcher.group(groupNum));
			}
		}
		return set;
	}
}