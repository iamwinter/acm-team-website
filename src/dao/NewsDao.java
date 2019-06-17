package dao;

import models.News;

import javax.persistence.Query;
import java.util.List;

public class NewsDao extends BaseDao<News> {

	public List findPublicPage(int pageId,int pageSize){
		// 肯定按时间排序
		Query query = session.createQuery("from News where isPublic=1 order by created desc");
		query.setFirstResult((pageId-1)*pageSize);
		query.setMaxResults(pageSize);
		List list= query.getResultList();
		close();
		return list;
	}

}
