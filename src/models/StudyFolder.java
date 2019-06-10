package models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Native;

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
	@Column(name = "subject_id")
	private Integer subjectId;


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

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public String toString() {
		return "StudyFolder{" +
				"id=" + id +
				", title='" + title + '\'' +
				", resume='" + resume + '\'' +
				", forYear=" + forYear +
				", subjectId=" + subjectId +
				'}';
	}
}
