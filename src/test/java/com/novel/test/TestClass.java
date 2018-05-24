package com.novel.test;

import java.io.File;

import org.junit.Test;

public class TestClass {

	@Test
	public void testCreateFile() {
		String path = "src"+File.separator+"main"+File.separator+"webapp"+File.separator+"test";
		File file = new File(path);
		file.mkdir();
	}
}
