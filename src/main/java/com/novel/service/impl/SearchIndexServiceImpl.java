package com.novel.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.dao.IIndexQueryDao;
import com.novel.pojo.Novel;
import com.novel.service.SearchIndexService;

@Service
public class SearchIndexServiceImpl implements SearchIndexService {

	@Autowired
	IIndexQueryDao dao;

	@Override
	public void createIndex(String indexPath) {
		try {
			File file = new File(indexPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			// Analyzer analyzer = new StandardAnalyzer();
			SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(dir, config);
			List<Novel> list = dao.selectAllBooksToSearch();
			for (Novel novel : list) {
				Integer id = novel.getId();
				String bookName = novel.getName();
				String description = novel.getDescription();
				String author = novel.getAuthor();
				String typeName = novel.getTypeName();
				Document doc = new Document();
				doc.add(new TextField("id", id + "", Field.Store.YES));
				doc.add(new TextField("bookName", bookName, Field.Store.YES));
				doc.add(new TextField("description", description, Field.Store.YES));
				doc.add(new TextField("author", author, Field.Store.YES));
				doc.add(new TextField("typeName", typeName, Field.Store.YES));
				// writer.addDocument(doc);
				writer.updateDocument(new Term("id"), doc);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Novel> searchData(String queryStr, String indexPath) {
		List<Novel> books = new ArrayList<>();
		Directory dir = null;
		try {
			dir = FSDirectory.open(Paths.get(indexPath));
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			// Analyzer analyzer = new StandardAnalyzer();
			SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
			// QueryParser parser = new QueryParser(fieldString, analyzer);
			QueryParser parser = new MultiFieldQueryParser(
					new String[] { "bookName", "description", "author", "typeName" }, analyzer);
			parser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = parser.parse(queryStr);
			int count = reader.maxDoc();// 所有文档数
			TopDocs hits = searcher.search(query, count); // 查找操作

			// 算分
			QueryScorer scorer = new QueryScorer(query);

			// 显示得分高的片段
			Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);

			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");

			// 第一个参数是对查到的结果进行实例化；第二个是片段得分（显示得分高的片段，即摘要）
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
			// 设置片段
			highlighter.setTextFragmenter(fragmenter);

			for (ScoreDoc scoreDoc : hits.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc); // 根据文档打分得到文档的内容
				String id = doc.get("id");
				String bookName = doc.get("bookName");
				String description = doc.get("description");
				String author = doc.get("author");
				String typeName = doc.get("typeName");
				if (bookName != null) {
					TokenStream tokenStream = analyzer.tokenStream("bookName", new StringReader(bookName));
					String highlightStr = highlighter.getBestFragment(tokenStream, bookName);
					if (highlightStr != null) {
						bookName = highlightStr;
					}
				}
				if (description != null) {
					TokenStream tokenStream = analyzer.tokenStream("description", new StringReader(description));
					String highlightStr = highlighter.getBestFragment(tokenStream, description);
					if (highlightStr != null) {
						description = highlightStr;
					}
				}
				if (author != null) {
					TokenStream tokenStream = analyzer.tokenStream("author", new StringReader(author));
					String highlightStr = highlighter.getBestFragment(tokenStream, author);
					if (highlightStr != null) {
						author = highlightStr;
					}
				}
				if (typeName != null) {
					TokenStream tokenStream = analyzer.tokenStream("typeName", new StringReader(typeName));
					String highlightStr = highlighter.getBestFragment(tokenStream, typeName);
					if (highlightStr != null) {
						typeName = highlightStr;
					}
				}
				Novel novel = new Novel();
				novel.setId(Integer.parseInt(id));
				novel.setName(bookName);
				novel.setDescription(description);
				novel.setAuthor(author);
				novel.setTypeName(typeName);
//				System.out.println(novel);
				books.add(novel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		return books;
	}
	public static void main(String[] args) {
		new SearchIndexServiceImpl().searchData("小镇", "C:\\Users\\Administrator\\git\\NovelWeb\\src\\main\\webapp\\data\\index");
	}
}
