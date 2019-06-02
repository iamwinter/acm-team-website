package models;

import Tools.DateTool;

import javax.persistence.*;
import java.lang.annotation.Native;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "news")
public class News {
	@Id
	@Native
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "content")
	private String content;
	@Column(name = "is_public")
	private Integer isPublic;
	@Column(name = "views")
	private Integer views;
	@Column(name = "created")
	private Timestamp created;
	@Column(name = "modified")
	private Timestamp modified;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public String getCreated() {
		return DateTool.dateToStr(this.created);
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getModified() {
		return DateTool.dateToStr(this.modified);
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		News news = (News) o;
		return id == news.id &&
				Objects.equals(title, news.title) &&
				Objects.equals(content, news.content) &&
				Objects.equals(isPublic, news.isPublic) &&
				Objects.equals(views, news.views) &&
				Objects.equals(created, news.created) &&
				Objects.equals(modified, news.modified);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, content, isPublic, views, created, modified);
	}

	@Override
	public String toString() {
		return "News{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", isPublic=" + isPublic +
				", views=" + views +
				", created=" + created +
				", modified=" + modified +
				'}';
	}
}
