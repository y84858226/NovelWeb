package com.novel.background.pojo;

public class NovelChapterList {
	private int id;
	private String novelId;
	private String chapterName;
	private String chapterNum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNovelId() {
		return novelId;
	}

	public void setNovelId(String novelId) {
		this.novelId = novelId;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterNum() {
		return chapterNum;
	}

	public void setChapterNum(String chapterNum) {
		this.chapterNum = chapterNum;
	}

	public NovelChapterList(int id, String novelId, String chapterName, String chapterNum) {
		this.id = id;
		this.novelId = novelId;
		this.chapterName = chapterName;
		this.chapterNum = chapterNum;
	}

	public NovelChapterList() {
	}

	@Override
	public String toString() {
		return "NovelChapterList [id=" + id + ", novelId=" + novelId + ", chapterName=" + chapterName + ", chapterNum="
				+ chapterNum + "]";
	}

}
