package action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.NewsDao;
import models.News;
import models.User;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

/**
 * 模型驱动参考：https://blog.csdn.net/SHU15121856/article/details/80089820
 */

public class NewsAction extends ActionSupport implements ModelDriven<News>,ServletRequestAware, SessionAware {
	private News news = new News();
	private HttpServletRequest request;
	private Map<String,Object> session;
	private String result;  //用于返回json数据

	//文件
	private File upFile; //得到上传的文件
	private String upFileContentType; //得到文件的类型
	private String upFileFileName; //得到文件的名称

	public String check_admin(){
		// 检测当前用户是否是管理员
		User user_on= (User) session.get("user");
		if(user_on==null || user_on.getIsSuper()==0){
			request.setAttribute("msg","您没有管理员权限!");
			return ERROR;
		}
		return "admin";
	}

	public String saveNews(){
		if(check_admin()==ERROR)return ERROR;
		String add = request.getParameter("addNews");
		if(add!=null && add.equals("true")){
			//添加新闻
			news.setViews(0);
			news.setCreated(new Timestamp(new Date().getTime()));
			news.setModified(new Timestamp(new Date().getTime()));
			new NewsDao().add(news); //添加
			request.setAttribute("res",true);
			request.setAttribute("msg","新闻添加成功！");
		}else{
			//编辑新闻
			News aimNews = new NewsDao().findById(news.getId());
			if(aimNews==null){
				request.setAttribute("res",false);
				request.setAttribute("msg","新闻不存在,可能已被删除！");
				return ERROR;
			}
			aimNews.setTitle(news.getTitle());
			aimNews.setContent(news.getContent());
			aimNews.setIsPublic(news.getIsPublic());
			aimNews.setModified(new Timestamp(new Date().getTime()));
			new NewsDao().update(aimNews); //更新
			request.setAttribute("res",true);
			request.setAttribute("msg","新闻修改成功！");
		}
		return SUCCESS;
	}

	public String news(){
		//获取所有公开的新闻，前台展示
		return "news";
	}

	@Override
	public News getModel() {
		return news;
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

