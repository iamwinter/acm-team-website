package dao;

import models.StudyNode;

import java.util.List;

public class StudyNodeDao extends BaseDao<StudyNode> {
	public StudyNode findById(int id){
		List list=session.createQuery("from News where id=?1").setParameter(1,id).list();
		return list.isEmpty() ? null : (StudyNode) list.get(0);
	}
}
