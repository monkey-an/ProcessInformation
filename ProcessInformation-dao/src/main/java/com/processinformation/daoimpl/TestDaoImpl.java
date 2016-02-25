package com.processinformation.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.processinformation.base.dbinstance.DBInstance;
import com.processinformation.dao.TestDao;
import com.processinformation.entity.test.TestEntity;


@Repository
@Qualifier("testDao")
public class TestDaoImpl implements TestDao {

	final String mapperNamespace="com.processinformation.mapper.test.";
	
	@Override
	public List<TestEntity> getAllTestData(DBInstance dins) {
		// TODO Auto-generated method stub
		return dins.selectList(mapperNamespace+"getAllTestData", null);
	}

	@Override
	public int insertTestData(DBInstance dins, TestEntity entity) {
		// TODO Auto-generated method stub
		return dins.insert(mapperNamespace+"insertTestData", entity);
	}

	@Override
	public int updateTestData(DBInstance dins, TestEntity entity) {
		// TODO Auto-generated method stub
		return dins.update(mapperNamespace+"updateTestData", entity);
	}

	@Override
	public int deleteTestDate(DBInstance dins, long id) {
		// TODO Auto-generated method stub
		return dins.delete(mapperNamespace+"deleteTestDate", id);
	}
	
}
