package parsing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import objects.Assignment;
import objects.Course;
import objects.DataContainer;
import objects.Deliverable;
import objects.Department;
import objects.Exam;
import objects.File;
import objects.Lab;
import objects.Lecture;
import objects.Meeting;
import objects.Name;
import objects.Program;
import objects.Schedule;
import objects.School;
import objects.StaffMember;
import objects.StaffMemberID;
import objects.Syllabus;
import objects.Test;
import objects.Textbook;
import objects.Time;
import objects.TimePeriod;
import objects.Topic;
import objects.Week;

public class fillData {

	
	
	
	public DataContainer fillObjects() {
		System.out.println("welcome to query");
		DataContainer data = new DataContainer();
		Connection con = (Connection) connect.getConnection();
		Course course = new Course();
		try {
			String prefix = "";
			java.sql.Statement st1 = con.createStatement();
			ResultSet rs1 = st1.executeQuery("Select * from departments");
			while(rs1.next()) {
				prefix = rs1.getString(3); 
			}
			java.sql.Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM courses");
			
			while (rs.next()) {

				course.setNumber(rs.getString(2));
				course.setTitle(rs.getString(3));
				course.setUnits(rs.getInt(4));
				course.setTerm(rs.getString(5));
				course.setYear(rs.getInt(6));
				Syllabus syllabus = new Syllabus();
				syllabus.setUrl(rs.getString(9));
				course.setSyllabus(syllabus);
				Department d = new Department();
				d.setPrefix(prefix);
				d.setLongName(rs.getString(7));
				School school = new School();
				java.sql.Statement st2 = con.createStatement();
				ResultSet rs2 = st2.executeQuery("SELECT * FROM schools");
				while (rs2.next()) {
					school.setName(rs2.getString(2));
					school.setImage(rs2.getString(3));
				}
				List<School> schools = new ArrayList<School>();
				
				
				List<StaffMember> currStaff = new ArrayList<StaffMember>();
				java.sql.Statement getStaff = con.createStatement();
				ResultSet staffSet = getStaff.executeQuery("SELECT * FROM staffMembers");
				while (staffSet.next()) {


					StaffMember temp = new StaffMember();
					temp.setType(staffSet.getString(2));
					Name name = new Name();
					name.setFname(staffSet.getString(3));
					name.setLname(staffSet.getString(4));
					temp.setName(name);
					temp.setEmail(staffSet.getString(5));
					temp.setId(staffSet.getInt(1));
					if (temp.getType().equals("instructor")) {
						temp.setOffice(staffSet.getString(8));
						temp.setPhone(staffSet.getString(7));
					}
					temp.setImage(staffSet.getString(6));
					java.sql.Statement ohStmt = con.createStatement();
					ResultSet ohSet = ohStmt.executeQuery("SELECT * FROM officeHours where fname = '"
							+ temp.getName().getFirstName() + "' and lname = '" + temp.getName().getLastName() + "'");
					List<TimePeriod> oh = new ArrayList<TimePeriod>();

					while (ohSet.next()) {
						TimePeriod tempOh = new TimePeriod();
						Time tempTime = new Time();
						tempTime.setStart(ohSet.getString(2));
						tempTime.setEnd(ohSet.getString(3));
						tempOh.setTime(tempTime);
						tempOh.setDay(ohSet.getString(4));
						oh.add(tempOh);
					}
					temp.setOfficeHours(oh);
					currStaff.add(temp);
					
				}
				List<Meeting> currMeetings = new ArrayList<Meeting>();
				java.sql.Statement getMeeting = con.createStatement();
				ResultSet meetingSet = getMeeting.executeQuery("Select * FROM meeting");
				while(meetingSet.next()) {
					Meeting tempMeeting = new Meeting();
					tempMeeting.setRoom(meetingSet.getString(4));
					tempMeeting.setType(meetingSet.getString(2));
					tempMeeting.setSection(meetingSet.getString(3));
					java.sql.Statement period = con.createStatement();
					ResultSet periodSet = period.executeQuery("Select * from meetingPeriods where section='" + tempMeeting.getSection() + "'");
					List<TimePeriod> tempPeriod = new ArrayList<TimePeriod>();

					while(periodSet.next()) {
						TimePeriod temp = new TimePeriod();
						Time tempTime = new Time();
						temp.setDay(periodSet.getString(2));
						tempTime.setStart(periodSet.getString(3));
						tempTime.setEnd(periodSet.getString(4));
						temp.setTime(tempTime);
						tempPeriod.add(temp);
					}
					if(tempMeeting.getType() != "quiz") {
						java.sql.Statement assStmt = con.createStatement();
						ResultSet assistants = assStmt.executeQuery("SELECT * FROM assistants where section = '" + tempMeeting.getSection() + "'");
						List<StaffMemberID> ids = new ArrayList<StaffMemberID>();
						while(assistants.next()) {
							StaffMemberID temp = new StaffMemberID();
							temp.setStaffMemberID(assistants.getInt(2));
							ids.add(temp);
						}
						tempMeeting.setAssistants(ids);

					}
					tempMeeting.setMeetingPeriods(tempPeriod);
					currMeetings.add(tempMeeting);
				}
				course.setMeetings(currMeetings);
				course.setStaffMembers(currStaff);
				java.sql.Statement textBookStmt = con.createStatement();
				ResultSet tbSet = textBookStmt.executeQuery("select * from textBook");
				Schedule schedule = new Schedule();
				List<Textbook> textBookList = new ArrayList<Textbook>();
				while(tbSet.next()) {

					Textbook temp = new Textbook();
					temp.setAuthor(tbSet.getString(2));
					temp.setTitle(tbSet.getString(3));
					temp.setPublisher(tbSet.getString(4));
					temp.setYear(tbSet.getString(5));
					temp.setIsbn(tbSet.getString(6));
					textBookList.add(temp);
				}
				schedule.setTextbooks(textBookList);
				java.sql.Statement weekStmt = con.createStatement();
				ResultSet weekSet = weekStmt.executeQuery("Select * from week");
				List<Week> weekList = new ArrayList<Week>();
				while(weekSet.next()) {

					Week week = new Week();
					week.setWeek(weekSet.getInt(1));
					java.sql.Statement labStmt = con.createStatement();
					ResultSet labSet = labStmt.executeQuery("select * from labs where week = " + week.getWeek());
					List<Lab> labList = new ArrayList<Lab>();
					while(labSet.next()) {

						Lab tempLab = new Lab();
						tempLab.setNumber(labSet.getInt(3));
						tempLab.setTitle(labSet.getString(4));
						tempLab.setUrl(labSet.getString(5));
						labList.add(tempLab);
						java.sql.Statement labFileStmt = con.createStatement();

						ResultSet labFilesSet = labFileStmt.executeQuery("select * from files where type ='lab' and week = " +week.getWeek());

						if(labFilesSet != null) {
							List<File> labFiles = new ArrayList<File>();
							while(labFilesSet.next()) {
								File file = new File();
								file.setNumber(labFilesSet.getInt(2));
								file.setTitle(labFilesSet.getString(3));
								file.setUrl(labFilesSet.getString(4));
								labFiles.add(file);
							}
							tempLab.setFiles(labFiles);
						}
					}
					java.sql.Statement lecStmt = con.createStatement();
					ResultSet lecSet = lecStmt.executeQuery("select * from lectures where week = " + week.getWeek());
					List<Lecture> lecList = new ArrayList<Lecture>();
					while(lecSet.next()) {
						Lecture tempLec = new Lecture();
						tempLec.setNumber(lecSet.getInt(2));
						tempLec.setDate(lecSet.getString(3));
						tempLec.setDay(lecSet.getString(4));
						java.sql.Statement topicStmt = con.createStatement();
						ResultSet topicSet = topicStmt.executeQuery("select * from topics where week = " + week.getWeek() +" and lecNumber = " + tempLec.getNumber());
						List<Topic> topicList = new ArrayList<Topic>();
						while(topicSet.next()) {
							Topic tempTopic = new Topic();
							tempTopic.setNumber(topicSet.getInt(2));
							if(topicSet.getString(5) != null) {
								tempTopic.setChapter(topicSet.getString(5));
							}
							
							tempTopic.setUrl(topicSet.getString(4));
							tempTopic.setTitle(topicSet.getString(3));
							java.sql.Statement fileStmt = con.createStatement();
							ResultSet fileSet = fileStmt.executeQuery("select * from files where week = " + week.getWeek() + " and topicNumber=" + tempTopic.getNumber());
							if(fileSet != null) {
								List<Program> currPrograms = new ArrayList<Program>();
								List<File> proFiles = new ArrayList<File>();
								while(fileSet.next()) {
									File file = new File();
									file.setNumber(fileSet.getInt(2));
									file.setTitle(fileSet.getString(3));
									file.setUrl(fileSet.getString(4));
									proFiles.add(file);
								}
								Program temp = new Program();
								temp.setFiles(proFiles);
								currPrograms.add(temp);
								tempTopic.setPrograms(currPrograms);

							}
							topicList.add(tempTopic);
						}
						tempLec.setTopics(topicList);
						lecList.add(tempLec);
					}
					week.setLectures(lecList);
					week.setLabs(labList);
					weekList.add(week);
					
				}
				List<Assignment> currAssignments = new ArrayList<Assignment>();
				
				
				java.sql.Statement assStmt = con.createStatement();
				ResultSet assSet = assStmt.executeQuery("select * from assignments");
				while(assSet.next()) {
					if(assSet.getString(2).equals("Final Project")) {
						Assignment finalProject = new Assignment();
						finalProject.setAssignedDate(assSet.getString(6));
						finalProject.setNumber("Final Project");
						finalProject.setTitle(assSet.getString(4));
						finalProject.setUrl(assSet.getString(5));
						List<File> files = new ArrayList<File>();
						java.sql.Statement fileStmt = con.createStatement();
						ResultSet fileSet = fileStmt.executeQuery("Select * from files where type = 'finalFiles'");
						while(fileSet.next()) {
							File file = new File();
							file.setNumber(fileSet.getInt(2));
							file.setTitle(fileSet.getString(3));
							file.setUrl(fileSet.getString(4));
							files.add(file);
						}
						finalProject.setFiles(files);
						List<Deliverable> divs = new ArrayList<Deliverable>();
						java.sql.Statement divsStmt = con.createStatement();
						ResultSet divSet = divsStmt.executeQuery("Select * from deliverables");
						while(divSet.next()) {
							Deliverable tempDiv = new Deliverable();
							String num = Integer.toString(divSet.getInt(1));
							tempDiv.setNumber(num);
							tempDiv.setGradePercentage(divSet.getString(4));
							tempDiv.setTitle(divSet.getString(3));
							tempDiv.setDueDate(divSet.getString(2));
							java.sql.Statement divFile = con.createStatement();
							ResultSet divFileSet = divFile.executeQuery("select * from files where type = 'deliverableFiles' and topicNumber = "+divSet.getInt(1));
							if(divFileSet != null) {
								List<File> divFiles = new ArrayList<File>();
								while(divFileSet.next()) {
									File file = new File();
									file.setNumber(divFileSet.getInt(2));
									file.setTitle(divFileSet.getString(3));
									file.setUrl(divFileSet.getString(4));
									divFiles.add(file);
									
								}
								tempDiv.setFiles(divFiles);
							}
							divs.add(tempDiv);
						}
						finalProject.setDeliverables(divs);
						currAssignments.add(finalProject);
						
					}
					else {
						Assignment assignment = new Assignment();
						assignment.setDueDate(assSet.getString(3));
						assignment.setNumber(assSet.getString(2));
						assignment.setAssignedDate(assSet.getString(7));
						assignment.setGradePercentage(assSet.getString(6));
						if(assSet.getString(4) != null) {
							assignment.setTitle(assSet.getString(4));

						}
						if(assSet.getString(5) != null) {
							assignment.setUrl(assSet.getString(5));
						}
						java.sql.Statement fileStmt = con.createStatement();
						ResultSet fileSet = fileStmt.executeQuery("select * from files where type = 'assignmentfile' and week =" + assignment.getNumber());
						if(fileSet != null) {
							List<File> fileList = new ArrayList<File>();

							while(fileSet.next()) {
								File file = new File();
								
								file.setNumber(fileSet.getInt(2));
								file.setTitle(fileSet.getString(3));
								file.setUrl(fileSet.getString(4));
								fileList.add(file);
							}
							assignment.setFiles(fileList);
						}
						java.sql.Statement gCStmt = con.createStatement();
						ResultSet gCSet = gCStmt.executeQuery("select * from files where type = 'gradingCriteriaFiles' and week = " + assignment.getNumber());
						if(gCSet!=null) {
							List<File> gcfiles = new ArrayList<File>();
							while(gCSet.next()) {
								File file = new File();
								
								file.setNumber(gCSet.getInt(2));
								file.setTitle(gCSet.getString(3));
								file.setUrl(gCSet.getString(4));
								gcfiles.add(file);
							}
							assignment.setGradingCriteriaFiles(gcfiles);
						}
						java.sql.Statement solStmt = con.createStatement();
						ResultSet solSet = solStmt.executeQuery("select * from files where type = 'solutionFiles' and week = " + assignment.getNumber());
						if(solSet!=null) {
							List<File> Solfiles = new ArrayList<File>();
							while(solSet.next()) {
								File file = new File();
								file.setNumber(solSet.getInt(2));
								file.setTitle(solSet.getString(3));
								file.setUrl(solSet.getString(4));
								Solfiles.add(file);
							}
							assignment.setSolutionFiles(Solfiles);
						}
						currAssignments.add(assignment);
					}
					
				}
				course.setAssignments(currAssignments);
				
				List<Exam> exams = new ArrayList<Exam>();
				java.sql.Statement examStmt = con.createStatement();
				ResultSet examSet = examStmt.executeQuery("select * from exams");
				while(examSet.next()) {
					Exam exam = new Exam();
					List<Test> tests = new ArrayList<Test>();
					exam.setYear(examSet.getString(3));
					exam.setSemester(examSet.getString(2));
					java.sql.Statement testStmt = con.createStatement();
					ResultSet testSet = testStmt.executeQuery("select * from tests where semester = '" + exam.getSemester() + "' and year = '" + exam.getYear() + "'");
					while(testSet.next()) {
						Test test = new Test();
						test.setTitle(testSet.getString(4));
						java.sql.Statement fileStatement = con.createStatement();
						ResultSet fileSet = fileStatement.executeQuery("select * from examFiles where semester = '" + exam.getSemester() + "' and year = '" + exam.getYear() + "' and title = '" + test.getTitle() + "'");
						List<File> files = new ArrayList<File>();
						while(fileSet.next()) {
							File file = new File();
							file.setTitle(fileSet.getString(6));
							file.setUrl(fileSet.getString(7));
							file.setNumber(fileSet.getInt(5));
							files.add(file);
							
						}
						test.setFiles(files);
						tests.add(test);
					}
					
					exam.setTests(tests);
					exams.add(exam);
				}

				course.setExams(exams);
				schedule.setWeeks(weekList);
				course.setSchedule(schedule);
				List<Course> currCourse = new ArrayList<Course>();
				currCourse.add(course);
				d.setCourses(currCourse);
				school.addDepartment(d);
				schools.add(school);
				data.setSchools(schools);
				
			}
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	return data;	
	}
}
