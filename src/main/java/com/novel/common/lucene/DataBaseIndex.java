package com.novel.common.lucene;

import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.novel.dao.IIndexQueryDao;
import com.novel.pojo.Novel;

/** 
 * Created by kainan on 2018/6/04. 
 * 对数据库数据做索引 
 */
public class DataBaseIndex {
	@Autowired
	private static IIndexQueryDao indexQueryDao;
	
	public static void createIndex(final String indexPath) {
		try {
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			IndexWriter writer  = new IndexWriter(dir, config);
			List<Novel> books = indexQueryDao.selectAllBooksToSearch();
			for(Novel book : books) {
				Document doc = new Document();
				String bookName = book.getName();
				String description = book.getDescription();
				String author = book.getAuthor();
				String classifyName = book.getTypeName();
				String bookStr = JSONObject.toJSONString(book);
				String SearchStr = bookName + " " + description + " " + author + " " + classifyName;
				doc.add(new TextField("SearchStr",SearchStr,Field.Store.YES));
				doc.add(new TextField("bookStr",bookStr,Field.Store.YES));
				writer.addDocument(doc);
				writer.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
