package com.novel.pojo;

public class NovelType {
	private Integer id;
	private String originalTypeName;
	private String typeName;

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

	public NovelType(Integer id, String originalTypeName, String typeName) {
		this.id = id;
		this.originalTypeName = originalTypeName;
		this.typeName = typeName;
	}

	public NovelType() {
	}

	@Override
	public String toString() {
		return "NovelType [id=" + id + ", originalTypeName=" + originalTypeName + ", typeName=" + typeName + "]";
	}

}
