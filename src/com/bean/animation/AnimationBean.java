package com.bean.animation;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class AnimationBean {
	private String animation_id;// ID
	private String animation_name;// 动画名称
	private String animation_alias;// 动画别名
	private String animation_episode;// 集数
	private String animation_broadcast_time;// 播出时间
	private String animation_broadcast_season;// 播出季
	private String animation_type;// 类型
	private String animation_source;// 来源
	private String animation_cv;// 声优
	private String animation_staff;// 制作
	private String animation_intro;// 简介
	private String animation_detail_information;// 详细信息
	private String animation_remark;// 备注
	private String have_poster;// 是否有海报
	private String sequence;// 排序顺序

	private String[] resource_type;// 收录视频类型
	private String[] resource_format;// 收录视频格式
	private String[] resource_image_resolution;// 分辨率
	private String[] resource_sub;// 有无字幕
	private String[] resource_sub_type;// 字幕类型 内嵌 封装 外挂
	private String[] resource_sub_format;// 字幕格式 ass str
	private String[] resource_time;// 收率时间
	private String[] resource_address;// 存储地址
	private String[] resource_remark;// 备注

	private MultipartFile poster_hq;// 高清海报
	private MultipartFile poster_nq;// 普清海报
	private String poster_upload_time;// 上传时间

	private String posterUpdateFlag;// 图片改动标志

	public String getAnimation_id() {
		return animation_id;
	}

	public void setAnimation_id(String animation_id) {
		this.animation_id = animation_id;
	}

	public String getAnimation_name() {
		return animation_name;
	}

	public void setAnimation_name(String animation_name) {
		this.animation_name = animation_name;
	}

	public String getAnimation_alias() {
		return animation_alias;
	}

	public void setAnimation_alias(String animation_alias) {
		this.animation_alias = animation_alias;
	}

	public String getAnimation_episode() {
		return animation_episode;
	}

	public void setAnimation_episode(String animation_episode) {
		this.animation_episode = animation_episode;
	}

	public String getAnimation_broadcast_time() {
		return animation_broadcast_time;
	}

	public void setAnimation_broadcast_time(String animation_broadcast_time) {
		this.animation_broadcast_time = animation_broadcast_time;
	}

	public String getAnimation_broadcast_season() {
		return animation_broadcast_season;
	}

	public void setAnimation_broadcast_season(String animation_broadcast_season) {
		this.animation_broadcast_season = animation_broadcast_season;
	}

	public String getAnimation_type() {
		return animation_type;
	}

	public void setAnimation_type(String animation_type) {
		this.animation_type = animation_type;
	}

	public String getAnimation_source() {
		return animation_source;
	}

	public void setAnimation_source(String animation_source) {
		this.animation_source = animation_source;
	}

	public String getAnimation_cv() {
		return animation_cv;
	}

	public void setAnimation_cv(String animation_cv) {
		this.animation_cv = animation_cv;
	}

	public String getAnimation_staff() {
		return animation_staff;
	}

	public void setAnimation_staff(String animation_staff) {
		this.animation_staff = animation_staff;
	}

	public String getAnimation_intro() {
		return animation_intro;
	}

	public void setAnimation_intro(String animation_intro) {
		this.animation_intro = animation_intro;
	}

	public String getAnimation_detail_information() {
		return animation_detail_information;
	}

	public void setAnimation_detail_information(String animation_detail_information) {
		this.animation_detail_information = animation_detail_information;
	}

	public String getAnimation_remark() {
		return animation_remark;
	}

	public void setAnimation_remark(String animation_remark) {
		this.animation_remark = animation_remark;
	}

	public String getHave_poster() {
		return have_poster;
	}

	public void setHave_poster(String have_poster) {
		this.have_poster = have_poster;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String[] getResource_type() {
		return resource_type;
	}

	public void setResource_type(String[] resource_type) {
		this.resource_type = resource_type;
	}

	public String[] getResource_format() {
		return resource_format;
	}

	public void setResource_format(String[] resource_format) {
		this.resource_format = resource_format;
	}

	public String[] getResource_image_resolution() {
		return resource_image_resolution;
	}

	public void setResource_image_resolution(String[] resource_image_resolution) {
		this.resource_image_resolution = resource_image_resolution;
	}

	public String[] getResource_sub() {
		return resource_sub;
	}

	public void setResource_sub(String[] resource_sub) {
		this.resource_sub = resource_sub;
	}

	public String[] getResource_sub_type() {
		return resource_sub_type;
	}

	public void setResource_sub_type(String[] resource_sub_type) {
		this.resource_sub_type = resource_sub_type;
	}

	public String[] getResource_sub_format() {
		return resource_sub_format;
	}

	public void setResource_sub_format(String[] resource_sub_format) {
		this.resource_sub_format = resource_sub_format;
	}

	public String[] getResource_time() {
		return resource_time;
	}

	public void setResource_time(String[] resource_time) {
		this.resource_time = resource_time;
	}

	public String[] getResource_address() {
		return resource_address;
	}

	public void setResource_address(String[] resource_address) {
		this.resource_address = resource_address;
	}

	public String[] getResource_remark() {
		return resource_remark;
	}

	public void setResource_remark(String[] resource_remark) {
		this.resource_remark = resource_remark;
	}

	public MultipartFile getPoster_hq() {
		return poster_hq;
	}

	public void setPoster_hq(MultipartFile poster_hq) {
		this.poster_hq = poster_hq;
	}

	public MultipartFile getPoster_nq() {
		return poster_nq;
	}

	public void setPoster_nq(MultipartFile poster_nq) {
		this.poster_nq = poster_nq;
	}

	public String getPoster_upload_time() {
		return poster_upload_time;
	}

	public void setPoster_upload_time(String poster_upload_time) {
		this.poster_upload_time = poster_upload_time;
	}

	public String getPosterUpdateFlag() {
		return posterUpdateFlag;
	}

	public void setPosterUpdateFlag(String posterUpdateFlag) {
		this.posterUpdateFlag = posterUpdateFlag;
	}

	@Override
	public String toString() {
		return "AnimationBean [animation_id=" + animation_id + ", animation_name=" + animation_name
				+ ", animation_alias=" + animation_alias + ", animation_episode=" + animation_episode
				+ ", animation_broadcast_time=" + animation_broadcast_time + ", animation_broadcast_season="
				+ animation_broadcast_season + ", animation_type=" + animation_type + ", animation_source="
				+ animation_source + ", animation_cv=" + animation_cv + ", animation_staff=" + animation_staff
				+ ", animation_intro=" + animation_intro + ", animation_detail_information="
				+ animation_detail_information + ", animation_remark=" + animation_remark + ", have_poster="
				+ have_poster + ", sequence=" + sequence + ", resource_type=" + Arrays.toString(resource_type)
				+ ", resource_format=" + Arrays.toString(resource_format) + ", resource_image_resolution="
				+ Arrays.toString(resource_image_resolution) + ", resource_sub=" + Arrays.toString(resource_sub)
				+ ", resource_sub_type=" + Arrays.toString(resource_sub_type) + ", resource_sub_format="
				+ Arrays.toString(resource_sub_format) + ", resource_time=" + Arrays.toString(resource_time)
				+ ", resource_address=" + Arrays.toString(resource_address) + ", resource_remark="
				+ Arrays.toString(resource_remark) + ", poster_hq=" + poster_hq + ", poster_nq=" + poster_nq
				+ ", poster_upload_time=" + poster_upload_time + ", posterUpdateFlag=" + posterUpdateFlag + "]";
	}

}
