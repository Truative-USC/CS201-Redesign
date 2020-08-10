package objects;

import java.util.List;

public class Test {
	private String title;
	public void setTitle(String title) {
		this.title = title;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	private List<File> files;

	public String getTitle() {
		return title;
	}

	public List<File> getFiles() {
		return files;
	}
}
