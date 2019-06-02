package action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.NewsDao;
import dao.UserDao;
import models.News;
import models.SqlFile;
import models.User;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 模型驱动参考：https://blog.csdn.net/SHU15121856/article/details/80089820
 */

public class AdminAction extends ActionSupport implements ServletRequestAware, SessionAware {

	private HttpServletRequest request;
	private Map<String,Object> session;
	private String result;  //用于返回json数据

	//文件
	private File upFile; //得到上传的文件
	private String upFileContentType; //得到文件的类型
	private String upFileFileName; //得到文件的名称

	public String admin(){
		// 检测当前用户是否是管理员
		User user_on= (User) session.get("user");
		if(user_on==null || user_on.getIsSuper()==0){
			request.setAttribute("msg","您没有管理员权限!");
			return ERROR;
		}
		return "admin";
	}

	public String users(){
		if(admin()==ERROR)return ERROR;
		String key = request.getParameter("key");
		List<User> users = new UserDao().find_members(key==null?"":key,false);
		request.setAttribute("users",users);
		return "users";
	}

	public String editNews(){
		if(admin()==ERROR)return ERROR;
		String edit = request.getParameter("edit");
		if(edit!=null && edit.length()>0){  //编辑新闻
			int editId = Integer.valueOf(edit);
			News news = new NewsDao().findById(editId);
			request.setAttribute("news",news);  //传给前台新闻
			request.setAttribute("addNews",false);
		}else{
			request.setAttribute("addNews",true);//新添加新闻
		}
		return "editNews";
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
