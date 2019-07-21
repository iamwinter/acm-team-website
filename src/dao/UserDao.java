package dao;

import models.User;

import java.util.Calendar;

import java.util.List;

/**
 * Hibernate各种查询
 * https://www.cnblogs.com/jack1995/p/6952704.html
 */


public class UserDao extends BaseDao<User> {
	public static String[] tagStr={"游客","退役成员","现役成员","指导教师"};

	public User findByUsername(String username){
		List list=session.createQuery("from User where username = ?1")
				.setParameter(1,username).list();
		close();
		return list.isEmpty() ? null : (User) list.get(0);
	}

	public User findByEmail(String email){
		List list=session.createQuery("from User where email = ?1")
				.setParameter(1,email).list();
		close();
		return list.isEmpty() ? null : (User) list.get(0);
	}

	public void register(User user){
		user.setGrade(Calendar.getInstance().get(Calendar.YEAR));
		user.setTag(0);
		user.setPower(user.getUsername().equals("admin") ?3:0);
		super.add(user);
	}

	public User loginUser(String usernameOrEmail,String password){
		User user = new UserDao().findByUsername(usernameOrEmail);
		if(user==null){ //用户名不存在，则尝试邮箱
			user=new UserDao().findByEmail(usernameOrEmail);
		}
		if(user==null)return null;
		close();
		return user.getPassword().equals(password) ? user : null;
	}

	public List<User> find_members(String key) {
		List list=session.createQuery("from User where username like ?1" +
				" or nickName like ?1" +
				" or email like ?1" +
				" or str(grade) like ?1" +
				" order by grade desc")
				.setParameter(1,"%"+key+"%").list();
		close();
		return list;
	}
}
