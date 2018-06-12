package com.novel.service;

public interface WebService {
	/**
	 * 
	 * @param webUrl
	 *            需要收录网站的域名
	 * @param addUrl
	 *            百度收录api的添加接口
	 * @param updateUrl
	 *            百度收录api的更新接口
	 * @return
	 */
	public void addBaiduIndex(String webUrl, String addUrl);

	/**
	 * 
	 * @param indexUrl
	 *            需要被收录的网址
	 * @param baiduUrl
	 *            百度的api接口地址
	 * @return
	 */
	public void Post(String indexUrl, String baiduUrl);

}
