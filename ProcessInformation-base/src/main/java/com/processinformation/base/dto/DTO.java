package com.processinformation.base.dto;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.processinformation.base.log.LogWriter;
import com.processinformation.base.util.ConvertUtil;
import com.processinformation.base.util.HttpUtil;
import com.processinformation.base.util.StringUtil;


/**
 * xml瑙ｆ�?,杈撳嚭宸ュ叿 DTO绫诲皝瑁呬簡Dictionary m_content,m_content涓敭鐨勶拷?鍙兘鏄笁绉嶇被鍨嬬殑瀵硅�?
 * 绗竴绉嶆槸String,琛ㄧず鍙跺瓙鑺傜�? 绗簩绉嶆槸Dictionary,琛ㄧず闈炲彾鑺傜�? 绗笁绉嶆槸List,琛ㄧず鐩稿悓閿嚭鐜板娆＄殑鑺傜偣缁勬垚鐨勯泦锟�
 * 濡傛灉鐩稿悓鑺傜偣鍚嶅嚭鐜板娆＄殑鑺傜偣鍚嶇О鍙湁锟�锟斤�?,閫氳繃row鍙橀噺鎸囧畾杩欎釜鑺傜偣鐨勫悕锟�閫氳繃rows鑺傜偣鎸囧畾杩欎釜鑺傜偣鐨勭埗绾ц妭鐐瑰悕
 * 濡傛灉宓屽鍑虹幇鍚屼竴鑺傜偣鍚嶉噸澶嶅嚭鐜扮殑鎯呭�?, 鍙互閫氳繃addRowNode(String rows, String
 * row)鏂规�?,row鍙傛暟鎸囧畾杩欎釜閲嶅鍑虹幇鑺傜偣鐨勫悕锟�row鍙傛暟鎸囧畾杩欎釜鑺傜偣鐨勭埗绾ц妭鐐瑰悕 DTO鏃犳硶澶勭悊甯ml attribute
 * DTO�?寔杈撳嚭涓簒ml鎴杍son
 *
 */
public class DTO {
	protected Dictionary m_content = null;
	private String m_name = null;
	private String rows = "rows";
	private String row = "row";
	private HashMap<String, String> rowNodeHashMap = new HashMap<String, String>();

	public DTO() {
		this("DTO");
	}

	public DTO(String name, Dictionary dict) {
		this.m_name = name;
		this.m_content = dict;
	}

	public DTO(String name) {
		this.m_name = name;
		this.m_content = new Hashtable();
	}

	public DTO(String name, String rows, String row) {
		this.m_name = name;
		this.m_content = new Hashtable();
		this.rows = rows;
		this.row = row;
	}

	public void addRowNode(String rows, String row) {
		rowNodeHashMap.put(rows, row);
	}

	/**
	 * 鑾峰彇dto涓殑�?�硅�?
	 * 
	 * @param key
	 *            锟�
	 * @return 杩斿洖鍊肩被鍨嬩负Object,鍙兘鏄竴涓猯ist,map鎴朣tring
	 */
	public Object get(String key) {
		return this.m_content.get(key);
	}

	/**
	 * 璁剧疆dto涓殑�?�硅�?
	 * 
	 * @param key
	 *            锟�
	 * @param value
	 *            锟�
	 * @return
	 */
	public Object set(String key, Object value) {
		return this.m_content.put(key, value);
	}

	public Object Rows;

	/**
	 * 鑾峰彇鏍硅妭鐐�?�笅绗竴绾ц妭鐐逛腑鐨刲ist鑺傜�?
	 * 
	 * @return list鑺傜偣�?�硅�?
	 */
	public Object getRows() {
		return this.m_content.get(rows);
	}

	/**
	 * 鍦╠to涓缃甽ist鑺傜�?
	 * 
	 * @param rows
	 *            list鑺傜偣�?�硅�?
	 */
	public void setRows(Object rows) {
		this.m_content.put(this.rows, rows);
	}

