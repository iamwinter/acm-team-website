package models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Native;

@Entity
@Table(name = "study_node")
public class StudyNode {
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "increment")
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "resume")
	private String resume;
	@Column(name = "for_year")
	private Integer forYear;
	@Column(name = "subject_id")
	private Integer subjectId;
	@Column(name = "father_id")
	private Integer fatherId;
	@Column(name = "file_id")
	private Integer fileId;


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

	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "StudyNode{" +
				"id=" + id +
				", title='" + title + '\'' +
				", resume='" + resume + '\'' +
				", forYear=" + forYear +
				", subjectId=" + subjectId +
				", fatherId=" + fatherId +
				", fileId=" + fileId +
				'}';
	}
}
