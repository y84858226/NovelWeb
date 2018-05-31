package com.novel.pojo;

public class NovelChapterList {
	private Integer id;
	private Integer crawlerConfigId;
	private Integer novelId;
	private Integer chapterNum;
	private String chapterName;
	private String chapterLink;
	private String filePath;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCrawlerConfigId() {
		return crawlerConfigId;
	}

	public void setCrawlerConfigId(Integer crawlerConfigId) {
		this.crawlerConfigId = crawlerConfigId;
	}

	public Integer getNovelId() {
		return novelId;
	}

	public void setNovelId(Integer novelId) {
		this.novelId = novelId;
	}

	public Integer getChapterNum() {
		return chapterNum;
	}

	public void setChapterNum(Integer chapterNum) {
		this.chapterNum = chapterNum;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterLink() {
		return chapterLink;
	}

	public void setChapterLink(String chapterLink) {
		this.chapterLink = chapterLink;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public NovelChapterList(Integer id, Integer crawlerConfigId, Integer novelId, Integer chapterNum,
			String chapterName, String chapterLink, String filePath) {
		this.id = id;
		this.crawlerConfigId = crawlerConfigId;
		this.novelId = novelId;
		this.chapterNum = chapterNum;
		this.chapterName = chapterName;
		this.chapterLink = chapterLink;
		this.filePath = filePath;
	}

	public NovelChapterList() {
	}

	@Override
	public String toString() {
		return "NovelChapterList [id=" + id + ", crawlerConfigId=" + crawlerConfigId + ", novelId=" + novelId
				+ ", chapterNum=" + chapterNum + ", chapterName=" + chapterName + ", chapterLink=" + chapterLink
				+ ", filePath=" + filePath + "]";
	}

}
