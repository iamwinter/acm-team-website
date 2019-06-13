package action;

import Tools.IntegerTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.StudyFileDao;
import dao.StudyFolderDao;
import dao.StudySubjectDao;
import models.*;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

import java.util.List;
import java.util.Map;

public class StudyFolderAction extends ActionSupport implements ModelDriven<StudyFolder>, ServletRequestAware, SessionAware {
	public StudyFolder studyFolder =new StudyFolder();
	private HttpServletRequest request;
	private Map<String,Object> session;

	private String result;  //用于返回json数据
	private boolean res=false;	//执行结果 true or fale
	private String msg="";		//结果信息
	private List dataList; //用于返回结果集

	public boolean admin(){
		// 检测当前用户是否是管理员
		User user_on= (User) session.get("user");
		if(user_on==null || user_on.getIsSuper()==0){
			msg="您没有管理员权限!";
			return false;
		}
		return true;
	}

	private String check_access(){
		User user_on = (User) session.get("user");
		if(user_on==null){
			res=false;
			msg="请先登录!";
			return LOGIN;
		}
		if(user_on.getIsPublic()==0){
			res=false;
			msg="您没有权限查看该页面!";
			return ERROR;
		}
		return SUCCESS;
	}

	public String folders(){
		// 展示所有当前年份和科目下的 文件夹
		String acc=check_access();
		if(!acc.equals(SUCCESS))return acc;

		List<StudySubject> subjects = new StudySubjectDao().getList();
		Integer year = studyFolder.getForYear();
		Integer subjectId = IntegerTool.strToInt(request.getParameter("subjectId"));
		if(year==null)year=new StudyFolderDao().getMaxYear();
		if(subjectId==null)subjectId=subjects.get(0).getId();

		System.out.println("当前年份和科目编号："+year+"   "+subjectId);
		dataList = new StudyFolderDao().findFolders(year,subjectId);//读取所有文件夹
		System.out.println("读取到文件夹数量："+dataList.size());
		request.setAttribute("subjects",subjects);//当前条件下所有科目
		request.setAttribute("years",new StudyFolderDao().getAllYears());
		studyFolder.setForYear(year);
		studyFolder.setSubject(new StudySubjectDao().findById(subjectId));
		return "study";
	}

	public String files(){
		// 展示具体某一个文件夹下的所有文件
		String acc=check_access();
		if(!acc.equals(SUCCESS))return acc;

		StudyFolder sf = new StudyFolderDao().findById(studyFolder.getId());
		if(sf==null){
			msg="未指定文件夹或文件夹不存在";
			return ERROR;
		}
		dataList = new StudyFileDao().findByFolderId(studyFolder.getId());
		request.setAttribute("folderId",studyFolder.getId());
		studyFolder.setForYear(sf.getForYear());
		studyFolder.setSubject(sf.getSubject());
		return "file_show";
	}

	public String addFolder(){
		//添加文件夹
		if(!admin())return ERROR;
		JSONObject json=new JSONObject();
		System.out.println(studyFolder);
		new StudyFolderDao().add(studyFolder);
		json.put("res",true);
		json.put("msg","文件夹创建成功");
		result=json.toString();
		return "json";
	}


	public String delete_file(){
		//删除一个学习资料
		if(!admin())return ERROR;
		JSONObject json=new JSONObject();
		int fileId = IntegerTool.strToInt(request.getParameter("fileId"));
		new StudyFileDao().delete(new StudyFileDao().findById(fileId));	//此方法将永久删除文件
		json.put("res",true);
		json.put("msg","文件删除成功");
		result=json.toString();
		return "json";
	}


	public StudyFolder getStudyFolder() {
		return studyFolder;
	}

	public void setStudyFolder(StudyFolder studyFolder) {
		this.studyFolder = studyFolder;
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

	@Override
	public StudyFolder getModel() {
		return studyFolder;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request=httpServletRequest;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session=map;
	}

}
