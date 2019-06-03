package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Native;

@Entity
@Table(name = "study_subject")
public class StudySubject {
	@Id
	@Native
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "priority")
	private int priority;

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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "StudySubject{" +
				"id=" + id +
				", name='" + name + '\'' +
				", priority=" + priority +
				'}';
	}
}
