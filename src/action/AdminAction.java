package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.ContestDao;
import dao.MatchDao;
import dao.NewsDao;
import dao.StudySubjectDao;
import models.News;
import models.StudySubject;
import models.User;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 模型驱动参考：https://blog.csdn.net/SHU15121856/article/details/80089820
 */

public class AdminAction extends ActionSupport implements ServletRequestAware, SessionAware {

	private StudySubject studySubject;	//属性驱动
	private HttpServletRequest request;
	private Map<String,Object> session;

	private String result;  //用于返回json数据
	private boolean res=false;	//执行结果 true or fale
	private String msg="";		//结果信息
	private List dataList; //用于返回结果集

	//网站主页,不是管理员首页
	public String index(){
		request.setAttribute("recent_news", new NewsDao().findPublicPage(1,10));//近期10条新闻
		News homePage = new NewsDao().findById(1);
		homePage.setViews(homePage.getViews()+1);
		new NewsDao().update(homePage);
		request.setAttribute("mainText",homePage);//主页
		return "index";
	}

	// 检测当前用户是否是管理员
	private boolean check_admin(){
		User user_on= (User) session.get("user");
		if(user_on==null || (user_on.getPower()&1)==0){
			msg="您没有管理员权限!";
			return false;
		}
		return true;
	}

	//管理员主页
	public String admin(){
		if(!check_admin())return ERROR;
		return "admin";
	}

	//获取所有科目
	public String subject(){
		if(!check_admin())return ERROR;
		// 向管理员 展示 学习的科目列表
		dataList = new StudySubjectDao().getList();
		return "subject";
	}

	public String add_subject(){
		if(!check_admin())return ERROR;
		new StudySubjectDao().add(studySubject);
		System.out.println(studySubject);
		dataList = new StudySubjectDao().getList();
		return "subject_oper";
	}

	public String delete_subject(){
		if(!check_admin())return ERROR;
		System.out.println(studySubject);
		new StudySubjectDao().delete(studySubject);	//删除有错
		dataList = new StudySubjectDao().getList();
		return "subject_oper";
	}

	public String update_subject(){
		if(!check_admin())return ERROR;
		System.out.println(studySubject);
		new StudySubjectDao().update(studySubject);
		dataList = new StudySubjectDao().getList();
		return "subject_oper";
	}

	public String move_subject(){
		String dir = request.getParameter("dir");
		if("up".equals(dir)){
			new StudySubjectDao().move_pos(studySubject.getId(),true);
		}else if("down".equals(dir)){
			new StudySubjectDao().move_pos(studySubject.getId(),false);
		}
		dataList = new StudySubjectDao().getList();
		return "subject_oper";
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

	public StudySubject getStudySubject() {
		return studySubject;
	}

	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}
}
