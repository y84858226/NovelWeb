package com.novel.background.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

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
		// 设置爬虫的页面
		JsoupUtil jsoupUtil = new JsoupUtil(url);
		Document doc = jsoupUtil.getDoc();
		// 获取全部的link
		Elements elementsA = doc.select("a");
		//存放类型页的link
		Set<String> novelTypeSet = new HashSet<String>();
		// 设置小说类型页的正则
		Pattern typeLink = Pattern.compile("^//www.biquge5200.cc/[A-Za-z]+/$");
		// 循环访问全部a标签
		for (Element elementa : elementsA) {
			String aLink = elementa.attr("href");
			Matcher matcher = typeLink.matcher(aLink);
			boolean b = matcher.matches();
			if (b) {
				//是否开启字符串替换 变为标准的http://
				if(true) {
					aLink=aLink.replace("//", "https://");
				}
				novelTypeSet.add(aLink);
			}
		}
		
//		for (String string : novelTypeSet) {
//			System.out.println(string);
//		}
		
		//存放小说的link
		Set<String> novelSet = new HashSet<String>();
		// 读取每一个类型页
		for (String novelTypeLink : novelTypeSet) {
			jsoupUtil = new JsoupUtil(novelTypeLink);
			doc = jsoupUtil.getDoc();
			//获取类型页里面的全部link
			elementsA = doc.select("a");
			//设置小说的正则
			Pattern novelLink = Pattern.compile("^https://www.biquge5200.cc/\\d+_\\d+/$");
			// 循环访问类型页的全部a标签
			for (Element elementa : elementsA) {
				String aLink = elementa.attr("href");
				Matcher matcher = novelLink.matcher(aLink);
				boolean b = matcher.matches();
				if (b) {
					novelSet.add(aLink);
				}
			}
			//记录日志
		}
		System.out.println("共抓取小说："+novelSet.size()+"本");
	}

}