package objects;

import java.util.List;

public class Topic extends GeneralObject {
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}

	private String chapter;
	private List<Program> programs;

	public String getChapter() {
		return chapter;
	}

	public List<Program> getPrograms() {
		return programs;
	}
}
