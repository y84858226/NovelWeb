package com.novel.background.pojo;

public class NovelChapterDetail {
	private Integer id;
	private String novelChapterListId;
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNovelChapterListId() {
		return novelChapterListId;
	}

	public void setNovelChapterListId(String novelChapterListId) {
		this.novelChapterListId = novelChapterListId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NovelChapterDetail(Integer id, String novelChapterListId, String content) {
		this.id = id;
		this.novelChapterListId = novelChapterListId;
		this.content = content;
	}

	public NovelChapterDetail() {
	}

	@Override
	public String toString() {
		return "NovelChapterDetail [id=" + id + ", novelChapterListId=" + novelChapterListId + ", content=" + content
				+ "]";
	}

}
