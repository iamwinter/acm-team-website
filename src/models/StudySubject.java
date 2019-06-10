package models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Native;

@Entity
@Table(name = "study_subject")
public class StudySubject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "priority")
	private Integer priority;

	public Integer getId() {
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

	public Integer getPriority() {
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
