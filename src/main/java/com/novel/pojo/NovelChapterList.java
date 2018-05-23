package com.novel.pojo;

public class NovelChapterList {
	private Integer id;
	private String novelId;
	private String chapterName;
	private String chapterLink;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getChapterLink() {
		return chapterLink;
	}

	public void setChapterLink(String chapterLink) {
		this.chapterLink = chapterLink;
	}

	public NovelChapterList(Integer id, String novelId, String chapterName, String chapterLink) {
		this.id = id;
		this.novelId = novelId;
		this.chapterName = chapterName;
		this.chapterLink = chapterLink;
	}

	public NovelChapterList() {
	}

	@Override
	public String toString() {
		return "NovelChapterList [id=" + id + ", novelId=" + novelId + ", chapterName=" + chapterName + ", chapterLink="
				+ chapterLink + "]";
	}

}
