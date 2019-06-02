package dao;

import models.Contest;

import java.util.List;

public class ContestDao extends BaseDao<Contest> {

	public Contest findById(int id){
		List list=session.createQuery("from Contest where id=?1")
				.setParameter(1,id).list();
		return list.isEmpty() ? null : (Contest) list.get(0);
	}
}
