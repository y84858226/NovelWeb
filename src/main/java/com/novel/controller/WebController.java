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

	@RequestMapping("addBaiduIndex")
	public String addBaiduIndex(String webUrl, String addUrl, String token) {
		service.addBaiduIndex(webUrl, addUrl + "&token=" + token);
		return "<h1>百度检索录入成功！</h1>";
	}
}
