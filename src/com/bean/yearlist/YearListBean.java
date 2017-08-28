package com.bean.yearlist;

import java.util.List;
import java.util.Map;

import com.basedao.dbtool.MapBean;

public class YearListBean {
	private String year;
	private List<String> yearList;
	private Map<String, String> count;
	private List<MapBean> animationList;
	private List<MapBean> fuyuList;
	private List<MapBean> haruList;
	private List<MapBean> natsuList;
	private List<MapBean> akiList;
	private List<MapBean> ovaList;
	private List<MapBean> movieList;
	private List<MapBean> otherList;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Map<String, String> getCount() {
		return count;
	}

	public void setCount(Map<String, String> count) {
		this.count = count;
	}

	public List<MapBean> getAnimationList() {
		return animationList;
	}

	public void setAnimationList(List<MapBean> animationList) {
		this.animationList = animationList;
	}

	public List<MapBean> getFuyuList() {
		return fuyuList;
	}

	public void setFuyuList(List<MapBean> fuyuList) {
		this.fuyuList = fuyuList;
	}

	public List<MapBean> getHaruList() {
		return haruList;
	}

	public void setHaruList(List<MapBean> haruList) {
		this.haruList = haruList;
	}

	public List<MapBean> getNatsuList() {
		return natsuList;
	}

	public void setNatsuList(List<MapBean> natsuList) {
		this.natsuList = natsuList;
	}

	public List<MapBean> getAkiList() {
		return akiList;
	}

	public void setAkiList(List<MapBean> akiList) {
		this.akiList = akiList;
	}

	public List<MapBean> getOvaList() {
		return ovaList;
	}

	public void setOvaList(List<MapBean> ovaList) {
		this.ovaList = ovaList;
	}

	public List<MapBean> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<MapBean> movieList) {
		this.movieList = movieList;
	}

	public List<MapBean> getOtherList() {
		return otherList;
	}

	public void setOtherList(List<MapBean> otherList) {
		this.otherList = otherList;
	}

	public List<String> getYearList() {
		return yearList;
	}

	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}

	@Override
	public String toString() {
		return "YearListBean [year=" + year + ", yearList=" + yearList + ", count=" + count + ", animationList="
				+ animationList + ", fuyuList=" + fuyuList + ", haruList=" + haruList + ", natsuList=" + natsuList
				+ ", akiList=" + akiList + ", ovaList=" + ovaList + ", movieList=" + movieList + ", otherList="
				+ otherList + "]";
	}

}
