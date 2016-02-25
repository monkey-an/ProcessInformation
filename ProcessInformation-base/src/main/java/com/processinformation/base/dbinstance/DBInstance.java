package com.processinformation.base.dbinstance;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.processinformation.base.log.LogWriter;

/**
 * 鎿嶄綔鏁版嵁搴撹繛锟�浜嬪姟锟�灏佽浜哅ybatis SqlSessionFactory鍜孞dbcTransactionFactory.
 * 鐢变簬鏈被灏哠qlSessionFactory涓幏鍙栫殑SqlSession鍜孞dbcTransactionFactory涓幏鍙栫殑Transaction灏佽鍦ㄧ被
 * 锟�浣跨敤spring娉ㄥ叆璇ョ被锟�锟�锟斤拷涓洪潪鍗曚緥妯″紡,娉ㄥ叆璇ョ被鐨勪笂灞傚璞�?�篃锟�锟斤拷闈炲崟渚嬫ā锟�鍚﹀垯浼氭湁骞跺彂鍐茬獊闂�?
 */
public class DBInstance {
	protected SqlSessionFactory sqlSessionFactory;
	protected ThreadLocal<SqlSession> threadLocalSqlSession = new ThreadLocal<SqlSession>();
	protected ThreadLocal<Transaction> threadLocalTransaction = new ThreadLocal<Transaction>();
	// protected SqlSession sqlSession;
	protected JdbcTransactionFactory transactionFactory;

	// protected Transaction transaction;

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public SqlSession getConnection() {
		SqlSession sqlSession = threadLocalSqlSession.get();
		return sqlSession;
	}

	public JdbcTransactionFactory getTransactionFactory() {
		return transactionFactory;
	}

	public void setTransactionFactory(JdbcTransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}

	/**
	 * 鎵撳紑杩炴帴,榛樿鑷姩鎻愪氦浜嬪姟
	 * 
	 * @return 鎵撳紑杩炴帴鏄惁鎴愬姛,鍑虹幇寮傚父锟�杩斿洖false
	 */
	public boolean open() {
		return open(true);
	}

