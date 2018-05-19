package com.novel.background.pojo;

public class Novel {
	private Integer id;
	private String url;
	private String name;
	private String author;
	private String typeName;
	private String description;
	private String mainImage;
	private String createTime;
	private String updateTime;
	private Integer clickView;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Novel(Integer id, String url, String name, String author, String typeName, String description, String mainImage,
			String createTime, String updateTime, Integer clickView, String status) {
		this.id = id;
		this.url = url;
		this.name = name;
		this.author = author;
		this.typeName = typeName;
		this.description = description;
		this.mainImage = mainImage;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clickView = clickView;
		this.status = status;
	}

	public Novel() {
	}

	@Override
	public String toString() {
		return "Novel [id=" + id + ", url=" + url + ", name=" + name + ", author=" + author + ", typeName=" + typeName
				+ ", description=" + description + ", mainImage=" + mainImage + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", clickView=" + clickView + ", status=" + status + "]";
	}

}
