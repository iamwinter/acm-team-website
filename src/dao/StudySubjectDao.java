package dao;

import models.StudySubject;
import java.util.List;

public class StudySubjectDao extends BaseDao<StudySubject> {
	public StudySubject findById(int id){
		List list=session.createQuery("from News where id=?1").setParameter(1,id).list();
		return list.isEmpty() ? null : (StudySubject) list.get(0);
	}
}