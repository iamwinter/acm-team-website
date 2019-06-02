package models;

import Tools.DateTool;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Native;
import java.sql.Timestamp;

@Entity
@Table(name = "sql_file")
public class SqlFile {
	@Id
	@Native
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "path")
	private String path;
	@Column(name = "username")
	private String username;
	@Column(name = "created")
	private Timestamp created;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreated() {
		return DateTool.dateToStr(this.created);
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "SqlFile{" +
				"id=" + id +
				", name='" + name + '\'' +
				", path='" + path + '\'' +
				", username='" + username + '\'' +
				", created=" + created +
				'}';
	}
}
