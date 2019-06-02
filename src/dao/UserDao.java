package dao;

import models.User;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Hibernate各种查询
 * https://www.cnblogs.com/jack1995/p/6952704.html
 */


public class UserDao extends BaseDao<User> {
	public static String[] tagStr={"游客","退役成员","现役成员","指导教师"};

	public User findById(int id){
		List list=session.createQuery("from User where id = ?1")
				.setParameter(1,id).list();
		return list.isEmpty() ? null : (User) list.get(0);
	}
	public User findByUsername(String username){
		List list=session.createQuery("from User where username = ?1")
				.setParameter(1,username).list();
		return list.isEmpty() ? null : (User) list.get(0);
	}

	public User findByEmail(String email){
		List list=session.createQuery("from User where email = ?1")
				.setParameter(1,email).list();
		return list.isEmpty() ? null : (User) list.get(0);
	}

	public User loginUser(String usernameOrEmail,String password){
		User user = findByUsername(usernameOrEmail);
		if(user==null){ //用户名不存在，则尝试邮箱
			user=findByEmail(usernameOrEmail);
		}
		if(user==null)return null;
		return user.getPassword().equals(password) ? user : null;
	}

	public List<User> findAll(){
		List list=session.createQuery("from User").list();
		return list;
	}

	public List<User> find_members(String key,boolean onlyPublic) {
		System.out.println(key);
		try {
			key = new String(key.getBytes("ISO8859_1"), StandardCharsets.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List list=session.createQuery("from User where (username like ?1 or nick_name like ?1)"
				+ (onlyPublic ? " and is_public=1":""))
				.setParameter(1,"%"+key+"%").list();
		return list;
	}
}
