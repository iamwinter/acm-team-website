package action;

import Tools.MyConfig;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.StudyFileDao;
import dao.StudyFolderDao;
import dao.UserDao;
import models.StudyFile;
import models.User;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 模型驱动参考：https://blog.csdn.net/SHU15121856/article/details/80089820
 */

public class UserAction extends ActionSupport implements ModelDriven<User>, ServletRequestAware, SessionAware {
	public User user=new User();
	private HttpServletRequest request;
	private Map<String,Object> session;
	//下面是上传头像用的
	private File upFile; //得到上传的文件
	private String upFileContentType; //得到文件的类型
	private String upFileFileName; //得到文件的名称

	private String result;  //用于返回json数据
	private List dataList; //用于返回结果集
	private boolean res=false;	//执行结果 true or fale
	private String msg="";		//结果信息


	public String login(){
		User utemp = (User) session.get("user");
		if(utemp!=null){
			res=false;
			msg = "您已登录，请先登出！";
			return ERROR;
		}
		if (user.getUsername()==null || user.getUsername().length()<4){
			res=true;
			return LOGIN;//首次进入该页面，直接登录
		}
		User userGet=new UserDao().loginUser(user.getUsername(),user.getPassword());
		if(userGet==null) {
			res=false;
			msg = "用户名或密码不正确！";
			return LOGIN;
		}
		session.put("user",userGet);//登录
		System.out.println("用户登录："+userGet);
		res=true;
		msg="登陆成功";
		return SUCCESS;
	}

	public String logout(){
		System.out.println("用户登出："+session.get("user"));
		session.remove("user");
		return SUCCESS;
	}

	public String register(){
		User utemp = (User) session.get("user");
		if(utemp!=null){
			res=false;
			msg="您已登录! 不可注册！请先登出！";
			return ERROR;
		}
		if(user.getUsername()==null || user.getUsername().length()==0){
			res=true;
			return "register"; //首次进入注册页面
		}
		res=false;
		User userGet=new UserDao().findByUsername(user.getUsername());
		if(userGet!=null){
			msg="该用户名已经被用过了，换一个吧！";
			return "register";
		}
		userGet=new UserDao().findByEmail(user.getEmail());
		if(user.getEmail().length()>0&&userGet!=null){
			msg="该邮箱已经被注册过了，换一个吧！";
			return "register";
		}
		//接下来可以注册了
		new UserDao().register(user);
		res=true;
		msg="注册成功!";
		session.put("user",new UserDao().findByUsername(user.getUsername()));//登录
		return SUCCESS;
	}

	public String modify(){
		User user_on= (User) session.get("user");
		if(user_on==null){
			res=false;
			msg="您尚未登录，无法进行修改！";
			return ERROR;
		}
		User aimUser=new UserDao().findByUsername(user.getUsername());//修改目标
		if(user_on.getIsSuper()==0 || aimUser==null){
			aimUser=user_on;	//修改他自己
		}
		request.setAttribute("aimUser",aimUser);
		return "modify";
	}

	public String modify_pwd(){
		/**
		 * 仅修改密码
		 */
		User user_on= (User) session.get("user"); //当前在线用户
		JSONObject json=new JSONObject();
		String old_pwd=request.getParameter("old_password");
		String new_pwd=user.getPassword();
		System.out.println("用户输入旧密码："+old_pwd);

		User userGet=new UserDao().findById(user.getId());
		if(userGet.getPassword().equals(User.encode(old_pwd))
				|| user_on.getIsSuper()==1 && userGet.getIsSuper()==0 ){
			//旧密码对了 或者 管理员在操作普通用户
			userGet.setPassword(new_pwd);
			new UserDao().update(userGet);
			if(user_on.getId()==userGet.getId()){
				session.put("user",userGet);//用户自我修改，强制下线重新上线 覆盖 登录
			}
			json.put("res","true");
		}else{
			json.put("res","false");
		}
		result=json.toString();
		return "json";
	}

