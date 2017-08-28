package com.poster;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PosterService {
	
	@Autowired
	private PosterDao posterDao;

	public byte[] getPosterById(String id) throws SQLException { 
		return posterDao.getPosterById(id);
	}

}
