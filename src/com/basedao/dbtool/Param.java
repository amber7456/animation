package com.basedao.dbtool;

public class Param {

	private ColumnType type;
	private Object value;

	protected Param() {

	}

	public Param(Object v, ColumnType t) {
		this.value = v;
		this.type = t;
	}
 

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ColumnType getType() {
		return type;
	}

	public void setType(ColumnType type) {
		this.type = type;
	}

}
