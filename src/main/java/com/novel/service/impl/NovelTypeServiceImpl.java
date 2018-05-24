package com.novel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.dao.NovelDao;
import com.novel.dao.NovelTypeDao;
import com.novel.pojo.Novel;
import com.novel.pojo.NovelType;
import com.novel.service.NovelTypeService;

@Service
public class NovelTypeServiceImpl implements NovelTypeService {

	@Autowired
	NovelDao novelDao;

	@Autowired
	NovelTypeDao novelTypeDao;

	@Override
	public void addNovelType() {
		List<Novel> list = novelDao.selectType();
		novelTypeDao.truncateNovelType();
		for (Novel novel : list) {
			NovelType novelType = new NovelType();
			novelType.setTypeName(novel.getTypeName());
			novelTypeDao.addNovelType(novelType);
		}
	}

	@Override
	public List<NovelType> selectNovelType(int page, int limit) {
		int start = (page - 1) * limit;
		int end = limit;
		return novelTypeDao.selectNovelType(start, end);
	}

	@Override
	public int selectNovelTypeCount() {
		return novelTypeDao.selectNovelTypeCount();
	}

}
