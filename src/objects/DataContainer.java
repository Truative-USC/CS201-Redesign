package objects;

import java.util.ArrayList;
import java.util.List;

public class DataContainer {
	private List<School> schools;
	
	public void setSchools(List<School> schools) {
		this.schools = schools;
	}

	public DataContainer(){
		schools = new ArrayList<>();
	}
	
	public List<School> getSchools(){
		return schools;
	}
	
	
	public void organize(){
		
		if (schools != null){
			
			for (School school : schools){
				
				if (school.getDepartments() != null){
					
					for (Department dept : school.getDepartments()){
						
						if (dept.getCourses() != null){
							
							for (Course course : dept.getCourses()){
								course.organize();
							}
						}
					}
				}
			}
		}
	}
}
