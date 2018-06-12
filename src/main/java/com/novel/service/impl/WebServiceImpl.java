package com.novel.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.dao.NovelDao;
import com.novel.dao.NovelTypeDao;
import com.novel.service.WebService;

@Service
public class WebServiceImpl implements WebService {

	@Autowired
	NovelDao novelDao;

	@Autowired
	NovelTypeDao novelTypeDao;

	/**
	 * 百度链接推送
	 * 
	 * 首页、类型页、小说页面、章节页面
	 * 
	 * @param PostUrl
	 * @param Parameters
	 * @return
	 */
	public String addBaiduIndex(String webUrl, String addUrl, String updateUrl) {
		/*
		 * 首页
		 */
		
		/*
		 *类型页 
		 */
		
		/*
		 * 小说页面
		 */
		
		/*
		 * 章节页面
		 */
		return updateUrl;
	}

	@Override
	public String Post(String indexUrl, String baiduUrl) {
		String result = "";
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			// 建立URL之间的连接
			URLConnection conn = new URL(baiduUrl).openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Host", "data.zz.baidu.com");
			conn.setRequestProperty("User-Agent", "curl/7.12.1");
			conn.setRequestProperty("Content-Length", "83");
			conn.setRequestProperty("Content-Type", "text/plain");

			// 发送POST请求必须设置如下两行
			conn.setDoInput(true);
			conn.setDoOutput(true);

			// 获取conn对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(indexUrl.trim());
			// 进行输出流的缓冲
			out.flush();
			// 通过BufferedReader输入流来读取Url的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
