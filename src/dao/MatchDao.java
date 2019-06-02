package dao;

import models.Match;

import java.util.List;

public class MatchDao extends BaseDao<Match> {

	public Match findById(int id){
		List list=session.createQuery("from Match where id=?1")
				.setParameter(1,id).list();
		return list.isEmpty() ? null : (Match) list.get(0);
	}
}
