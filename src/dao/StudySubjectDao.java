package dao;

import Tools.IntegerTool;
import models.StudySubject;
import org.hibernate.query.Query;

import java.util.List;

public class StudySubjectDao extends BaseDao<StudySubject> {

	@Override
	public void add(StudySubject ss){
		// 新增一个科目，优先级默认等于id
		session.save(ss);
		transaction.commit();
		ss.setPriority(ss.getId());
		close();
		new StudySubjectDao().update(ss);
	}

	//优先级交换，即移动顺序
	public void move_pos(Integer id, boolean up) {
		StudySubject ss=new StudySubjectDao().findById(id);
		List list=session.createQuery(
				"from StudySubject where priority"+(up?"<":">")+"?1 order by priority "+(up?"desc":"asc"))
				.setParameter(1,ss.getPriority()).list();
		if(!list.isEmpty()){
			StudySubject aim= (StudySubject) list.get(0);
			Integer ssPri=ss.getPriority();
			ss.setPriority(aim.getPriority());
			aim.setPriority(ssPri);
			new StudySubjectDao().update(ss);
			new StudySubjectDao().update(aim);
		}
	}

	//按优先级排序，获取科目
	public List<StudySubject> getList() {
		List list=session.createQuery("from StudySubject order by priority asc").list();
		close();
		return list;
	}

}