package com.processinformation.base.cache;

import java.util.Date;

import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
/**
 * 鍩轰簬Oscache瀹炵幇鐨勫唴瀛樼紦锟�榛樿瀛樺偍澶у皬锟�?024涓�?
 */
public class ObjectCache {
	private static final long serialVersionUID = -4397192926052141162L;
	private GeneralCacheAdministrator admin;

	public GeneralCacheAdministrator getAdmin() {
		return admin;
	}

	public void setAdmin(GeneralCacheAdministrator admin) {
		this.admin = admin;
	}

	/**
	 * 璁剧疆鍐呭瓨缂撳�?
	 * 
	 * @param key
	 *            鍐呭瓨缂撳瓨鐨勯�?
	 * @param value
	 *            鍐呭瓨缂撳瓨鐨勶�??
	 * @return 鍐呭瓨缂撳瓨鏄惁璁剧疆鎴愬�?
	 */
	public boolean put(String key, Object value) {
		try {
			admin.putInCache(key, value);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 鍒犻櫎鍐呭瓨缂撳瓨涓殑瀵硅�?
	 * 
	 * @param key
	 *            鍐呭瓨缂撳瓨鐨刱ey
	 * @return 鍒犻櫎鎿嶄綔鏄惁鎴愬姛
	 */
	public boolean remove(String key) {
		try {
			admin.flushEntry(key);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 鍒犻櫎鍐呭瓨缂撳瓨涓璬ate鍙傛暟琛ㄧず鐨勬椂闂翠箣鍚庡瓨鍏ョ殑锟�锟斤拷鏁版嵁
	 * 
	 * @param date
	 *            date琛ㄧず鐨勬椂闂村悗�?�樺叆鐨勬暟鎹兘浼氳鍒犻�?
	 * @return 鍒犻櫎鎿嶄綔鏄惁鎴愬姛
	 */
	public boolean removeAll(Date date) {
		try {
			admin.flushAll(date);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 鍒犻櫎鍐呭瓨缂撳瓨涓殑锟�锟斤拷鏁版嵁
	 * 
	 * @return 鍒犻櫎鎿嶄綔鏄惁鎴愬姛
	 */
	public boolean removeAll() {
		try {
			admin.flushAll();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 鑾峰彇缂撳瓨涓殑�?�硅�?,鍙璇ュ璞℃病鏈夊洜涓虹紦�?�樻坊鍔犵瓥鐣ワ�??娓呴�?,閮藉彲浠ヨ幏鍙栧�?,娌℃湁杩囨湡鏃堕�?
	 * 
	 * @param key
	 *            鍐呭瓨缂撳瓨鐨刱ey
	 * @return 鏍规嵁key寰楀埌鐨剉alue,濡傛灉娌℃湁瀵瑰簲鎴栧嚭鐜板紓甯歌繑鍥�?�ull
	 */
	public Object get(String key) {
		return get(key, CacheEntry.INDEFINITE_EXPIRY);
	}

	/**
	 * 鑾峰彇缂撳瓨涓殑�?�硅�?,鏈夎繃鏈熸椂锟�
	 * 
	 * @param key
	 *            鍐呭瓨缂撳瓨鐨刱ey
	 * @param refreshPeriod
	 *            缂撳瓨鐨勮繃鏈熸椂锟�浠ョ涓哄崟锟�鍗冲綋鍓嶆椂锟�缂撳瓨鍐欏叆鏃堕棿>refeshPeriod鐨勭紦�?�樻棤娉曡鍙栧�?
	 * @return
	 */
	public Object get(String key, int refreshPeriod) {
		try {
			return admin.getFromCache(key, refreshPeriod);
		} catch (NeedsRefreshException ex) {
			try {
				admin.cancelUpdate(key);
				return null;
			} catch (Exception exp) {
				return null;
			}
		}
	}
}
