package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Schedule {
	private List<Textbook> textbooks;
	public void setTextbooks(List<Textbook> textbooks) {
		this.textbooks = textbooks;
	}

	public void setWeeks(List<Week> weeks) {
		this.weeks = weeks;
	}

	private List<Week> weeks;

	public List<Textbook> getTextbooks() {
		return textbooks;
	}

	public List<Week> getWeeks() {
		return weeks;
	}
	
	/*
	 * Organize each week of the schedule
	 */
	public void organize(Map<String, List<GeneralAssignment>> mappedAssignments) {
		weeks.stream().forEach(week -> week.organize(mappedAssignments));
	}

	//return a list of identifiers for topics that contain the query string in their title
	public List<String> matchTopics(String query) {
		List<String> results = new ArrayList<>();
		
		for (Week week : weeks){
			
			for (Lecture lecture : week.getLectures()){
				
				for (Topic topic : lecture.getTopics()){
					//if the topic title contains the query string
					if (topic.getTitle().toLowerCase().contains(query.toLowerCase())){
						//add the topic identifier to the list of results
						//the topic identifier is constructed as follows: <week_number>,<lecture_number>,<topic_number>
						results.add(week.getWeek()+","+lecture.getNumber()+","+topic.getNumber());
					}
				}
			}
		}
		return results;
	}
}
