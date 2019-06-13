package dao;

import Tools.DateTool;
import models.StudyFile;
import models.User;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StudyFileDao extends BaseDao<StudyFile> {

	public StudyFile add(File upFile, String basePath, String filename, User uploadUser){
		String path = basePath + "/";
		path += DateTool.dateToStr(new Date(),"yyyyMMddhhmmss_");//以上传时间命名
		path += new Random().nextInt(90000)+10000;
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
		StudyFile studyFile =new StudyFile();
		studyFile.setName( filename );
		studyFile.setPath(path);
		studyFile.setCreated(new java.sql.Timestamp(new java.util.Date().getTime()));

		session.save(studyFile);
		transaction.commit();
		close();

		System.out.println("新增文件："+ studyFile);
		return studyFile;
	}

	public void delete(StudyFile studyFile){
		if(studyFile ==null)return; //没有这条记录
		String realPath= ServletActionContext.getServletContext().getRealPath(studyFile.getPath());
		File storeFile = new java.io.File( realPath );	//创建文件
		if(storeFile.isFile()){
			storeFile.delete();
			System.out.println("从磁盘中删除文件:"+ studyFile.getPath());
		}
		super.delete(studyFile);
	}

	public List findByFolderId(int folderId) {
		// 获取某文件夹下的所有文件
		List list=session.createQuery("from StudyFile where folder.id=?1 order by name asc")
				.setParameter(1,folderId).list();
		close();
		return list;
	}

}
