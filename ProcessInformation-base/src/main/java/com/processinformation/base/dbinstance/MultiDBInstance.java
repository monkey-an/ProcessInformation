package com.processinformation.base.dbinstance;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.processinformation.base.log.LogWriter;
import com.processinformation.base.util.ConvertUtil;

/**
 * @date 2013-6-4 涓嬪�?1:28:08 鍙栨ā鍒嗗簱鏁版嵁搴撴搷浣滄暟鎹簱杩炴帴,浜嬪姟锟�?
 */
public class MultiDBInstance {
	private HashMap<Integer, SqlSessionFactory> sqlSessionFactory;
	protected JdbcTransactionFactory transactionFactory;
	// protected SqlSession sqlSession;
	// protected Transaction transaction;
	protected ThreadLocal<SqlSession> threadLocalSqlSession = new ThreadLocal<SqlSession>();
	protected ThreadLocal<Transaction> threadLocalTransaction = new ThreadLocal<Transaction>();
	protected int modValue;

	/**
	 * @return the modValue
	 */
	public int getModValue() {
		return modValue;
	}

	/**
	 * @param modValue
	 *            the modValue to set
	 */
	public void setModValue(int modValue) {
		this.modValue = modValue;
	}

	/**
	 * @return the sqlSessionFactory
	 */
	public HashMap<Integer, SqlSessionFactory> getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	/**
	 * @param sqlSessionFactory
	 *            the sqlSessionFactory to set
	 */
	public void setSqlSessionFactory(
			HashMap<Integer, SqlSessionFactory> sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * @return the transactionFactory
	 */
	public JdbcTransactionFactory getTransactionFactory() {
		return transactionFactory;
	}

	/**
	 * @param transactionFactory
	 *            the transactionFactory to set
	 */
	public void setTransactionFactory(JdbcTransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}

	public boolean open(long id) {
		return open(id, true);
	}

	public boolean open(long id, boolean autoCommit) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		if (sqlSession == null) {
			try {
				sqlSession = sqlSessionFactory.get(
						ConvertUtil.toInt(id % modValue)).openSession();
				sqlSession.getConnection().setAutoCommit(autoCommit);
				threadLocalSqlSession.set(sqlSession);
				return true;
			} catch (Exception ex) {
				LogWriter.writeErrorLog(ex);
				return false;
			}
		}
		return true;
	}

	public boolean selectDB(long id) {
		try {
			SqlSession sqlSession = sqlSessionFactory.get(
					ConvertUtil.toInt(id % modValue)).openSession();
			sqlSession.getConnection().setAutoCommit(true);
			threadLocalSqlSession.set(sqlSession);
			return true;
		} catch (Exception ex) {
			LogWriter.writeErrorLog(ex);
			return false;
		}
	}

	public boolean close() {
		SqlSession sqlSession = threadLocalSqlSession.get();
		if (sqlSession != null) {
			try {
				sqlSession.close();
				sqlSession = null;
				threadLocalSqlSession.remove();
				return true;
			} catch (Exception ex) {
				LogWriter.writeErrorLog(ex);
				return false;
			}
		}
		return true;
	}

	public boolean beginTransaction() {
		SqlSession sqlSession = threadLocalSqlSession.get();
		if (sqlSession != null) {
			try {
				sqlSession.getConnection().setAutoCommit(false);
				Transaction transaction = transactionFactory
						.newTransaction(sqlSession.getConnection());
				threadLocalTransaction.set(transaction);
				return true;
			} catch (Exception ex) {
				LogWriter.writeErrorLog(ex);
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean commitTransaction() {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		if (sqlSession != null) {
			try {
				if (transaction != null) {
					transaction.commit();
					sqlSession.getConnection().setAutoCommit(true);
					transaction = null;
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
					threadLocalTransaction.remove();
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {
				LogWriter.writeErrorLog(ex);
				return false;
			} finally {
				if (sqlSession != null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
			}
		} else {
			return false;
		}
	}

	public boolean rollBackTransaction() {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		if (sqlSession != null) {
			try {
				if (transaction != null) {
					transaction.rollback();
					sqlSession.getConnection().setAutoCommit(true);
					transaction = null;
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {
				LogWriter.writeErrorLog(ex);
				return false;
			} finally {
				if (sqlSession != null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
			}
		} else {
			return false;
		}
	}

	public Object selectOne(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		if (sqlSession != null) {
			try {
				Object t = sqlSession.selectOne(statement, parameter);
				if (transaction == null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
				return t;
			} catch (Exception ex) {
				LogWriter.writeDbErrorLog(statement, ex);
				return null;
			} finally {
				if (transaction == null && sqlSession != null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
			}
		} else {
			return null;
		}
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		if (sqlSession != null) {
			try {
				List<E> e = sqlSession.selectList(statement, parameter);
				if (transaction == null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
				return e;
			} catch (Exception ex) {
				LogWriter.writeDbErrorLog(statement, ex);
				return null;
			} finally {
				if (transaction == null && sqlSession != null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
			}
		} else {
			return null;
		}
	}

	public int update(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		if (sqlSession != null) {
			try {
				int num = sqlSession.update(statement, parameter);
				if (transaction == null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
				return num;
			} catch (Exception ex) {
				LogWriter.writeDbErrorLog(statement, ex);
				return -1;
			} finally {
				if (transaction == null && sqlSession != null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
			}
		} else {
			return -1;
		}
	}

	public int insert(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		if (sqlSession != null) {
			try {
				int num = sqlSession.insert(statement, parameter);
				if (transaction == null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
				return num;
			} catch (Exception ex) {
				LogWriter.writeDbErrorLog(statement, ex);
				return -1;
			} finally {
				if (transaction == null && sqlSession != null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
			}
		} else {
			return -1;
		}
	}

	public int delete(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		if (sqlSession != null) {
			try {
				int num = sqlSession.delete(statement, parameter);
				if (transaction == null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
				return num;
			} catch (Exception ex) {
				LogWriter.writeDbErrorLog(statement, ex);
				return -1;
			} finally {
				if (transaction == null && sqlSession != null) {
					sqlSession.close();
					sqlSession = null;
					threadLocalSqlSession.remove();
				}
			}
		} else {
			return -1;
		}
	}
}