	/**
	 * 瑙ｆ瀽xml string
	 * 
	 * @param xml
	 *            寰呰В鏋愮殑xml string
	 * @return 瑙ｆ瀽鏄惁鎴愬�?
	 */
	public boolean loadXML(String xml) {

		Document xdoc = null;
		try {
			xdoc = DocumentHelper.parseText(xml);
		} catch (Exception ex) {
			LogWriter.writeErrorLog(ex);
			return false;
		}

		return loadFromXDoc(xdoc);

	}

	public boolean loadFromXDoc(Document doc) {
		Element root = doc.getRootElement();
		if (root == null)
			return false;
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
			if (isLeafNode(element)) {
				this.m_content.put(element.getName(), element.getTextTrim());
			} else if (hasRowChild(element)) {
				List list = new ArrayList();
				visit_list(element, list);
				this.m_content.put(element.getName(), list);
			} else {
				Dictionary dic = new Hashtable();
				visit_dictionary(element, dic);
				this.m_content.put(element.getName(), dic);
			}
		}
		return true;
	}

	/**
	 * 閫氳繃http鑾峰彇xml骞惰В锟�
	 * 
	 * @param url
	 *            xml鍦板�?
	 * @param charset
	 *            xml缂栫�?
	 * @return 瑙ｆ瀽鏄惁鎴愬�?
	 */
	public boolean load(String url, Charset charset,int timeOut) {
		Document xdoc = null;
		try {
			String xml = HttpUtil.getPageHTML(url, charset, false,timeOut);
			xdoc = DocumentHelper.parseText(xml);
		} catch (Exception ex) {
			LogWriter.writeErrorLog(ex);
			return false;
		}

		return loadFromXDoc(xdoc);
	}
	
	public boolean load(String url, Charset charset) 
	{
		return load(url, charset,30000);
	}

	/**
	 * 寰楀埌鑺傜偣闆嗗悎銆愮洰鏍囪妭鐐瑰繀椤绘槸Dictionary锟�
	 * 
	 * @param dtoPath
	 * @return
	 */
	public Dictionary selectSingleNode(String dtoPath) {
		if (dtoPath == null) {
			return null;
		}
		if (dtoPath.isEmpty()) {
			return null;
		}
		String[] itemDictNames = dtoPath.split("/");
		if (itemDictNames.length == 0) {
			return null;
		}

		Dictionary currentDict = m_content;
		for (int i = 0; i < itemDictNames.length; i++) {
			Object currentChild = currentDict.get(itemDictNames[i]);
			if (currentChild == null) {
				return null;
			}
			if (currentChild instanceof Dictionary) {
				currentDict = (Dictionary) currentChild;
				if (i == itemDictNames.length - 1) {
					return currentDict;
				}
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 寰楀埌鑺傜偣闆嗗悎銆愮洰鏍囪妭鐐瑰繀椤绘槸list锟�
	 * 
	 * @param dtoPath
	 * @return
	 */
	public List<Dictionary> selectNodes(String dtoPath) {
		if (dtoPath == null) {
			return null;
		}
		if (dtoPath.isEmpty()) {
			return null;
		}
		String[] itemDictNames = dtoPath.split("/");
		if (itemDictNames.length == 0) {
			return null;
		}

		Dictionary currentDict = m_content;
		for (int i = 0; i < itemDictNames.length; i++) {
			Object currentChild = currentDict.get(itemDictNames[i]);
			if (currentChild == null) {
				return null;
			}
			if (currentChild instanceof Dictionary) {
				currentDict = (Dictionary) currentChild;
			} else if (currentChild instanceof List) {
				if (i == (itemDictNames.length - 1)) {
					return (List<Dictionary>) currentChild;
				}
				return null;
			} else {
				return null;
			}
		}
		return null;
	}

	private static boolean isLeafNode(Element element) {
		return element.elements().size() == 0;
	}

	private boolean hasRowChild(Element element) {
		// if (element == null) {
		// return false;
		// }
		// List childs = element.elements();
		// if (childs == null) {
		// return false;
		// }
		// if (childs.size() == 0) {
		// return false;
		// }
		// String listItemName = "";
		// for (Object object : childs) {
		// String elementName = ((Element) object).getName();
		// if (listItemName.isEmpty()) {
		// listItemName = elementName;
		// }
		// if (!listItemName.equals(elementName)) {
		// return false;
		// }
		// }
		// return true;
		return (element.getName().equals(rows) && ((Element) element.elements()
				.get(0)).getName().equals(row))
				|| (rowNodeHashMap.containsKey(element.getName()) && rowNodeHashMap
						.get(element.getName())
						.equals(((Element) element.elements().get(0)).getName()));
	}

	private void visit_list(Element element, List list) {
		for (Iterator i = element.elementIterator(); i.hasNext();) {
			Element subelement = (Element) i.next();
			if (isLeafNode(subelement)) {

				list.add(subelement.getTextTrim());
			} else if (hasRowChild(subelement)) {
				List list_item = new ArrayList();
				visit_list(subelement, list_item);
				list.add(list_item);
			} else {
				Dictionary dic_item = new Hashtable();
				visit_dictionary(subelement, dic_item);
				list.add(dic_item);
			}
		}
	}

	private void visit_dictionary(Element element, Dictionary dict) {
		for (Iterator i = element.elementIterator(); i.hasNext();) {
			Element subelement = (Element) i.next();
			if (isLeafNode(subelement)) {
				dict.put(subelement.getName(), subelement.getTextTrim());
			} else if (hasRowChild(subelement)) {
				List list_item = new ArrayList();
				visit_list(subelement, list_item);
				dict.put(subelement.getName(), list_item);
			} else {
				Dictionary dic_item = new Hashtable();
				visit_dictionary(subelement, dic_item);
				dict.put(subelement.getName(), dic_item);
			}
		}
	}

	/**
	 * 灏哾to瀵硅薄杞崲涓簒ml string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\" ?>");
		// sb.append(StringUtil.format("<%1$s>", this.m_name));
		sb.append(StringUtil.format("<{0}>", this.m_name));
		Enumeration keys = this.m_content.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			visit_object(sb, key, this.m_content.get(key));
		}
		// sb.append(StringUtil.format("</%1$s>", this.m_name));
		sb.append(StringUtil.format("</{0}>", this.m_name));
		return sb.toString();
	}

	private void visit_dictionary(StringBuilder sb, String key, Dictionary data) {
		// sb.append(StringUtil.format("<%1$s>", key));
		sb.append(StringUtil.format("<{0}>", key));
		Enumeration keys = data.keys();
		while (keys.hasMoreElements()) {
			String subkey = (String) keys.nextElement();
			visit_object(sb, subkey, data.get(subkey));
		}
		// sb.append(StringUtil.format("</%1$s>", key));
		sb.append(StringUtil.format("</{0}>", key));
	}

	private void visit_map(StringBuilder sb, String key, Map data) {
		// sb.append(StringUtil.format("<%1$s>", key));
		sb.append(StringUtil.format("<{0}>", key));
		Set<Map.Entry<String, Object>> sets = data.entrySet();
		for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			visit_object(sb, entry.getKey(), entry.getValue());
		}
		// sb.append(StringUtil.format("</%1$s>", key));
		sb.append(StringUtil.format("</{0}>", key));
	}

	private void visit_list(StringBuilder sb, String key, List data) {
		// sb.append(StringUtil.format("<%1$s>", key));
		sb.append(StringUtil.format("<{0}>", key));
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object item_data = iterator.next();
			visit_object(sb, row, item_data);
		}
		// sb.append(StringUtil.format("</%1$s>", key));
		sb.append(StringUtil.format("</{0}>", key));
	}

	private void visit_object(StringBuilder sb, String key, Object data) {
		String type_name = null;
		if (data instanceof Dictionary) {
			visit_dictionary(sb, key, (Dictionary) data);
		} else if (data instanceof Map) {
			visit_map(sb, key, (Map) data);
		} else if (data instanceof List) {
			visit_list(sb, key, (List) data);
		} else {
			// sb.append(StringUtil.format("<%1$s><![CDATA[%2$s]]></%1$s>", key,
			// data));
			sb.append(StringUtil
					.format("<{0}><![CDATA[{1}]]></{0}>", key, data));
		}
	}

	/**
	 * 灏哾to瀵硅薄杞崲涓簀son string
	 * 
	 * @return json string
	 */
	public String toJsonString() {
		return ConvertUtil.toJsonString(m_content);
	}
}
