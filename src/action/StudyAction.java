package action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import models.StudyNode;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class StudyAction extends ActionSupport implements ModelDriven<StudyNode>, ServletRequestAware, SessionAware {
	public StudyNode studyNode=new StudyNode();
	private HttpServletRequest request;
	private Map<String,Object> session;
	private String result;  //用于返回json数据

	public String study(){
		if(false){
			//无权用户不能在这里学习
			request.setAttribute("msg","权限不足!");
			return ERROR;
		}
		return "study";
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
