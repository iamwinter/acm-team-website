package models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Native;
import java.sql.Timestamp;

@Entity
@Table(name = "match")
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="news_id",referencedColumnName="id")
	private News news;
	@Column(name = "title")
	private String title;
	@Column(name = "date")
	private Timestamp date;
	@Column(name = "prize1")
	private int prize1;
	@Column(name = "prize2")
	private int prize2;
	@Column(name = "prize3")
	private int prize3;
	@Column(name = "prize4")
	private int prize4;

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

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getPrize1() {
		return prize1;
	}

	public void setPrize1(int prize1) {
		this.prize1 = prize1;
	}

	public int getPrize2() {
		return prize2;
	}

	public void setPrize2(int prize2) {
		this.prize2 = prize2;
	}

	public int getPrize3() {
		return prize3;
	}

	public void setPrize3(int prize3) {
		this.prize3 = prize3;
	}

	public int getPrize4() {
		return prize4;
	}

	public void setPrize4(int prize4) {
		this.prize4 = prize4;
	}

	@Override
	public String toString() {
		return "Match{" +
				"id=" + id +
				", newsId=" + news +
				", title='" + title + '\'' +
				", date=" + date +
				", prize1=" + prize1 +
				", prize2=" + prize2 +
				", prize3=" + prize3 +
				", prize4=" + prize4 +
				'}';
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
}