	public String modify_info(){
		/**
		 * 基本信息修改
		 */
		User user_on= (User) session.get("user"); //当前在线用户
		JSONObject json=new JSONObject();

		User userGet=new UserDao().findByEmail(user.getEmail());
		if(user.getEmail().length()>4 && user.getId()!=userGet.getId()){
			json.put("res","false");
			json.put("msg","该邮箱已经被别人用过了，换一个吧！");
		}else if(user_on.getId()==user.getId() || user_on.getIsSuper()==1 && userGet.getIsSuper()==0){
//			用户自我修改 || 管理员修改普通成员
			User aimUser=new UserDao().findById(user.getId());
			aimUser.setEmail(user.getEmail());
			aimUser.setTag(user.getTag());
			aimUser.setMajor(user.getMajor());
			aimUser.setBlogUrl(user.getBlogUrl());
			aimUser.setNickName(user.getNickName());
			aimUser.setGrade(user.getGrade());
			aimUser.setWork(user.getWork());
			aimUser.setResume(user.getResume());
			new UserDao().update(aimUser);
			if(user_on.getId()==user.getId()){
				session.put("user",aimUser);//用户自我修改，强制下线重新上线 覆盖 登录
			}
			json.put("res","true");
		}else{
			json.put("res","false");
			json.put("msg","你没有权限修改该用户");
		}
		result=json.toString();
		return "json";
	}

	public String modify_photo(){
		/**
		 * 仅修改照片
		 */
		User user_on=((User)session.get("user"));
		User aimUser=new UserDao().findById(user.getId());
		JSONObject json=new JSONObject();
		if(aimUser==null){
			json.put("res","false");
			json.put("msg","修改失败，你可能已掉线或者用户不存在！");
			result=json.toString();
			return "json";
		}
		if(upFileFileName==null){
			json.put("res","false");
			json.put("msg","您没有选择任何文件！");
			result=json.toString();
			return "json";
		}
		StudyFile studyFile =new StudyFileDao().add(upFile,
				MyConfig.get("upload")+"/photo/"+aimUser.getUsername(),
				upFileFileName, user_on); //上传文件
		if(studyFile ==null){
			json.put("res","false");
			json.put("msg","文件上传失败！");
		}else if(user_on.getId()==user.getId() || user_on.getIsSuper()==1 && aimUser.getIsSuper()==0){
//			用户自我修改 || 管理员修改普通成员
			Integer last_photo = aimUser.getPhotoId();//之前的头像
			aimUser.setPhotoId(studyFile.getId());	//给用户更换图片
			new UserDao().update(aimUser);	//更新到数据库
			new StudyFileDao().delete(new StudyFileDao().findById(last_photo)); //删除之前的照片
			if(user_on.getId()==aimUser.getId()){
				session.put("user",aimUser);//用户自我修改，强制下线重新上线 覆盖 登录
			}
			json.put("res","true");
			json.put("msg","头像修改成功！");
		}else{
			json.put("res","false");
			json.put("msg","您没有权限修改该用户！");
		}
		result=json.toString();
		return "json";
	}

	public String admin_users(){
		User user_on= (User) session.get("user");
		if(user_on==null || user_on.getIsSuper()==0){
			msg="权限不足";
			return ERROR;
		}
		String key = request.getParameter("key");
		dataList = new UserDao().find_members(key==null?"":key,false);
		return "admin_users";
	}

	public String members(){
		User user_on= (User) session.get("user");
		if(user_on==null){
			msg="您尚未登录，不允许查看个人信息！请先登录";
			return LOGIN;
		}
		String key = request.getParameter("key");
		dataList = new UserDao().find_members(key==null?"":key,true);
		return "members";
	}

	public String user(){
		User user_on= (User) session.get("user");
		if(user_on==null){
			res=false;
			msg="您尚未登录，不允许查看个人信息！";
			return ERROR;
		}
		User temp=new UserDao().findByUsername(user.getUsername());
		request.setAttribute("aimUser", temp==null?user_on:temp);
		return "user";
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session=map;
	}

	@Override
	public User getModel() {
		return user;
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

	public void setUpFile(File upFile) {
		this.upFile = upFile;
	}

	public void setUpFileContentType(String upFileContentType) {
		this.upFileContentType = upFileContentType;
	}

	public void setUpFileFileName(String upFileFileName) {
		this.upFileFileName = upFileFileName;
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
