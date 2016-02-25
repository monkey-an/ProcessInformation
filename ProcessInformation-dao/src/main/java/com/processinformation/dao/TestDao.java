package com.processinformation.dao;

import java.util.List;

import com.processinformation.base.dbinstance.DBInstance;
import com.processinformation.entity.test.TestEntity;

public interface TestDao {
	List<TestEntity> getAllTestData(DBInstance dins);
	int insertTestData(DBInstance dins,TestEntity entity);
	int updateTestData(DBInstance dins,TestEntity entity);
	int deleteTestDate(DBInstance dins,long id);
}
