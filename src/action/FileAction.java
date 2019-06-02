package action;

import Tools.MyConfig;
import com.opensymphony.xwork2.ActionSupport;
import dao.FileDao;
import models.SqlFile;
import models.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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

	public void setResult(String result) {
		this.result = result;
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
