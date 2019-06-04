package action;

import Tools.MyConfig;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.FileDao;
import dao.StudyNodeDao;
import dao.StudySubjectDao;
import models.*;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

public class StudyAction extends ActionSupport implements ModelDriven<StudyNode>, ServletRequestAware, SessionAware {
	public StudyNode studyNode=new StudyNode();
	private HttpServletRequest request;
	private Map<String,Object> session;
	private String result;  //用于返回json数据

	private File[] upFile; //得到上传的文件
	private String[] upFileContentType; //得到文件的类型
	private String[] upFileFileName; //得到文件的名称

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
		int fatherId = studyNode.getFatherId();
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
	public String upload(){
		//资料文件上传 批量
		if(!admin())return ERROR;
		JSONObject json = new JSONObject();
		User user_on = (User) session.get("user");
		int folderId = studyNode.getFatherId();	//目标文件夹id
		StudyNode fatherFolder = new StudyNodeDao().findById(folderId);//获取目标文件夹
		SqlFile[] sqlFile=new SqlFile[upFile.length]; //数据库记录
		for (int i=0;i<upFile.length;i++){
			sqlFile[i]=new FileDao().add(upFile[i],
					MyConfig.get("upload")+"/study/year"+fatherFolder.getForYear()+"/subject"+fatherFolder.getSubjectId()+"/folder"+folderId,
					upFileFileName[i],
					user_on.getUsername()); //上传文件
			//增加资料记录
			StudyNode sn = new StudyNode();
			sn.setTitle("文件");
			sn.setResume(studyNode.getResume());
			sn.setForYear(fatherFolder.getForYear());
			sn.setSubjectId(fatherFolder.getSubjectId());
			sn.setFatherId(folderId);
			sn.setFileId(sqlFile[i].getId());
			new StudyNodeDao().add(sn);	//添加学习资料
		}
		json.put("res",true);
		json.put("msg","文件上传成功");
		result=json.toString();
		return "json";
	}

	public String getResult() {
		return result;
	}

	public void setUpFile(File[] upFile) {
		this.upFile = upFile;
	}

	public void setUpFileContentType(String[] upFileContentType) {
		this.upFileContentType = upFileContentType;
	}

	public void setUpFileFileName(String[] upFileFileName) {
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
