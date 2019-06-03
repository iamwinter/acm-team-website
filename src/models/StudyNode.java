package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Native;

@Entity
@Table(name = "study_node")
public class StudyNode {
	@Id
	@Native
	private int id;
	@Column(name = "father_id")
	private int fatherId;
	@Column(name = "year")
	private int year;
	@Column(name = "subject_id")
	private int subjectId;
	@Column(name = "title")
	private String title;
	@Column(name = "describe")
	private String describe;
	@Column(name = "file_id")
	private int fileId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFatherId() {
		return fatherId;
	}

	public void setFatherId(int fatherId) {
		this.fatherId = fatherId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "StudyNode{" +
				"id=" + id +
				", fatherId=" + fatherId +
				", year=" + year +
				", subjectId=" + subjectId +
				", title='" + title + '\'' +
				", describe='" + describe + '\'' +
				", fileId=" + fileId +
				'}';
	}
}
