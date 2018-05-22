package com.novel.background.common;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtil {

	public  void downloadPicture(String imageUrl, String savePath) {
		URL url = null;
		try {
			url = new URL(imageUrl);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());
			String dirPath = savePath.substring(0, savePath.lastIndexOf(File.separator));
			File file = new File(dirPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			byte[] context = output.toByteArray();
			fileOutputStream.write(output.toByteArray());
			dataInputStream.close();
			fileOutputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void download(String urlString, String savePath) {
		HttpURLConnection urlConnection = null;
		try {
			// 生成一个URL对象
			URL url = new URL(urlString);
			// 打开URL
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)"); // 防止报403错误。
			// 获取服务器响应代码
			int responsecode = urlConnection.getResponseCode();
			if (responsecode == 200) {
				DataInputStream dataInputStream = new DataInputStream(urlConnection.getInputStream());
				String dirPath = savePath.substring(0, savePath.lastIndexOf(File.separator));
				File file = new File(dirPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length;
				while ((length = dataInputStream.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
				byte[] context = output.toByteArray();
				fileOutputStream.write(output.toByteArray());
				dataInputStream.close();
				fileOutputStream.close();
			} else {
				System.out.println("获取不到网页的源码，服务器响应代码为：" + responsecode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}
	}

}
