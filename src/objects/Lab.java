package objects;

import java.util.List;

//lab extends GeneralObject because it has the exact same member variables as deliverables
public class Lab extends GeneralObject{
	private List<File> files;

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<File> getFiles(){
		return files;
	}
}
