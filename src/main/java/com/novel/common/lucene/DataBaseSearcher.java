package com.novel.common.lucene;

import java.io.IOException;
import org.apache.lucene.queryparser.classic.ParseException; 
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.alibaba.fastjson.JSONObject;
import com.novel.pojo.Novel;

/** 
 * Created by kainan on 2018/6/04. 
 * 对数据库数据做搜索
 */
public class DataBaseSearcher {
	 public static List<Novel> searchData(final String queryStr,final String indexPath) {
		 	List<Novel> books = new ArrayList<>();
	        Directory dir = null;  
	        try {  
	            dir = FSDirectory.open(Paths.get(indexPath));  
	            IndexReader reader = DirectoryReader.open(dir);  
	            IndexSearcher searcher = new IndexSearcher(reader);  
	            Analyzer analyzer = new StandardAnalyzer();  
	            String fieldString = "SearchStr";  
	            QueryParser parser = new QueryParser(fieldString, analyzer);  
	            parser.setDefaultOperator(QueryParser.AND_OPERATOR);  
	            Query query = parser.parse(queryStr);
	            int count = reader.maxDoc();//所有文档数
	            TopDocs hits = searcher.search(query,count); // 查找操作  
	            for(ScoreDoc scoreDoc : hits.scoreDocs) {  
	                Document doc = searcher.doc(scoreDoc.doc); // 根据文档打分得到文档的内容  
	                System.out.println(doc.get("bookStr")); // 找到文件后，输出路径 
	                Novel book = JSONObject.parseObject(doc.get("bookStr"), Novel.class);
	                books.add(book);
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (ParseException ex) {  
	            ex.printStackTrace();  
	        }
	        return books;
	    }  
}
