package com.novel.background.pojo;

public class CrawlerConfig {
	private int id;
	private String configId;
	private String configName;
	private String select;
	private String num;
	private String attrName;
	private String reg;
	private String regGroupNum;
	private String appendResult;
	private int sort;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}
	public String getRegGroupNum() {
		return regGroupNum;
	}
	public void setRegGroupNum(String regGroupNum) {
		this.regGroupNum = regGroupNum;
	}
	public String getAppendResult() {
		return appendResult;
	}
	public void setAppendResult(String appendResult) {
		this.appendResult = appendResult;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public CrawlerConfig(int id, String configId, String configName, String select, String num, String attrName,
			String reg, String regGroupNum, String appendResult, int sort) {
		this.id = id;
		this.configId = configId;
		this.configName = configName;
		this.select = select;
		this.num = num;
		this.attrName = attrName;
		this.reg = reg;
		this.regGroupNum = regGroupNum;
		this.appendResult = appendResult;
		this.sort = sort;
	}
	public CrawlerConfig() {
		
	}
	@Override
	public String toString() {
		return "CrawlerConfig [id=" + id + ", configId=" + configId + ", configName=" + configName + ", select="
				+ select + ", num=" + num + ", attrName=" + attrName + ", reg=" + reg + ", regGroupNum=" + regGroupNum
				+ ", appendResult=" + appendResult + ", sort=" + sort + "]";
	}
	
}
