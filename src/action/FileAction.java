package action;

import Tools.DateTool;
import Tools.IntegerTool;
import Tools.MyConfig;
import com.opensymphony.xwork2.ActionSupport;
import dao.FileDao;
import dao.StudyNodeDao;
import models.SqlFile;
import models.StudyNode;
import models.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 模型驱动参考：https://blog.csdn.net/SHU15121856/article/details/80089820
 */

public class FileAction extends ActionSupport implements ServletRequestAware, SessionAware {
	private HttpServletRequest request;
	private Map<String,Object> session;
	private String result;  //用于返回json数据

	//文件
	private File upFile; //得到上传的文件
	private String upFileContentType; //得到文件的类型
	private String upFileFileName; //得到文件的名称

	private static final long serialVersionUID = 837481714629791752L;
	private String fileName;
	private String fileSize;
	private String uploadSize;
	private String responseInfo;

	//wangeditor图片上传，返回json地址
	public String wangUploadImage(){
		User user_on = (User) session.get("user");
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();

		SqlFile sqlFile = new FileDao().add(upFile, MyConfig.get("upload")+"/wangImage",
				upFileFileName,user_on.getUsername());
		if(sqlFile!=null){
			data.add(sqlFile.getPath());
			json.put("data",data);
			json.put("errno",0);
		}else{
			json.put("errno",1);//上传失败
		}
		result = json.toString();
		return "json";
	}


	//下面是学习专区上传文件  断点续传
	// 获取当前临时文件的绝对路径的父文件夹
	private String getTempFilePath(int fatherId){
		StudyNode folder = new StudyNodeDao().findById(fatherId);
		String fpath = MyConfig.get("upload")+"/study/year"+folder.getForYear()+
				"/subject"+folder.getSubjectId()+"/folder"+fatherId;   //相对父目录
		String fatherPath= ServletActionContext.getServletContext().getRealPath(fpath); //获取磁盘绝对父路径
		return fatherPath;// 相对路径，绝对路径
	}
	// 看一下有没有临时文件，有的话就是断点续传
	public String getUploadedSize() throws IOException {
		uploadSize = "0";

		Integer fatherId = IntegerTool.strToInt(request.getParameter("fatherId"),0);
		String fatherPath = getTempFilePath(fatherId);
		File fileUploadDetal = new File(fatherPath+"/"+fileName+".rhxy");  //临时文件，记录文件上传的状态
		if(fileUploadDetal.exists()){
			//有临时文件，说明是断点续传
			// 读取笔记文件，里面记录了上传信息
			FileReader fileReader = new FileReader(fileUploadDetal);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String realPath = bufferedReader.readLine(); //文件绝对路径
			String uploadedSize = bufferedReader.readLine();//已上传大小
			bufferedReader.close();
			fileReader.close();
			uploadSize=uploadedSize;
		}
		return "uploadedSize";
	}

	public String doUpload() throws Exception {
		System.out.println("=======================================");
		System.out.println(fileName);
		System.out.println("总大小:"+fileSize+"，已传大小："+uploadSize);

		//构造上传路径
		Integer fatherId = IntegerTool.strToInt(request.getParameter("fatherId"),0); //父文件夹id
		String fatherPath = getTempFilePath(fatherId);

		File fileUploadDetal = new File(fatherPath+"/"+fileName+".rhxy");//读取临时文件，记录文件上传的状态

		String realPath=null;	//文件真实路径
		if (!fileUploadDetal.exists()) {
			if(!fileUploadDetal.getParentFile().exists())fileUploadDetal.getParentFile().mkdirs(); //创建父目录
			fileUploadDetal.createNewFile(); //临时文件不存在，说明是开始上传该文件，要同时写入数据库
			//构造保存名
			String fileSaveName = DateTool.dateToStr(new Date(),"yyyyMMddhhmmss_");//以上传时间命名
			fileSaveName += new Random().nextInt(900000)+100000;
			fileSaveName += fileName.substring(fileName.lastIndexOf(".")); //后缀

			realPath = fatherPath+"/"+fileSaveName;
		}else{
			FileReader fileReader = new FileReader(fileUploadDetal);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			realPath = bufferedReader.readLine(); //文件绝对路径
			fileReader.close();
		}
		FileWriter fileWriter = new FileWriter(fileUploadDetal);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(realPath);
		bufferedWriter.newLine();
		bufferedWriter.write(uploadSize);
		bufferedWriter.close();
		fileWriter.close();

		//创建新文件并写入
		File newFile = new File(realPath);
		if(!newFile.exists())
			newFile.createNewFile();
		copyFile(upFile,newFile);

		if(uploadSize.equals(fileSize) && fileUploadDetal.exists()){// 上传完成，删除临时文件
			fileUploadDetal.delete();

			//写入数据库
			SqlFile sqlFile=new SqlFile();
			sqlFile.setName(fileName);
			sqlFile.setPath(realPath.substring(realPath.indexOf(MyConfig.get("upload"))).replace("\\","/")); //相对路径
			sqlFile.setUsername(((User)session.get("user")).getUsername());
			sqlFile.setCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
			new FileDao().add(sqlFile); //保存到数据库

			//增加学习资料记录
			StudyNode sn = new StudyNode();
			sn.setTitle(fileName);
			sn.setFatherId(fatherId);
			sn.setFileId(sqlFile.getId());
			new StudyNodeDao().add(sn);	//添加学习资料
		}
		responseInfo = "上传成功!";
		return "doupload";
	}

	private void copyFile(File oldFile,File newFile) throws IOException{
		InputStream in = new FileInputStream(oldFile);
		OutputStream out = new FileOutputStream(newFile,true);
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getUploadSize() {
		return uploadSize;
	}

	public String getResponseInfo() {
		return responseInfo;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setUploadSize(String uploadSize) {
		this.uploadSize = uploadSize;
	}

	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session=map;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request=httpServletRequest;
	}

	public String getResult() {
		return result;
	}

	public void setUpFile(File upFile) {
		this.upFile = upFile;
	}

	public void setUpFileContentType(String upFileContentType) {
		this.upFileContentType = upFileContentType;
	}

	public void setUpFileFileName(String upFileFileName) {
		this.upFileFileName = upFileFileName;
	}
}
