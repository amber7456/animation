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

	public MapBean getAnimationDetail(String animationId) throws SQLException { 
		return animationDao.getAnimationDetail(animationId);
	}

	public int animationAdd(AnimationBean animation) throws IOException {
		return animationDao.animationAdd(animation);
	}

	public List<MapBean> getResourceById(String animationId) throws SQLException {
		return animationDao.getResourceById(animationId);
	}

	public int animationEdit(AnimationBean animation) throws IOException {
		return animationDao.animationEdit(animation);
	}

	public int animationDelete(AnimationBean animation) {
		return animationDao.animationDelete(animation);
	}

	
	
}
