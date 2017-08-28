package com.basedao.dbtool;
 
public class Column extends Param {
	private String column;

	public Column(String column, Object v, ColumnType t) {
		this.column = column;
		super.setValue(v);
		super.setType(t);
	}

	public String getColumn() {
		return column;
	}

}
