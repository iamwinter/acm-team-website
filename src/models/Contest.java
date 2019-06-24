package models;

import Tools.IntegerTool;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Native;
import java.sql.Timestamp;

@Entity
@Table(name = "contest")
public class Contest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "title")
	private String title;
	@Column(name = "startTime")
	private Timestamp startTime;
	@Column(name = "endTime")
	private Timestamp endTime;
	@Column(name = "url")
	private String url;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Contest{" +
				"id=" + id +
				", title='" + title + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", url='" + url + '\'' +
				'}';
	}
}
