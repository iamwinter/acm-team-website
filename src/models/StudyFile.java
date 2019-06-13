package models;

import Tools.DateTool;

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
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="folder_id",referencedColumnName="id")
	private StudyFolder folder;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StudyFolder getFolder() {
		return folder;
	}

	public void setFolder(StudyFolder folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return "StudyFile{" +
				"id=" + id +
				", name='" + name + '\'' +
				", path='" + path + '\'' +
				", created=" + created +
				", user=" + user +
				", folder=" + folder +
				'}';
	}
}
