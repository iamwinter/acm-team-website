package dao;

import models.StudyNode;

import java.util.ArrayList;
import java.util.List;

public class StudyNodeDao extends BaseDao<StudyNode> {

	public StudyNode findById(int id){
		List list=session.createQuery("from StudyNode where id=?1").setParameter(1,id).list();
		return list.isEmpty() ? null : (StudyNode) list.get(0);
	}

	public List findFolders(int year, int subjectId) {
		// 按年份和科目查找文件夹
		List list=session.createQuery("from StudyNode where forYear=?1 and subjectId=?2 and fileId=null")
				.setParameter(1,year)
				.setParameter(2,subjectId).list();
		return list;
	}

	public Integer getMaxYear() {
		// 获取最大年份
		List<Integer> list= session.createQuery("select max(forYear) from StudyNode").list();
		return list.size()==0 || list.get(0)==null ? 0 : list.get(0);
	}

	public List findFiles(int fatherId) {
		// 获取某文件夹下的所有文件
		List list=session.createQuery("select b from StudyNode a,SqlFile b where a.fatherId=?1 and a.fileId=b.id order by b.name asc")
				.setParameter(1,fatherId).list();
		return list;
	}

	public List getAllYears() {
		List list=session.createQuery("select forYear from StudyNode group by forYear order by forYear desc")
				.list();
		List retList=new ArrayList();
		for(int i=0;i<list.size();i++){
			if (list.get(i)!=null){
				retList.add(list.get(i));
			}
		}
		return retList;
	}
}
