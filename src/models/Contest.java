package models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Native;
import java.sql.Timestamp;

@Entity
@Table(name = "contest")
public class Contest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "describe")
	private String describe;
	@Column(name = "start")
	private Timestamp start;
	@Column(name = "end")
	private Timestamp end;
	@Column(name = "url")
	private String url;

	public int getId() {
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Contest{" +
				"id=" + id +
				", title='" + title + '\'' +
				", describe='" + describe + '\'' +
				", start=" + start +
				", end=" + end +
				", url='" + url + '\'' +
				'}';
	}
}
