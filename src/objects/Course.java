package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import comparators.GAComparator;
import parsing.StringConstants;

public class Course {
	private String number;
	public void setNumber(String number) {
		this.number = number;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public void setStaffMembers(List<StaffMember> staffMembers) {
		this.staffMembers = staffMembers;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void setFinalProject(Assignment finalProject) {
		this.finalProject = finalProject;
	}

	public void setOfficeHours(StaffMember[][] officeHours) {
		this.officeHours = officeHours;
	}

	public void setOrganized(Boolean organized) {
		this.organized = organized;
	}

	
	public void setSortedMeetings(Map<String, List<Meeting>> sortedMeetings) {
		this.sortedMeetings = sortedMeetings;
	}

	public void setSortedStaff(Map<String, List<StaffMember>> sortedStaff) {
		this.sortedStaff = sortedStaff;
	}

	public void setMappedAssignments(Map<String, List<GeneralAssignment>> mappedAssignments) {
		this.mappedAssignments = mappedAssignments;
	}

	public void setStaffMap(Map<Integer, StaffMember> staffMap) {
		this.staffMap = staffMap;
	}

	private String term;
	private String title;
	private Integer year;
	private Integer units;
	private Syllabus syllabus;
	private Schedule schedule;
	private List<Assignment> assignments;
	private List<Exam> exams;
	private List<StaffMember> staffMembers;
	private List<Meeting> meetings;

	private Assignment finalProject;
	//holds a 2D array of staff members to prepare for displaying the OH in home.jsp
	private StaffMember[][] officeHours;
	private Boolean organized = false;
	//map from the start time string to the column index in officeHours
	private static Map<String, Integer> ohTimeToIndex;
	//map from the day string to the row index in officeHours
	private static Map<String, Integer> ohDayToIndex;
	//ordered list of the office hour days
	public static String[] officeHourDays;
	//ordered list of the office hour start times
	public static String[] officeHourTimes;

	// meetings sorted into a map, key is the meeting type, value is a list of all meetings of that type
	private Map<String, List<Meeting>> sortedMeetings;
	// staff sorted into a map, key is the staff type, value is a list of all staff members of that type
	private Map<String, List<StaffMember>> sortedStaff;
	// map from due date string to assignment and deliverable objects -- used on the lectures page to show due dates
	private Map<String, List<GeneralAssignment>> mappedAssignments;
	//map from staff member ID to staff member object
	private Map<Integer, StaffMember> staffMap;

	//static constructor to initialize and populate static member variables
	static {
		ohTimeToIndex = new HashMap<>();
		ohDayToIndex = new HashMap<>();
		officeHourDays = new String[6];
		officeHourTimes = new String[5];

		officeHourDays[0] = "Monday";
		officeHourDays[1] = "Tuesday";
		officeHourDays[2] = "Wednesday";
		officeHourDays[3] = "Thursday";
		officeHourDays[4] = "Friday";
		officeHourDays[5] = "Saturday";

		officeHourTimes[0] = "10:00a.m.-12:00p.m.";
		officeHourTimes[1] = "12:00p.m.-2:00p.m.";
		officeHourTimes[2] = "2:00p.m.-4:00p.m.";
		officeHourTimes[3] = "4:00p.m.-6:00p.m.";
		officeHourTimes[4] = "6:00p.m.-8:00p.m.";

		ohTimeToIndex.put("10:00a.m.", 0);
		ohTimeToIndex.put("12:00p.m.", 1);
		ohTimeToIndex.put("2:00p.m.", 2);
		ohTimeToIndex.put("4:00p.m.", 3);
		ohTimeToIndex.put("6:00p.m.", 4);

		ohDayToIndex.put("Monday", 0);
		ohDayToIndex.put("Tuesday", 1);
		ohDayToIndex.put("Wednesday", 2);
		ohDayToIndex.put("Thursday", 3);
		ohDayToIndex.put("Friday", 4);
		ohDayToIndex.put("Saturday", 5);
	}

	public StaffMember[][] getOfficeHours() {
		return officeHours;
	}

	public String getTitle() {
		return title;
	}

	public Integer getUnits() {
		return units;
	}

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public Assignment getFinalProject() {
		return finalProject;
	}

	public String getNumber() {
		return number;
	}

	public String getTerm() {
		return term;
	}

	public Integer getYear() {
		return year;
	}

	public List<StaffMember> getStaffMembers() {
		return staffMembers;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public Map<String, List<Meeting>> getSortedMeetings() {
		return sortedMeetings;
	}

	public Map<String, List<StaffMember>> getSortedStaff() {
		return sortedStaff;
	}
	
	public Map<Integer, StaffMember> getMappedStaff() {
		return staffMap;
	}
	
	//get a sorted list of general assignment objects
	//this method does not modify the course object
	public List<GeneralAssignment> getSortedGAs(String type, String sortBy, String direction){
		List<GeneralAssignment> sortedGas = null;
		
		if (type.equals(StringConstants.DELIVERABLES)){
			sortedGas = new ArrayList<>(finalProject.getDeliverables());
		}
		else if (type.equals(StringConstants.ASSIGNMENTS)){
			sortedGas = new ArrayList<>(assignments);
		}
		
		Collections.sort(sortedGas, new GAComparator(sortBy, direction));
		
		return sortedGas;
	}

	/*
	 * Prepares and populates on the data structures that were not populated by Gson
	 * All these data structures will make displaying the course content easier on the front end
	 */
	public void organize() {
		
		if (!organized) {
			sortedMeetings = meetings.stream().collect(Collectors.groupingBy(Meeting::getMeetingType));
			sortedStaff = staffMembers.stream().collect(Collectors.groupingBy(StaffMember::getJobType));
			mappedAssignments = new HashMap<>();
			staffMap = new HashMap<>();
			officeHours = new StaffMember[6][5];

			staffMembers.stream().forEach(staff -> {
				staffMap.put(staff.getID(), staff);
				//populate the 2D OH array
				if (staff.getOH() != null && !staff.getJobType().equals(StringConstants.INSTRUCTOR)) {
					
					for (TimePeriod time : staff.getOH()) {
						//column index based on the start time of this office hour
						Integer col = ohTimeToIndex.get(time.getTime().getStartTime());
						//row index based on the day of this office hour
						Integer row = ohDayToIndex.get(time.getDay());
						//set the object at these indices to the current staff member
						officeHours[row][col] = staff;
					}
				}

			});
			
			//iterate through all the assignments and deliverables and add them to the map
			int fpIndex = -1;
			for (int i = 0; i<assignments.size(); i++) {
				Assignment assignment = assignments.get(i);
				//try to parse the number member variable as an integer -- if we catch an exception, we know it is the final project
				try {
					Integer.parseInt(assignment.getNumber());
					//if we made it this far, this assignment is not the final project, so add it to the map
					addAssignmentToMap(assignment);
				} 
				catch (NumberFormatException nfe) {
					//set this assignment as the final project
					finalProject = assignment;
					fpIndex = i;
					//iterate through all the deliverables and add them to the map
					if (finalProject.getDeliverables() != null) {
						finalProject.getDeliverables().stream().forEach(del -> addAssignmentToMap(del));
					}
				}
			}
			//remove the final project from the list of assignments
			assignments.remove(fpIndex);
			//initially sort both the assignments and fp deliverables in ascending order by their due dates
			Collections.sort(assignments, new GAComparator(StringConstants.DUE, StringConstants.ASCENDING));
			Collections.sort(finalProject.getDeliverables(), new GAComparator(StringConstants.DUE, StringConstants.ASCENDING));

			schedule.organize(mappedAssignments);
			organized = true;
		}
	}
	
	//return a list of staff member ids that correspond to staff with a matching fname, lname or fullname to the query
	public List<Integer> matchStaff(String query){
		List<Integer> ids = new ArrayList<>();
		
		for (StaffMember staff : staffMembers){
			String fname = staff.getName().getFirstName().toLowerCase();
			String lname = staff.getName().getLastName().toLowerCase();
			if (query.toLowerCase().equals(fname) || query.toLowerCase().equals(lname) || query.toLowerCase().equals(fname + " " + lname)){
				ids.add(staff.getID());
			}
		}
		
		return ids;
	}
	
	/*
	 * Given a GeneralAssignment, determine whether it's due date is already a key in the assignments map
	 * If so, add it to the existing list, otherwise create a new list and add it to the map
	 */
	private void addAssignmentToMap(GeneralAssignment assignment){
		String dueDate = assignment.getDueDate();

		if (mappedAssignments.containsKey(dueDate)) {
			mappedAssignments.get(dueDate).add(assignment);
		} 
		else {
			List<GeneralAssignment> list = new ArrayList<>();
			list.add(assignment);
			mappedAssignments.put(dueDate, list);
		}
	}
}
