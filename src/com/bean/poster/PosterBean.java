package com.bean.poster;

import java.util.Arrays;
import java.util.Date;

public class PosterBean {
	private String animation_id;
	private byte[] poster_hq;
	private byte[] poster_nq;
	private Date poster_upload_time;

	public String getAnimation_id() {
		return animation_id;
	}

	public void setAnimation_id(String animation_id) {
		this.animation_id = animation_id;
	}

	public byte[] getPoster_hq() {
		return poster_hq;
	}

	public void setPoster_hq(byte[] poster_hq) {
		this.poster_hq = poster_hq;
	}

	public byte[] getPoster_nq() {
		return poster_nq;
	}

	public void setPoster_nq(byte[] poster_nq) {
		this.poster_nq = poster_nq;
	}

	public Date getPoster_upload_time() {
		return poster_upload_time;
	}

	public void setPoster_upload_time(Date poster_upload_time) {
		this.poster_upload_time = poster_upload_time;
	}

	@Override
	public String toString() {
		return "PosterBean [animation_id=" + animation_id + ", poster_hq=" + Arrays.toString(poster_hq) + ", poster_nq="
				+ Arrays.toString(poster_nq) + ", poster_upload_time=" + poster_upload_time + "]";
	}

}
