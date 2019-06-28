package action;

import Tools.IntegerTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.NewsDao;
import models.News;
import models.User;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
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
	private List dataList; //用于返回结果集
	private boolean res=false;	//执行结果 true or fale
	private String msg="";		//结果信息


	public boolean check_admin(){
		// 检测当前用户是否是管理员
		User user_on= (User) session.get("user");
		if(user_on==null || (user_on.getPower()&1)==0 ){
			msg="您没有管理员权限!";
			return false;
		}
		return true;
	}

	public String admin_list(){
		if(!check_admin())return ERROR;
		int pageSize=13;
		String key = request.getParameter("key");
		int num = IntegerTool.strToInt(request.getParameter("page"),1);
		dataList = new NewsDao().findPage(num,pageSize,
				"from News"
						+(key==null?"":" where title like '%"+key+"%'")
						+" order by created desc");request.setAttribute("page",num);
		request.setAttribute("key",key);
		request.setAttribute("page",num);
		request.setAttribute("pageCount",new NewsDao().pageCount(pageSize,"from News"+(key==null?"":" where title like '%"+key+"%'")));
		return "admin_list";
	}

	public String change_public(){
		if(!check_admin())return ERROR;
		JSONObject json=new JSONObject();
		news=new NewsDao().findById(news.getId());
		news.setIsPublic(news.getIsPublic()^1);
		new NewsDao().update(news);
		json.put("res",true);
		result=json.toString();
		return "json";
	}
	public String delete(){
		User user_on = (User) session.get("user");
		JSONObject json=new JSONObject();
		if(news.getId()==1){
			json.put("res",false);
			json.put("msg","权限不足");
		}else{
			new NewsDao().delete(news);
			json.put("res",true);
		}
		result=json.toString();
		return "json";
	}

	public String editNews(){
		if(!check_admin())return ERROR;
		News aimNews = new NewsDao().findById(news.getId());
		if(aimNews==null && news.getId()==1){
			//首页
			new NewsDao().executeSQL("insert into `news`(id,title,created,modified)" +
					" values(1,'首页',now(),now())");
		}
		if(aimNews!=null){  //编辑新闻
			news=aimNews;
			res=true;
			System.out.println("正在编辑新闻"+news.getId());
		}else{
			res=false;
			System.out.println("正在添加新闻");
		}
		return "editNews";
	}

	public String saveNews(){
		if(check_admin()==false)return ERROR;
		News aimNews = new NewsDao().findById(news.getId());
		if(aimNews==null){
			//添加新闻
			news.setViews(0);
			news.setCreated(new Timestamp(new Date().getTime()));
			news.setModified(new Timestamp(new Date().getTime()));
			new NewsDao().add(news); //添加
			res=true;
			msg="新闻添加成功！";
		}else{
			//编辑新闻
			aimNews.setTitle(news.getTitle());
			aimNews.setContent(news.getContent());
			aimNews.setIsPublic(news.getIsPublic());
			aimNews.setModified(new Timestamp(new Date().getTime()));
			new NewsDao().update(aimNews); //更新
			res=true;
			msg="新闻修改成功！";
		}
		return SUCCESS;
	}

	public String news(){
		//获取所有公开的新闻，前台展示
		int pageSize=13;
		String key = request.getParameter("key");
		int num = IntegerTool.strToInt(request.getParameter("page"),1);
		dataList = new NewsDao().findPage(num,pageSize,
				"from News where isPublic=1"
						+(key==null?"":" and title like '%"+key+"%'")
						+" order by created desc");
		request.setAttribute("page",num);
		request.setAttribute("key",key);
		request.setAttribute("pageCount",new NewsDao().pageCount
				(pageSize,"from News where isPublic=1"+(key==null?"":" and title like '%"+key+"%'")));
		return "news";
	}

	public String show(){
		//获取所有公开的新闻，前台展示
		news = new NewsDao().findById(news.getId());
		if(news==null || news.getIsPublic()==0){
			res=false;
			msg="没有找到该新闻！";
			return ERROR;
		}
		news.setViews(news.getViews()+1); // 浏览量+1
		new NewsDao().update(news);
		return "show";
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

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public boolean isRes() {
		return res;
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
}