	/**
	 * 鎵撳紑杩炴帴,鏍规嵁autoCommit鍙傛暟鎸囧畾鏄惁鑷姩鎻愪氦浜嬪姟
	 * 
	 * @param autoCommit
	 *            鏄惁鑷姩鎻愪氦浜嬪姟
	 * @return 鎵撳紑杩炴帴鏄惁鎴愬姛,鍑虹幇寮傚父锟�杩斿洖false
	 */
	public boolean open(boolean autoCommit) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		if (sqlSession == null) {
			try {
				sqlSession = sqlSessionFactory.openSession(autoCommit);
				threadLocalSqlSession.set(sqlSession);
				return true;
			} catch (Exception ex) {
				LogWriter.writeErrorLog(ex);
				return false;
			}
		}
		return true;
	}

	/**
	 * 鍏抽棴杩炴帴
	 * 
	 * @return 鍏抽棴杩炴帴鏄惁鎴愬姛
	 */
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

	/**
	 * 鎵撳紑鏁版嵁搴撲簨锟�灏嗚嚜鍔ㄦ彁浜や簨鍔¤缃负false
	 * 
	 * @return 鎵撳紑浜嬪姟鏄惁鎴愬姛,鍑虹幇寮傚父杩斿洖false
	 */
	public boolean beginTransaction() {
		try {
			SqlSession sqlSession = threadLocalSqlSession.get();
			if (sqlSession == null) {
				sqlSession = sqlSessionFactory.openSession(true);
				threadLocalSqlSession.set(sqlSession);
			}
			sqlSession.getConnection().setAutoCommit(false);
			Transaction transaction = transactionFactory
					.newTransaction(sqlSession.getConnection());
			threadLocalTransaction.set(transaction);
			return true;
		} catch (Exception ex) {
			LogWriter.writeErrorLog(ex);
			return false;
		}
	}

	/**
	 * 鎻愪氦浜嬪姟,鎻愪氦�?�屾垚鍚庡皢浜嬪姟鑷姩鎻愪氦璁剧疆涓簍rue
	 * 
	 * @return 鎻愪氦浜嬪姟鏄惁鎴愬姛,鍑虹幇寮傚父杩斿洖false
	 */
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

	/**
	 * 鍥炴粴浜嬪姟,鍥炴粴鍚庡皢浜嬪姟鑷姩鎻愪氦璁剧疆涓簍rue
	 * 
	 * @return 鍥炴粴浜嬪姟鏄惁鎴愬姛,鍑虹幇寮傚父杩斿洖false
	 */
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

	/**
	 * 鎵цselect璇�?,杩斿洖鍊间负ORM鏄犲皠寰�?埌鐨勫锟�濡傛灉sql杩斿洖琛屾暟>1鍒欎細鎶涘嚭寮傚�? 鎶涘嚭寮傚父锟�杩斿洖null
	 * 濡傛灉sql杩斿洖鍑芥暟锟�,杩斿洖null
	 * 
	 * @param statement
	 *            sql mapper id
	 * @param parameter
	 *            鍙傛�?,鍙互涓哄熀锟�锟斤拷锟�pojo瀵硅薄鎴朚ap
	 * @return ORM鏄犲皠寰�?埌鐨勫锟�
	 */
	public Object selectOne(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		try {
			if (sqlSession == null) {
				sqlSession = sqlSessionFactory.openSession(true);
				threadLocalSqlSession.set(sqlSession);
			}
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
	}

	/**
	 * 鎵цselect璇�?,杩斿洖鍊间负ORM鏄犲皠寰�?埌鐨勫璞￠泦锟�? 鎶涘嚭寮傚父锟�杩斿洖null 濡傛灉sql杩斿洖鍑芥暟锟�,size=0鐨刲ist
	 * 
	 * @param statement
	 *            sql mapper id
	 * @param parameter
	 *            鍙傛�?,鍙互涓哄熀锟�锟斤拷锟�pojo瀵硅薄鎴朚ap
	 * @return
	 */
	public <E> List<E> selectList(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		try {
			if (sqlSession == null) {
				sqlSession = sqlSessionFactory.openSession(true);
				threadLocalSqlSession.set(sqlSession);
			}
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
	}

	/**
	 * 鎵цupdate璇�?
	 * 
	 * @param statement
	 *            sql mapper id
	 * @param parameter
	 *            鍙傛�?,鍙互涓哄熀锟�锟斤拷锟�pojo瀵硅薄鎴朚ap
	 * @return 鍙楀奖鍝嶈锟�寮傚父杩斿洖-1
	 */
	public int update(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		try {
			if (sqlSession == null) {
				sqlSession = sqlSessionFactory.openSession(true);
				threadLocalSqlSession.set(sqlSession);
			}
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
	}

	/**
	 * 鎵цinsert璇�?
	 * 
	 * @param statement
	 *            statement sql mapper id
	 * @param parameter
	 *            鍙傛�?,鍙互涓哄熀锟�锟斤拷锟�pojo瀵硅薄鎴朚ap
	 * @return 鍙楀奖鍝嶈锟�寮傚父杩斿洖-1
	 */
	public int insert(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		try {
			if (sqlSession == null) {
				sqlSession = sqlSessionFactory.openSession(true);
				threadLocalSqlSession.set(sqlSession);
			}
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
	}

	/**
	 * 鎵цdelete璇�?
	 * 
	 * @param statement
	 *            statement sql mapper id
	 * @param parameter
	 *            鍙傛�?,鍙互涓哄熀锟�锟斤拷锟�pojo瀵硅薄鎴朚ap
	 * @return 鍙楀奖鍝嶈锟�寮傚父杩斿洖-1
	 */
	public int delete(String statement, Object parameter) {
		SqlSession sqlSession = threadLocalSqlSession.get();
		Transaction transaction = threadLocalTransaction.get();
		try {
			if (sqlSession == null) {
				sqlSession = sqlSessionFactory.openSession(true);
				threadLocalSqlSession.set(sqlSession);
			}
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
	}
}
