package com.animation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basedao.dbtool.MapBean;
import com.bean.animation.AnimationBean;

@Component
public class AnimationService {

	@Autowired
	public AnimationDao animationDao;

	/**
	 * 获取动画详情
	 * 
	 * @param animationId
	 * @return
	 * @throws SQLException
	 */
	public MapBean getAnimationDetail(String animationId) throws SQLException {
		return animationDao.getAnimationDetail(animationId);
	}

	/**
	 * 
	 * @param animation
	 * @return
	 * @throws IOException
	 */
	public int animationAdd(AnimationBean animation) throws IOException {
		return animationDao.animationAdd(animation);
	}

	/**
	 * 
	 * @param animationId
	 * @return
	 * @throws SQLException
	 */
	public List<MapBean> getResourceById(String animationId) throws SQLException {
		return animationDao.getResourceById(animationId);
	}

	/**
	 * 
	 * @param animation
	 * @return
	 * @throws IOException
	 */
	public int animationEdit(AnimationBean animation) throws IOException {
		return animationDao.animationEdit(animation);
	}

	public int animationDelete(AnimationBean animation) {
		return animationDao.animationDelete(animation);
	}

}
