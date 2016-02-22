package com.processinformation.base.dto;

/**
 * 解析xml输入数据对象,继承DTO
 */
public class InputObject extends DTO {
	/**
	 * 无参数的构�?函数,设置xml根节点为inputObject
	 */
	public InputObject() {
		super("inputObject");
	}

	/**
	 * 传入xml根节点名称的构�?函数
	 * 
	 * @param rootName
	 *            xml根节点名�??
	 */
	public InputObject(String rootName) {
		super(rootName);
	}

	/**
	 * 传入rows和row节点名称的构造函�??
	 * 
	 * @param rows
	 *            rows节点名称
	 * @param row
	 *            row节点名称
	 */
	public InputObject(String rows, String row) {
		super("inputObject", rows, row);
	}

	/**
	 * 传入xml根节点名�??rows和row节点名称的构造函�??
	 * 
	 * @param rootName
	 *            xml根节点名�??
	 * @param rows
	 *            rows节点名称
	 * @param row
	 *            row节点名称
	 */
	public InputObject(String rootName, String rows, String row) {
		super(rootName, rows, row);
	}
}