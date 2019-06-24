package action;

import Tools.IntegerTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.ContestDao;
import models.Contest;
import models.News;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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

	public String home(){
		boolean come = !"true".equals(request.getParameter("ending"));
		int page = IntegerTool.strToInt(request.getParameter("page"),1);
		String key = request.getParameter("key");

		if(come){
			dataList = new ContestDao().findPage(page,20,
					"from Contest where startTime>CURRENT_DATE() order by startTime asc");
			res=true;
			msg="等你来战";
		}else{
			dataList = new ContestDao().findPage(page,20,
					"from Contest where endTime<CURRENT_DATE() order by startTime desc");
			res=false;
			msg="温故知新";
			request.setAttribute("ending","true");
		}

		List list = new ContestDao().findPage(page,20,
				"from Contest where startTime<CURRENT_DATE() and endTime>CURRENT_DATE() order by startTime asc");
		request.setAttribute("running",list);
		return "home";
	}

	public String update(){
		System.out.println(contest);
		if(contest.getId()!=null){
			//update
			new ContestDao().update(contest);
		}else{
			new ContestDao().add(contest);
		}
		return "home";
	}

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
