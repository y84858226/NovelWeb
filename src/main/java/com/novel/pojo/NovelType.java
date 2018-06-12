package com.novel.pojo;

public class NovelType {
	private Integer id;
	private String originalTypeName;
	private String typeName;
	private String isBaiduIndex;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOriginalTypeName() {
		return originalTypeName;
	}

	public void setOriginalTypeName(String originalTypeName) {
		this.originalTypeName = originalTypeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getIsBaiduIndex() {
		return isBaiduIndex;
	}

	public void setIsBaiduIndex(String isBaiduIndex) {
		this.isBaiduIndex = isBaiduIndex;
	}

	public NovelType(Integer id, String originalTypeName, String typeName, String isBaiduIndex) {
		this.id = id;
		this.originalTypeName = originalTypeName;
		this.typeName = typeName;
		this.isBaiduIndex = isBaiduIndex;
	}

	public NovelType() {
	}

	@Override
	public String toString() {
		return "NovelType [id=" + id + ", originalTypeName=" + originalTypeName + ", typeName=" + typeName
				+ ", isBaiduIndex=" + isBaiduIndex + "]";
	}

}
