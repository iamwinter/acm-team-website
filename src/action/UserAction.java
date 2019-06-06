package action;

import Tools.MyConfig;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.FileDao;
import dao.UserDao;
import models.SqlFile;
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
	private String result;  //用于返回json数据

	//下面是上传头像用的
	private File upFile; //得到上传的文件
	private String upFileContentType; //得到文件的类型
	private String upFileFileName; //得到文件的名称

	public String login(){
		User utemp = (User) session.get("user");
		if(utemp!=null){
			request.setAttribute("res",false);
			request.setAttribute("msg","请求被禁止! 您已登录，请先登出！");
			return ERROR;
		}
		String username=user.getUsername();
		String password=user.getPassword();
		if (username==null || username.length()<4){
			return LOGIN;//首次进入该页面，直接登录
		}
		User userGet=new UserDao().loginUser(username,password);
		if(userGet!=null) {
			session.put("user",userGet);//登录
			System.out.println("用户登录："+userGet);
			request.setAttribute("res",true);
			request.setAttribute("msg","登陆成功");
			return SUCCESS;
		}
		request.setAttribute("res",false);
		request.setAttribute("msg","用户名或密码不正确！");
		return LOGIN;
	}

	public String logout(){
		System.out.println("用户登出："+session.get("user"));
		session.remove("user");
		return "json";
	}

	public String register(){
		User utemp = (User) session.get("user");
		if(utemp!=null){
			request.setAttribute("res",false);
			request.setAttribute("msg","您已登录! 不可注册！请先登出！");
			return "register";
		}
		if(user.getUsername()==null || user.getUsername().length()==0){
			return "register"; //首次进入注册页面
		}
		request.setAttribute("res",false);
		User userGet=new UserDao().findByUsername(user.getUsername());
		if(userGet!=null){
			request.setAttribute("msg","该用户名已经被用过了，换一个吧！");
			return "register";
		}
		userGet=new UserDao().findByEmail(user.getEmail());
		if(user.getEmail().length()>0&&userGet!=null){
			request.setAttribute("msg","该邮箱已经被注册过了，换一个吧！");
			return "register";
		}
		//接下来可以注册了
		new UserDao().add(user);
		request.setAttribute("res","true");
		request.setAttribute("msg","注册成功");
		session.put("user",new UserDao().findByUsername(user.getUsername()));//登录
		return SUCCESS;
	}

	public String modify(){
		User user= (User) session.get("user");
		if(user==null){
			request.setAttribute("res",false);
			request.setAttribute("msg","您尚未登录，无法进行修改！");
			return ERROR;
		}
		User aimUser=user;
		if(user.getIsSuper()==1 && !String.valueOf(user.getId()).equals(request.getParameter("username")) ){
			User temp=new UserDao().findByUsername(request.getParameter("username"));
			if(temp!=null)aimUser=temp;
		}
		request.setAttribute("aimUser",aimUser);
		SqlFile photo = new FileDao().findById(aimUser.getPhotoId());
		if(photo!=null)
			request.setAttribute("photo_path",photo.getPath() );
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
		SqlFile sqlFile=new FileDao().add(upFile,MyConfig.get("upload")+"/photo/"+aimUser.getUsername(),
				upFileFileName, user_on.getUsername()); //上传文件
		if(sqlFile==null || sqlFile.getId()==0 ){
			json.put("res","false");
			json.put("msg","文件上传失败！");
		}else if(user_on.getId()==user.getId() || user_on.getIsSuper()==1 && aimUser.getIsSuper()==0){
//			用户自我修改 || 管理员修改普通成员
			int last_photo_id = aimUser.getPhotoId();
			aimUser.setPhotoId(sqlFile.getId());	//给用户更换图片
			new UserDao().update(aimUser);
			new FileDao().delete(new FileDao().findById(last_photo_id)); //删除之前的照片
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

	public String members(){
		User user_on= (User) session.get("user");
		if(user_on==null){
			request.setAttribute("msg","您尚未登录，不允许查看个人信息！请先登录");
			return ERROR;
		}
		String key = request.getParameter("key");
		List<User> users = new UserDao().find_members(key==null?"":key,true);
		request.setAttribute("users",users);
		return "members";
	}

	public String user(){
		User user_on= (User) session.get("user");
		if(user_on==null){
			request.setAttribute("msg","您尚未登录，不允许查看个人信息！");
			return ERROR;
		}
		User temp=new UserDao().findByUsername(request.getParameter("username"));
		if(temp==null)
			temp = user_on;
		request.setAttribute("aimUser", temp);
		SqlFile sf = new FileDao().findById(temp.getPhotoId());
		request.setAttribute("photo_path",sf!=null ? sf.getPath() : null);
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
}
