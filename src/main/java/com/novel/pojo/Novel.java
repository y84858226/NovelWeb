package com.novel.pojo;

public class Novel {
	private Integer id;
	private String name;
	private String author;
	private String typeName;
	private String description;
	private String status;
	private String mainImage;
	private Integer lastChapterNum;
	private String createTime;
	private String updateTime;
	private Integer clickView;
	private String display;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getLastChapterNum() {
		return lastChapterNum;
	}

	public void setLastChapterNum(Integer lastChapterNum) {
		this.lastChapterNum = lastChapterNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getClickView() {
		return clickView;
	}

	public void setClickView(Integer clickView) {
		this.clickView = clickView;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public Novel(Integer id, String name, String author, String typeName, String description, String status,
			Integer lastChapterNum, String createTime, String updateTime, Integer clickView, String display) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.typeName = typeName;
		this.description = description;
		this.status = status;
		this.lastChapterNum = lastChapterNum;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clickView = clickView;
		this.display = display;
	}

	public Novel() {
	}

	@Override
	public String toString() {
		return "Novel [id=" + id + ", name=" + name + ", author=" + author + ", typeName=" + typeName + ", description="
				+ description + ", status=" + status + ", lastChapterNum=" + lastChapterNum + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", clickView=" + clickView + ", display=" + display + "]";
	}

}
