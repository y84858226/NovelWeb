package com.novel.background.pojo;

public class NovelType {
	private int id;
	private String typeName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public NovelType(int id, String typeName) {
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
