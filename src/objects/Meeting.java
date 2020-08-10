package objects;

import java.util.ArrayList;
import java.util.List;

public class Meeting {
	private String type;
	public void setType(String type) {
		this.type = type;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setMeetingPeriods(List<TimePeriod> meetingPeriods) {
		this.meetingPeriods = meetingPeriods;
	}

	public void setAssistants(List<StaffMemberID> assistants) {
		this.assistants = assistants;
	}

	public void setListedTimes(String listedTimes) {
		this.listedTimes = listedTimes;
	}

	public void setListedDays(String listedDays) {
		this.listedDays = listedDays;
	}

	private String section;
	private String room;
	private List<TimePeriod> meetingPeriods;
	private List<StaffMemberID> assistants;
	//strings that will be used on the front end to print meeting times
	private String listedTimes;
	private String listedDays;

	public Meeting() {
		meetingPeriods = new ArrayList<>();
		assistants = new ArrayList<>();
	}

	public String getType() {
		return type;
	}

	public String getRoom() {
		return room;
	}

	public List<TimePeriod> getMeetingPeriods() {
		return meetingPeriods;
	}

	public List<StaffMemberID> getAssistants() {
		return assistants;
	}

	public String getMeetingType() {
		return type;
	}

	public String getSection() {
		return section;
	}
	
	public String getListedTimes(){
		populateStrings();
		return listedTimes;
	}
	
	public String getListedDays(){
		populateStrings();
		return listedDays;
	}
	
	/*
	 * Populates the listedDays and listedTimes strings if they haven't already been created
	 * These strings are used to print the meeting times on the front end in home.jsp
	 */
	private void populateStrings(){
		
		if (listedTimes == null || listedDays == null){
			
			for (int j = 0; j<meetingPeriods.size(); j++)
        	{
        		TimePeriod period = meetingPeriods.get(j);
        		if (listedDays == null){
        			listedDays = period.getDay();
        			listedTimes = period.getTime().getStartTime() + " - " + period.getTime().getEndTime();
        		}
        		else{
        			listedDays += "/"+period.getDay();
        			listedTimes += "/"+ period.getTime().getStartTime() + " - " + period.getTime().getEndTime();
        		}
    		}
		}
	}

}