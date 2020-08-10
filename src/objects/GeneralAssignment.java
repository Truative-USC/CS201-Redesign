package objects;


import java.util.List;

//the assignment and deliverable classes inherit from this class
public abstract class GeneralAssignment {
	private String gradePercentage;
	public void setGradePercentage(String gradePercentage) {
		this.gradePercentage = gradePercentage;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	private List<File> files;
	private String assignedDate;
	private String number;
	private String dueDate;
	private String title;

	public String getAssignedDate() {
		return assignedDate;
	}
	public String getNumber() {
		return number;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getTitle() {
		return title;
	}

	public String getGradePercentage() {
		return gradePercentage;
	}

	public List<File> getFiles() {
		return files;
	}

}
