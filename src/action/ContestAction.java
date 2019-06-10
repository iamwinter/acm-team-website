package action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import models.Contest;
import models.News;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class ContestAction extends ActionSupport implements ModelDriven<Contest>, ServletRequestAware, SessionAware {
	private Contest contest = new Contest();
	private HttpServletRequest request;
	private Map<String,Object> session;

	private String result;  //用于返回json数据
	private boolean res=false;	//执行结果 true or fale
	private String msg="";		//结果信息
	private List dataList; //用于返回结果集

	//TO DO 在这里写功能函数

	@Override
	public Contest getModel() {
		return contest;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
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
