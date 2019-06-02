package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 完成了增删改，查在各自的dao实现
 * @param <Bean>
 */
public class BaseDao<Bean> {
	protected Session session;
	protected Transaction transaction;

	BaseDao() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory(); //创建工厂
		session = sessionFactory.openSession();  //打开session
		transaction = session.beginTransaction();  //开启事务
	}
	public void add(Bean bean){
		session.save(bean);
		transaction.commit();
		System.out.println("新增记录:"+bean);
	}
	public void update(Bean bean){
		session.update(bean);
		transaction.commit();
		System.out.println("更新："+bean);
	}
	public void delete(Bean bean){
		session.delete(bean);
		transaction.commit();
		System.out.println("删除："+bean);
	}
}
