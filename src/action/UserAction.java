package action;

import Tools.DateTool;
import Tools.IntegerTool;
import Tools.MyConfig;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import dao.UserDao;
import models.User;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
		return "modify";//注册成功后 完善个人信息
	}

	public String modify(){
		User user_on= (User) session.get("user");
		if(user_on==null){
			res=false;
			msg="请先登录！";
			return LOGIN;
		}
		User aimUser=new UserDao().findByUsername(user.getUsername());//修改目标
		if((user_on.getPower()&1)==0 || aimUser==null){
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
				|| (user_on.getPower()&1)==1 && (user_on.getPower()&1)==0 ){
			//旧密码对了 或者 管理员在操作普通用户
			userGet.setPassword(new_pwd);
			new UserDao().update(userGet);
			if(user_on.getId()==userGet.getId()){
				session.put("user",userGet);//用户自我修改，强制下线重新上线 覆盖 登录
			}
			json.put("res",true);
		}else{
			json.put("res",false);
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
		User aimUser=new UserDao().findById(user.getId());
		if(user.getEmail().length()>4 && user.getId()!=userGet.getId()){
			json.put("res",false);
			json.put("msg","该邮箱已经被别人用过了，换一个吧！");
		}else if( (user_on.getId()==user.getId() || (user_on.getPower()&1)==1 && (aimUser.getPower()&1)==0)
				&& (aimUser.getPower()>>2&1)==0){
//			(用户自我修改 || 管理员修改普通成员) && 未被锁定
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
			json.put("res",true);
		}else{
			json.put("res",false);
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
			aimUser=user_on;
		}
		if(upFileFileName==null){
			json.put("res",false);
			json.put("msg","您没有选择任何文件！");
			result=json.toString();
			return "json";
		}
		if( !aimUser.getId().equals(user_on.getId()) && (user_on.getPower()&1)<=(aimUser.getPower()&1) ){
			//既不是自我修改，也不是管理员超级权限修改，则非法
			json.put("res",false);
			json.put("msg","权限不足！");
			result=json.toString();
			return "json";
		}


		//构造路径
		String path = MyConfig.get("upload")+"/photo/"+aimUser.getUsername() + "/";
		path += DateTool.dateToStr(new Date(),"yyyyMMddhhmmss_");//以上传时间命名
		path += new Random().nextInt(9000)+1000;
		path += upFileFileName.substring(upFileFileName.lastIndexOf(".")); //后缀

		String realPath= ServletActionContext.getServletContext().getRealPath(path); //获取磁盘绝对路径
		System.out.println("相对路径    path: "+path);
		System.out.println("绝对路径realPath: "+realPath);
		try {
			File storeFile = new java.io.File(realPath);	//创建文件
			if(!storeFile.getParentFile().exists())storeFile.getParentFile().mkdirs();//父目录不存在则创建
			FileUtils.copyFile(upFile, storeFile);//copy upFile ==> 磁盘文件
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件保存异常");
			json.put("res",false);
			json.put("msg","文件上传失败！");
			result=json.toString();
			return "json";
		}

		String last_photo_path = aimUser.getPhotoPath();//之前的头像
		aimUser.setPhotoPath(path);	//给用户更换图片
		new UserDao().update(aimUser);	//更新到数据库
		if(user_on.getId()==aimUser.getId()){
			session.put("user",aimUser);//用户自我修改，强制下线重新上线 覆盖 登录
		}

		//下面删除原来的图片
		String last_realPath= ServletActionContext.getServletContext().getRealPath(last_photo_path);
		if(last_photo_path!=null){
			File storeFile = new java.io.File( last_realPath );	//创建文件
			if(storeFile.isFile()){
				storeFile.delete();
				System.out.println("从磁盘中删除文件:"+ last_realPath);
			}
		}

		json.put("res",true);
		json.put("msg","头像修改成功！");
		result=json.toString();
		return "json";
	}

	public String members(){
		User user_on= (User) session.get("user");
		String key = request.getParameter("key");
		List<User> listAll = new UserDao().find_members(key==null?"":key);
		List<User> teacher=new ArrayList<>();	//老师
		Map<Integer,List<User>> student = new HashMap<>();	//按年级分类
		for(User i:listAll){
			if(i.getTag()==3)//老师
				teacher.add(i);
			else if((i.getTag()==1||i.getTag()==2) && i.getGrade()>=2000){
				if(!student.containsKey(i.getGrade()))
					student.put(i.getGrade(),new ArrayList<User>());
				student.get(i.getGrade()).add(i);	//对应年级
			}
		}
		request.setAttribute("teacher",teacher);
		request.setAttribute("student",student);
		request.setAttribute("key",key);
		listAll.clear();
		return "members";
	}

	public String user(){
		User user_on= (User) session.get("user");
		User temp=new UserDao().findByUsername(user.getUsername());
		if(user_on==null&&temp==null){
			res=false;
			msg="未找到用户信息！";
			return ERROR;
		}
		request.setAttribute("aimUser", temp==null?user_on:temp);
		return "user";
	}

	//检查是否是admin
	private boolean check_power(){
		User user_on= (User) session.get("user");
		if( user_on==null || (user_on.getPower()&1)==0 )
			return false;
		return true;
	}

	//展示用户信息
	public String admin_users(){
		if( !check_power() ){
			msg="权限不足";
			return ERROR;
		}
		String key = request.getParameter("key");
		dataList = new UserDao().find_members(key==null?"":key);
		request.setAttribute("key",key);
		return "admin_users";
	}

	//切换用户各种权限
	public String switch_power(){
		JSONObject json=new JSONObject();
		User user_on= (User) session.get("user");
		if( !check_power() ){
			json.put("res",false);
			json.put("msg","您没有权限进行该操作!");
		}else{
			Integer x= IntegerTool.strToInt(request.getParameter("x"));
			user=new UserDao().findById(user.getId());
			if("admin".equals(user.getUsername()) && ( x==0 || !user_on.getId().equals(user.getId())) ){
				json.put("res",false);
				json.put("msg","admin的权限不容侵犯");
			}else{
				user.setPower( user.getPower() ^ (1<<x) );
				new UserDao().update(user);
				json.put("res",true);
				json.put("msg",null);
				if(user_on.getId().equals(user.getId())){
					session.put("user",user);//用户自我修改，强制下线重新上线 覆盖 登录
				}
			}
		}
		result=json.toString();
		return "json";
	}

	//修改用户身份标签
	public String change_tag(){
		JSONObject json=new JSONObject();
		User user_on= (User) session.get("user");
		User aimUser=new UserDao().findById(user.getId());
		if( !check_power() || "admin".equals(aimUser.getUsername()) && !user_on.getId().equals(aimUser.getId())){
			json.put("res",false);
			json.put("msg","你的用户权限过低，不能修改该用户!");
		}else{
			aimUser.setTag(user.getTag());
			new UserDao().update(aimUser);
			json.put("res",true);
			if(user_on.getId().equals(aimUser.getId())){
				session.put("user",aimUser);//用户自我修改，强制下线重新上线 覆盖 登录
			}
		}
		result=json.toString();
		return "json";
	}

	//删除用户
	public String delete_user(){
		JSONObject json=new JSONObject();
		User user_on= (User) session.get("user");
		User aimUser=new UserDao().findById(user.getId());
		if( !check_power() ){
			json.put("res",false);
			json.put("msg","权限不足!");
		}else if( (aimUser.getPower()&1)==1 || user_on.getId().equals(aimUser.getId())){
			json.put("res",false);
			json.put("msg","不能删除管理员或自己!");
		}else{
			new UserDao().delete(aimUser);
			json.put("res",true);
		}
		result=json.toString();
		return "json";
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
