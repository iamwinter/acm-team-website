package dao;

import models.News;

import java.util.List;

public class NewsDao extends BaseDao<News> {

	public News findById(int id){
		List list=session.createQuery("from News where id=?1")
				.setParameter(1,id).list();
		return list.isEmpty() ? null : (News) list.get(0);
	}

}
