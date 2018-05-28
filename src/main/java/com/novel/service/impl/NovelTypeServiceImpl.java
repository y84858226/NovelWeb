package com.novel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.dao.NovelDao;
import com.novel.dao.NovelTypeDao;
import com.novel.pojo.NovelType;
import com.novel.service.NovelTypeService;

@Service
public class NovelTypeServiceImpl implements NovelTypeService {

	@Autowired
	NovelDao novelDao;

	@Autowired
	NovelTypeDao novelTypeDao;

	@Override
	public void addNovelType(NovelType novelType) {
		novelTypeDao.addNovelType(novelType);
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

	@Override
	public void updateNovelType() {
		// 查询type
		List<NovelType> list = novelTypeDao.selectNovelTypeAll();
		for (NovelType novelType : list) {
			// 更新novel里面的type类型
			novelTypeDao.updateNovelType(novelType);
		}
	}

	@Override
	public void deleteNovelType(NovelType novelType) {
		novelTypeDao.deleteNovelType(novelType);
	}

}
