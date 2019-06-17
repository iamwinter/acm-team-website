package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.ContestDao;
import dao.MatchDao;
import dao.NewsDao;
import dao.StudySubjectDao;
import models.User;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 模型驱动参考：https://blog.csdn.net/SHU15121856/article/details/80089820
 */

public class AdminAction extends ActionSupport implements ServletRequestAware, SessionAware {

	private HttpServletRequest request;
	private Map<String,Object> session;

	private String result;  //用于返回json数据
	private boolean res=false;	//执行结果 true or fale
	private String msg="";		//结果信息
	private List dataList; //用于返回结果集

	public String index(){
		request.setAttribute("recent_news", new NewsDao().findPublicPage(1,10));//近期10条新闻
		request.setAttribute("recent_contest",new ContestDao().findPage(1,10,"start",false));
		request.setAttribute("recent_match",new MatchDao().findPage(1,10,"date",false)); //近期赛事
		return "index";
	}

	public String admin(){
		// 检测当前用户是否是管理员
		User user_on= (User) session.get("user");
		if(user_on==null || (user_on.getPower()&1)==0){
			msg="您没有管理员权限!";
			return ERROR;
		}
		return "admin";
	}
	public String subject(){
		if(!admin().equals("admin"))return ERROR;
		// 向管理员 展示 学习的科目列表
		dataList = new StudySubjectDao().getList();
		return "subject";
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

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

}
