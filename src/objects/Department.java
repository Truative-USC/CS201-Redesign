package objects;

import java.util.ArrayList;
import java.util.List;

public class Department {
	private String longName;
	public void setLongName(String longName) {
		this.longName = longName;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	private String prefix;
	private List<Course> courses;

	public Department() {
		courses = new ArrayList<>();
	}

	public String getLongName() {
		return longName;
	}

	public String getPrefix() {
		return prefix;
	}

	public List<Course> getCourses() {
		return courses;
	}
}
