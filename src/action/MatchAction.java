package action;

import com.opensymphony.xwork2.ModelDriven;
import models.Match;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MatchAction implements ModelDriven<Match>, ServletRequestAware, SessionAware {
	private Match match = new Match();
	private HttpServletRequest request;
	private Map<String,Object> session;
	private String result;  //用于返回json数据

	//TO DO 在这里写功能函数

	public String getResult() {
		return result;
	}
	@Override
	public Match getModel() {
		return match;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

}
