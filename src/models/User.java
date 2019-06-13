package models;

import sun.misc.BASE64Encoder;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "email")
	private String email;
	@Column(name = "nick_name")
	private String nickName;
	@Column(name = "is_super")
	private Integer isSuper;
	@Column(name = "is_public")
	private Integer isPublic;
	@Column(name = "tag")
	private Integer tag;    //用户角色：0外部 1退役 2现役 3教师
	@Column(name = "grade")
	private Integer grade;
	@Column(name = "major")
	private String major;
	@Column(name = "work")
	private String work;
	@Column(name = "blog_url")
	private String blogUrl;
	@Column(name = "resume")
	private String resume; //简历
	@Column(name = "photo_path")
	private String photoPath;


	public static String encode(String password) //获取密码的密文
	{
		String enPassword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();

			byte[] pwd=password.getBytes("UTF-8");
			enPassword = base64en.encode(md5.digest(pwd));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return enPassword;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getNickName() {
		return nickName;
	}

	public Integer getIsSuper() {
		return isSuper;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public Integer getTag() {
		return tag;
	}

	public Integer getGrade() {
		return grade;
	}

	public String getMajor() {
		return major;
	}

	public String getWork() {
		return work;
	}

	public String getBlogUrl() {
		return blogUrl;
	}

	public String getResume() {
		return resume;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		if(password.length()!=encode(password).length()){
			password=encode(password);
		}
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setIsSuper(Integer isSuper) {
		this.isSuper = isSuper;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}


	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}



	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", nickName='" + nickName + '\'' +
				", isSuper=" + isSuper +
				", isPublic=" + isPublic +
				", tag=" + tag +
				", grade=" + grade +
				", major='" + major + '\'' +
				", work='" + work + '\'' +
				", blogUrl='" + blogUrl + '\'' +
				", resume='" + resume + '\'' +
				", photoPath='" + photoPath + '\'' +
				'}';
	}
}
