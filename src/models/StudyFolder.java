package models;

import javax.persistence.*;
@Entity
@Table(name = "study_folder")
public class StudyFolder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "resume")
	private String resume;
	@Column(name = "for_year")
	private Integer forYear;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="subject_id",referencedColumnName="id")
	private StudySubject subject;


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

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public Integer getForYear() {
		return forYear;
	}

	public void setForYear(Integer forYear) {
		this.forYear = forYear;
	}

	public StudySubject getSubject() {
		return subject;
	}

	public void setSubject(StudySubject subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "StudyFolder{" +
				"id=" + id +
				", title='" + title + '\'' +
				", resume='" + resume + '\'' +
				", forYear=" + forYear +
				", subject=" + subject +
				'}';
	}
}
