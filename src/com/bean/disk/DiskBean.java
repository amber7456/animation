package com.bean.disk;

public class DiskBean {
	private String disk_id;//
	private String disk_name;//
	private String disk_capacity;//
	private String disk_state;//
	private String is_full;//

	public String getDisk_id() {
		return disk_id;
	}

	public void setDisk_id(String disk_id) {
		this.disk_id = disk_id;
	}

	public String getDisk_name() {
		return disk_name;
	}

	public void setDisk_name(String disk_name) {
		this.disk_name = disk_name;
	}

	public String getDisk_capacity() {
		return disk_capacity;
	}

	public void setDisk_capacity(String disk_capacity) {
		this.disk_capacity = disk_capacity;
	}

	public String getDisk_state() {
		return disk_state;
	}

	public void setDisk_state(String disk_state) {
		this.disk_state = disk_state;
	}

	public String getIs_full() {
		return is_full;
	}

	public void setIs_full(String is_full) {
		this.is_full = is_full;
	}

	@Override
	public String toString() {
		return "DiskBean [disk_id=" + disk_id + ", disk_name=" + disk_name + ", disk_capacity=" + disk_capacity
				+ ", disk_state=" + disk_state + ", is_full=" + is_full + "]";
	}

}
