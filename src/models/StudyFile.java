package models;

import Tools.DateTool;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "study_file")
public class StudyFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "path")
	private String path;
	@Column(name = "created")
	private Timestamp created;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "folder_id")
	private Integer folderId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCreated() {
		return DateTool.dateToStr(this.created);
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	@Override
	public String toString() {
		return "StudyFile{" +
				"id=" + id +
				", name='" + name + '\'' +
				", path='" + path + '\'' +
				", created=" + created +
				", userId=" + userId +
				", folderId=" + folderId +
				'}';
	}
}
