package dao;

import models.StudyFolder;

import java.util.ArrayList;
import java.util.List;

public class StudyFolderDao extends BaseDao<StudyFolder> {

	public List findFolders(int year, int subjectId) {
		// 按年份和科目查找文件夹
		List list=session.createQuery("from StudyFolder where forYear=?1 and subjectId=?2")
				.setParameter(1,year)
				.setParameter(2,subjectId).list();
		close();
		return list;
	}

	public Integer getMaxYear() {
		// 获取最大年份
		List<Integer> list= session.createQuery("select max(forYear) from StudyFolder").list();
		close();
		return list.size()==0 || list.get(0)==null ? 0 : list.get(0);
	}

	public List getAllYears() {
		List list=session.createQuery("select forYear from StudyFolder group by forYear order by forYear desc")
				.list();
		List retList=new ArrayList();
		for(int i=0;i<list.size();i++){
			if (list.get(i)!=null){
				retList.add(list.get(i));
			}
		}
		close();
		return retList;
	}
}
