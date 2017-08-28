package com.basedao.dbtool;

import java.util.Map;

public class MapBean {
	private Map<String, String> data = null;

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "MapBean [data=" + data + "]";
	}
	
	
}
