package com.novel.pojo;

public class NovelType {
	private Integer id;
	private String typeName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public NovelType(Integer id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}

	public NovelType() {
	}

	@Override
	public String toString() {
		return "NovelType [id=" + id + ", typeName=" + typeName + "]";
	}

}
