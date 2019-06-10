package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 完成了增删改，查在各自的dao实现
 * @param <Bean>
 *     注意！：成员方法不可调用其他成员方法，因为会话已关闭
 */

public class BaseDao<Bean>  {
	private SessionFactory sessionFactory;
	protected Session session;
	protected Transaction transaction;

	private Class<Bean> beanClass; // 获取实体类

	BaseDao() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory(); //创建工厂
		session = sessionFactory.openSession();  //打开session
		transaction = session.beginTransaction();  //开启事务

		ParameterizedType pt=(ParameterizedType)this.getClass().getGenericSuperclass(); //泛型落实后才生效
		this.beanClass=(Class<Bean>) pt.getActualTypeArguments()[0];
	}

	public void close(){
		if(session!=null)session.close();
		if(sessionFactory!=null) sessionFactory.close();
	}

	public void add(Bean bean){
		session.save(bean);
		transaction.commit();
		System.out.println("新增记录:"+bean);
		close();
	}

	public void update(Bean bean){

		session.update(bean);
		transaction.commit();
		System.out.println("更新："+bean);
		close();
	}

	public void delete(Bean bean){
		session.delete(bean);
		transaction.commit();
		System.out.println("删除："+bean);
		close();
	}

	public Bean findById(Integer id){
		List list=session.createQuery("from "+beanClass.getName()+" a where a.id = ?1")
				.setParameter(1,id).list();
		close();
		return list.isEmpty() ? null : (Bean) list.get(0);
	}

	public List<Bean> findAll(){
		List list=session.createQuery("from "+beanClass.getName()).list();
		close();
		return list;
	}

}
