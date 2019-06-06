package action;

import Tools.IntegerTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.FileDao;
import dao.StudyNodeDao;
import dao.StudySubjectDao;
import models.*;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

import java.util.List;
import java.util.Map;

public class StudyAction extends ActionSupport implements ModelDriven<StudyNode>, ServletRequestAware, SessionAware {
	public StudyNode studyNode=new StudyNode();
	private HttpServletRequest request;
	private Map<String,Object> session;
	private String result;  //用于返回json数据

	private File upFile; //得到上传的文件
	private String upFileContentType; //得到文件的类型
	private String upFileFileName; //得到文件的名称

	public boolean admin(){
		// 检测当前用户是否是管理员
		User user_on= (User) session.get("user");
		if(user_on==null || user_on.getIsSuper()==0){
			request.setAttribute("msg","您没有管理员权限!");
			return false;
		}
		return true;
	}

	private boolean check_access(){
		User user_on = (User) session.get("user");
		if(user_on==null){
			request.setAttribute("res",false);
			request.setAttribute("msg","请您先登录!");
			return false;
		}
		if(user_on.getIsPublic()==0){
			request.setAttribute("res",false);
			request.setAttribute("msg","您没有权限查看该页面!");
			return false;
		}
		return true;
	}

	public String study(){
		// 展示所有当前年份和科目下的 文件夹
		if(!check_access())return ERROR;
		List<StudySubject> subjects = new StudySubjectDao().getList();
		Integer year = studyNode.getForYear();
		Integer subjectId = studyNode.getSubjectId();
		if(year==null){
			year=new StudyNodeDao().getMaxYear();
		}
		if(subjectId==null)subjectId=subjects.get(0).getId();

		System.out.println("当前年份和科目编号："+year+"   "+subjectId);
		List list = new StudyNodeDao().findFolders(year,subjectId);//读取所有文件夹
		System.out.println("读取到文件夹数量："+list.size());
		request.setAttribute("folders",list);//当前条件下所有文件夹
		request.setAttribute("subjects",subjects);//当前条件下所有科目
		request.setAttribute("years",new StudyNodeDao().getAllYears());
		request.setAttribute("forYear",year);//当前年份
		request.setAttribute("subjectId",subjectId);//当前科目编号
		return "study";
	}

	public String files(){
		// 展示具体某一个文件夹下的所有文件,未完成!!
		if(!check_access())return ERROR;
		Integer fatherId = studyNode.getFatherId();
		if(fatherId==null){
			request.setAttribute("msg","未指定文件夹或文件夹不存在");
			return ERROR;
		}
		List list = new StudyNodeDao().findFiles(fatherId);
		request.setAttribute("files",list);
		return "file_show";
	}

	public String addFolder(){
		//添加文件夹
		if(!admin())return ERROR;
		JSONObject json=new JSONObject();
		new StudyNodeDao().add(studyNode);
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
		StudyNode sn = new StudyNodeDao().findByFileId(fileId); //文件对应的学习资料记录
		new StudyNodeDao().delete(sn);
		new FileDao().delete(new FileDao().findById(fileId));	//此方法将永久删除文件
		json.put("res",true);
		json.put("msg","文件删除成功");
		result=json.toString();
		return "json";
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

	@Override
	public StudyNode getModel() {
		return studyNode;
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
