package objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Week {
	public void setWeek(Integer week) {
		this.week = week;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public void setMappedDueDates(Map<Integer, List<GeneralAssignment>> mappedDueDates) {
		this.mappedDueDates = mappedDueDates;
	}

	public void setRowSize(Integer rowSize) {
		this.rowSize = rowSize;
	}

	public void setNumDueDates(Integer numDueDates) {
		this.numDueDates = numDueDates;
	}

	public void setLabs(List<Lab> labs) {
		this.labs = labs;
	}

	private Integer week;
	private List<Lecture> lectures;
	private Map<Integer, List<GeneralAssignment>> mappedDueDates;
	private Integer rowSize;
	private Integer numDueDates;
	private List<Lab> labs;

	public Integer getWeek() {
		return week;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public List<Lab> getLabs() {
		return labs;
	}
	
	public Integer getRowSize(){
		return rowSize;
	}
	
	public Integer getNumDueDates(){
		return numDueDates;
	}
	
	public Map<Integer, List<GeneralAssignment>> getMappedDueDates(){
		return mappedDueDates;
	}
	
	/*
	 * Populate the map that has lecture numbers as keys and lists of GeneralAssignments as values
	 * This is used to display due dates on the lectures page
	 */
	public void organize(Map<String, List<GeneralAssignment>> mappedAssignments){
		
		mappedDueDates = new HashMap<>();
		rowSize = lectures.size();
		numDueDates = 0;
		
		for (Lecture lecture : lectures){
			lecture.organize();
			//if we have some assignments that are due on this lecture date
			if (mappedAssignments.containsKey(lecture.getDate())){
  				List<GeneralAssignment> assignmentsDue = mappedAssignments.get(lecture.getDate());
  				//increment our row size by the number of assignments due
	  			numDueDates += assignmentsDue.size();
	  			
	  			mappedDueDates.put(lecture.getNumber(), assignmentsDue);
  			}
		}
		rowSize += numDueDates;
	}
}
