package com.processinformation.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.processinformation.base.dbinstance.DBInstance;
import com.processinformation.dao.TestDao;
import com.processinformation.entity.test.TestEntity;
import com.processinformation.service.TestService;

@Service
@Qualifier("testService")
public class TestServiceImpl implements TestService {

	@Autowired
	@Qualifier("testDao")
	private TestDao testDao;
	
	@Autowired
	@Qualifier("dinsTest")
	private DBInstance dinsTest;
	
	@Override
	public List<TestEntity> getAllTestData() {
		// TODO Auto-generated method stub
		return testDao.getAllTestData(dinsTest);
	}
	
}
