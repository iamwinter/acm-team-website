package dao;

import Tools.DateTool;
import models.SqlFile;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FileDao extends BaseDao<SqlFile> {

	public SqlFile add(File upFile, String basePath, String filename, String username){
		String path = basePath + "/";
		path += DateTool.dateToStr(new Date(),"yyyyMMddhhmmss_");//以上传时间命名
		path += new Random().nextInt(9000)+1000;
		path += filename.substring(filename.lastIndexOf(".")); //后缀

		String realPath= ServletActionContext.getServletContext().getRealPath(path); //获取磁盘绝对路径
		System.out.println("相对路径    path: "+path);
		System.out.println("绝对路径realPath: "+realPath);
		try {
			File storeFile = new java.io.File(realPath);	//创建文件
			if(!storeFile.getParentFile().exists())storeFile.getParentFile().mkdirs();//父目录不存在则创建
			FileUtils.copyFile(upFile, storeFile);//copy upFile ==> 磁盘文件
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件保存异常");
			return null;
		}

		//下面在数据库中增加记录
		SqlFile sqlFile=new SqlFile();
		sqlFile.setName( filename );
		sqlFile.setPath(path);
		sqlFile.setUsername(username);
		sqlFile.setCreated(new java.sql.Timestamp(new java.util.Date().getTime()));

		int id= (int) session.save(sqlFile);
		transaction.commit();

		System.out.println("文件："+sqlFile);
		return sqlFile;
	}

	public void delete(int id){
		SqlFile sqlFile = findById(id);
		if(sqlFile==null)return; //没有这条记录
		String realPath= ServletActionContext.getServletContext().getRealPath(sqlFile.getPath());
		File storeFile = new java.io.File( realPath );	//创建文件
		if(storeFile.isFile()){
			storeFile.delete();
			System.out.println("从外存中删除文件:"+sqlFile.getPath());
		}
		super.delete(sqlFile);
	}

	public SqlFile findById(int id) {
		List list=session.createQuery("from SqlFile where id=?1")
				.setParameter(1,id).list();
		return list.isEmpty() ? null : (SqlFile) list.get(0);
	}

}
