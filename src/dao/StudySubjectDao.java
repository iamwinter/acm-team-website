package dao;

import models.StudySubject;
import java.util.List;

public class StudySubjectDao extends BaseDao<StudySubject> {

	@Override
	public void add(StudySubject ss){
		// 新增一个科目，优先级默认等于id,此处有错
		session.save(ss);
		transaction.commit();
		ss.setPriority(ss.getId());
		super.update(ss);
	}

	public void swap(int x,int y){
		// 优先级交换
		StudySubject a=findById(x), b=findById(y);
		int temp = a.getPriority();
		a.setPriority(b.getPriority());
		b.setPriority(temp);
		new StudySubjectDao().update(a);
		super.update(b);
	}

	public List<StudySubject> getList() {
		List list=session.createQuery("from StudySubject order by priority asc").list();
		close();
		return list;
	}
}