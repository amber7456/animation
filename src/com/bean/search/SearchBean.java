package com.bean.search;

public class SearchBean {

	private String startYear;
	private String endYear;
	private String animation_type;
	private String animation_name;
	private String searchType;

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getAnimation_type() {
		return animation_type;
	}

	public void setAnimation_type(String animation_type) {
		this.animation_type = animation_type;
	}

	public String getAnimation_name() {
		return animation_name;
	}

	public void setAnimation_name(String animation_name) {
		this.animation_name = animation_name;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	@Override
	public String toString() {
		return "SearchBean [startYear=" + startYear + ", endYear=" + endYear + ", animation_type=" + animation_type
				+ ", animation_name=" + animation_name + ", searchType=" + searchType + "]";
	}

}
