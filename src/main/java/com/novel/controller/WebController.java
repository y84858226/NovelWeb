package com.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novel.service.WebService;

@RestController // 证明是controller层并且返回json
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class WebController {
	
	@Autowired
	WebService service;
	
	
	/*public static void main(String[] args) {
		String url = "http://data.zz.baidu.com/urls?site=www.5yege.com&token=HzU35eNxy14Klgpu";// 网站的服务器连接
		String[] param = { "http://5yege.com/index.html", "http://5yege.com/bookdetail.html?id=256" };
		String json = service.Post(url, param);// 执行推送方法
	}*/
	
	/**
	 * 
	 */
	@RequestMapping("addBaiduIndex")
	public void addBaiduIndex(String webUrl,String addUrl,String updateUrl) {
//		service.Post(PostUrl);
	}
}
