package com.animation;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.basedao.dbtool.Column;
import com.basedao.dbtool.ColumnType;
import com.basedao.dbtool.DBTools;
import com.basedao.dbtool.MapBean;
import com.basedao.dbtool.Param;
import com.bean.animation.AnimationBean;
import com.utils.CommonUtils;

@Transactional
@Repository
public class AnimationDao {

	@Autowired
	public DBTools DBTools;

	/**
	 * 
	 * @param animationId
	 * @return
	 * @throws SQLException
	 */
	public MapBean getAnimationDetail(String animationId) throws SQLException {
		String sql = "SELECT * FROM ANIMATION_INFORMATION T1 WHERE T1.ANIMATION_ID = ? ";
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(animationId, ColumnType.VARCHAR));
		dataList = DBTools.query(sql, pList);
		return dataList.get(0);
	}

	public int animationAdd(AnimationBean animation) throws IOException {
		int r = 0;
		List<Column> cList = new ArrayList<Column>();
		String id = CommonUtils.getNowStr("YYYYMMddHHmmssSSS");
		cList.add(new Column("animation_id", id, ColumnType.VARCHAR));//
		cList.add(new Column("animation_name", animation.getAnimation_name(), ColumnType.VARCHAR));//
		cList.add(new Column("animation_episode", animation.getAnimation_episode(), ColumnType.VARCHAR));//
		cList.add(new Column("animation_broadcast_time", animation.getAnimation_broadcast_time(), ColumnType.VARCHAR));//
		cList.add(new Column("animation_type", animation.getAnimation_type(), ColumnType.VARCHAR)); //
		cList.add(new Column("animation_source", animation.getAnimation_source(), ColumnType.VARCHAR)); //
		cList.add(new Column("animation_cv", animation.getAnimation_cv(), ColumnType.VARCHAR)); //
		cList.add(new Column("animation_staff", animation.getAnimation_staff(), ColumnType.VARCHAR)); //
		cList.add(new Column("animation_detail_information", animation.getAnimation_detail_information(),
				ColumnType.VARCHAR));//
		cList.add(new Column("animation_remark", animation.getAnimation_remark(), ColumnType.VARCHAR));//
		cList.add(new Column("LAST_UPDATE_TIME", CommonUtils.getNowStr("YYYY-MM-dd HH:mm:ss"), ColumnType.VARCHAR));//

		if (animation.getPoster_hq() != null && animation.getPoster_hq().getSize() > 0) {
			cList.add(new Column("have_poster", "YES", ColumnType.VARCHAR));//
		} else {
			cList.add(new Column("have_poster", "NO", ColumnType.VARCHAR));//
		}

		boolean haveBDResourceFlag = false;
		if (animation.getResource_type() != null && animation.getResource_type().length > 0) {
			for (int i = 0; i < animation.getResource_type().length; i++) {
				if (animation.getResource_type()[i].equals("BD")) {
					haveBDResourceFlag = true;
					break;
				}
			}
		}
		
		if(haveBDResourceFlag){
			cList.add(new Column("HAVE_BD_RESOURCE", "1", ColumnType.VARCHAR));//
		}else{
			cList.add(new Column("HAVE_BD_RESOURCE", "0", ColumnType.VARCHAR));//
		}

		// 保存基本信息
		r = DBTools.table("ANIMATION_INFORMATION").add(cList);

		// 海报
		if (animation.getPoster_hq() != null && animation.getPoster_hq().getSize() > 0) {
			List<Column> c_cList = new ArrayList<Column>();
			InputStream inputS = animation.getPoster_hq().getInputStream();// 获得输入流
			byte[] photo = IOUtils.toByteArray(inputS);// 转换为byte
			c_cList.add(new Column("ANIMATION_ID", id, ColumnType.VARCHAR));//
			c_cList.add(new Column("POSTER_HQ", photo, ColumnType.BLOB));// --照片
			c_cList.add(new Column("POSTER_UPLOAD_TIME", CommonUtils.getNowStr(), ColumnType.VARCHAR));
			DBTools.table("animation_poster").add(c_cList);
		}

		// 加入资源信息
		if (animation.getResource_type() != null && animation.getResource_type().length > 0) {
			List<Column> c_cList = null;

			for (int i = 0; i < animation.getResource_type().length; i++) {
				c_cList = new ArrayList<Column>();
				c_cList.add(new Column("ANIMATION_ID", id, ColumnType.VARCHAR));//
				c_cList.add(new Column("resource_type", animation.getResource_type()[i], ColumnType.VARCHAR));//
				c_cList.add(new Column("resource_format", animation.getResource_format()[i], ColumnType.VARCHAR));
				c_cList.add(new Column("resource_image_resolution", animation.getResource_image_resolution()[i],
						ColumnType.VARCHAR));
				c_cList.add(new Column("resource_sub", "YES", ColumnType.VARCHAR));// 有无字幕
				c_cList.add(new Column("resource_sub_type", animation.getResource_sub_type()[i], ColumnType.VARCHAR));
				c_cList.add(
						new Column("resource_sub_format", animation.getResource_sub_format()[i], ColumnType.VARCHAR));
				c_cList.add(new Column("resource_time", animation.getResource_time()[i], ColumnType.VARCHAR));
				if (animation.getResource_address().length > 0) {
					c_cList.add(new Column("resource_address", animation.getResource_address()[i], ColumnType.VARCHAR));
				}
				if (animation.getResource_remark().length > 0) {
					c_cList.add(new Column("resource_remark", animation.getResource_remark()[i], ColumnType.VARCHAR));
				}
				DBTools.table("animation_resource").add(c_cList);
			}

		}

		return r;
	}

	/**
	 * 通过id查找资源
	 * 
	 * @param animationId
	 * @return
	 * @throws SQLException
	 */
	public List<MapBean> getResourceById(String animationId) throws SQLException {
		List<MapBean> dataList = new ArrayList<MapBean>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(animationId, ColumnType.VARCHAR));
		dataList = DBTools.table("animation_resource").where(" animation_id = ? ", pList).select();
		return dataList;
	}

	public int animationEdit(AnimationBean animation) throws IOException {
		int r = 0;

		List<Column> cList = new ArrayList<Column>();
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(animation.getAnimation_id(), ColumnType.VARCHAR));

		cList.add(new Column("animation_name", animation.getAnimation_name(), ColumnType.VARCHAR));//
		cList.add(new Column("animation_episode", animation.getAnimation_episode(), ColumnType.VARCHAR));//
		cList.add(new Column("animation_broadcast_time", animation.getAnimation_broadcast_time(), ColumnType.VARCHAR));//
		cList.add(new Column("animation_type", animation.getAnimation_type(), ColumnType.VARCHAR)); //
		cList.add(new Column("animation_source", animation.getAnimation_source(), ColumnType.VARCHAR)); //
		cList.add(new Column("animation_cv", animation.getAnimation_cv(), ColumnType.VARCHAR)); //
		cList.add(new Column("animation_staff", animation.getAnimation_staff(), ColumnType.VARCHAR)); //
		cList.add(new Column("animation_detail_information", animation.getAnimation_detail_information(),
				ColumnType.VARCHAR));//
		cList.add(new Column("animation_remark", animation.getAnimation_remark(), ColumnType.VARCHAR));//
		cList.add(new Column("LAST_UPDATE_TIME", CommonUtils.getNowStr("YYYY-MM-dd HH:mm:ss"), ColumnType.VARCHAR));//
		String have_poster = "";
		if (animation.getPosterUpdateFlag().equals("1")) {
			if (animation.getPoster_hq() != null && animation.getPoster_hq().getSize() > 0) {
				cList.add(new Column("have_poster", "YES", ColumnType.VARCHAR));//
				have_poster = "YES";
			} else {
				cList.add(new Column("have_poster", "NO", ColumnType.VARCHAR));//
				have_poster = "NO";
			}
		} else {
			cList.add(new Column("have_poster", animation.getHave_poster(), ColumnType.VARCHAR));//
			have_poster = "NO_UPDATE";
		}
		
		//判断是否有BD资源
		boolean haveBDResourceFlag = false;
		if (animation.getResource_type() != null && animation.getResource_type().length > 0) {
			for (int i = 0; i < animation.getResource_type().length; i++) {
				if (animation.getResource_type()[i].equals("BD")) {
					haveBDResourceFlag = true;
					break;
				}
			}
		}
		
		if(haveBDResourceFlag){
			cList.add(new Column("HAVE_BD_RESOURCE", "1", ColumnType.VARCHAR));//
		}else{
			cList.add(new Column("HAVE_BD_RESOURCE", "0", ColumnType.VARCHAR));//
		}

		// 修改基本信息
		r = DBTools.table("ANIMATION_INFORMATION").where(" animation_id = ? ", pList).update(cList);

		// 海报
		if (have_poster.equals("YES")) {
			// 有图片 先删除 再添加
			DBTools.table("animation_poster").where(" animation_id = ? ", pList).delete();
			List<Column> c_cList = new ArrayList<Column>();
			InputStream inputS = animation.getPoster_hq().getInputStream();// 获得输入流
			byte[] photo = IOUtils.toByteArray(inputS);// 转换为byte
			c_cList.add(new Column("ANIMATION_ID", animation.getAnimation_id(), ColumnType.VARCHAR));//
			c_cList.add(new Column("POSTER_HQ", photo, ColumnType.BLOB));// --照片
			c_cList.add(new Column("POSTER_UPLOAD_TIME", CommonUtils.getNowStr(), ColumnType.VARCHAR));
			DBTools.table("animation_poster").add(c_cList);
		} else if (have_poster.equals("NO")) {
			// 无图片 只删除
			DBTools.table("animation_poster").where(" animation_id = ? ", pList).delete();
		}

		// 收录信息
		// 先删除
		DBTools.table("animation_resource").where(" animation_id = ? ", pList).delete();
		// 再添加
		if (animation.getResource_type() != null && animation.getResource_type().length > 0) {
			List<Column> c_cList = null;
			for (int i = 0; i < animation.getResource_type().length; i++) {
				c_cList = new ArrayList<Column>();
				c_cList.add(new Column("ANIMATION_ID", animation.getAnimation_id(), ColumnType.VARCHAR));//
				c_cList.add(new Column("resource_type", animation.getResource_type()[i], ColumnType.BLOB));//
				c_cList.add(new Column("resource_format", animation.getResource_format()[i], ColumnType.VARCHAR));
				c_cList.add(new Column("resource_image_resolution", animation.getResource_image_resolution()[i],
						ColumnType.VARCHAR));
				c_cList.add(new Column("resource_sub", "YES", ColumnType.VARCHAR));// 有无字幕
				c_cList.add(new Column("resource_sub_type", animation.getResource_sub_type()[i], ColumnType.VARCHAR));
				c_cList.add(
						new Column("resource_sub_format", animation.getResource_sub_format()[i], ColumnType.VARCHAR));
				c_cList.add(new Column("resource_time", animation.getResource_time()[i], ColumnType.VARCHAR));
				if (animation.getResource_address().length > 0) {
					c_cList.add(new Column("resource_address", animation.getResource_address()[i], ColumnType.VARCHAR));
				}
				if (animation.getResource_remark().length > 0) {
					c_cList.add(new Column("resource_remark", animation.getResource_remark()[i], ColumnType.VARCHAR));
				}
				DBTools.table("animation_resource").add(c_cList);
			}
		}

		return r;
	}

	public int animationDelete(AnimationBean animation) {
		int r = 0;
		List<Param> pList = new ArrayList<Param>();
		pList.add(new Param(animation.getAnimation_id(), ColumnType.VARCHAR));
		r = DBTools.table("animation_poster").where(" animation_id = ? ", pList).delete();
		r = DBTools.table("animation_resource").where(" animation_id = ? ", pList).delete();
		r = DBTools.table("ANIMATION_INFORMATION").where(" animation_id = ? ", pList).delete();
		return r;
	}

}
