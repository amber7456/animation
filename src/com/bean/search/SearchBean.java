package com.bean.search;

public class SearchBean {

	private String startYear;
	private String endYear;
	private String animation_type;
	private String animation_name;
	private String animation_source;
	private String searchType;
	private String yearRange;
	private String isBD;
	private String disk_name;

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

	public String getAnimation_source() {
		return animation_source;
	}

	public void setAnimation_source(String animation_source) {
		this.animation_source = animation_source;
	}

	public String getYearRange() {
		return yearRange;
	}

	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}

	public String getIsBD() {
		return isBD;
	}

	public void setIsBD(String isBD) {
		this.isBD = isBD;
	}

	public String getDisk_name() {
		return disk_name;
	}

	public void setDisk_name(String disk_name) {
		this.disk_name = disk_name;
	}

	@Override
	public String toString() {
		return "SearchBean [startYear=" + startYear + ", endYear=" + endYear + ", animation_type=" + animation_type
				+ ", animation_name=" + animation_name + ", animation_source=" + animation_source + ", searchType="
				+ searchType + ", yearRange=" + yearRange + ", isBD=" + isBD + ", disk_name=" + disk_name + "]";
	}

}
