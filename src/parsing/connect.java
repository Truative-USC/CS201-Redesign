package parsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
import objects.Program;
import objects.Schedule;
import objects.School;
import objects.StaffMember;
import objects.StaffMemberID;
import objects.Test;
import objects.Textbook;
import objects.TimePeriod;
import objects.Topic;
import objects.Week;

public class connect {
	static Connection con = null;

	public connect(String userName, String password, String ipAdress, String SSL) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://" + ipAdress + "/attarzad_201_site?user=" + userName
					+ "&password=" + password + "&useSSL=" + SSL);

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error connectiong to server");

		}

	}

	public static void writeJavaObject(DataContainer data) throws Exception {

		// set input parameters
		List<School> currSchool = data.getSchools();

		for (int i = 0; i < currSchool.size(); i++) {
			PreparedStatement schools = con.prepareStatement("INSERT INTO schools  (name,image) VALUES(?,?)");

			String schoolName = currSchool.get(i).getName();
			String image = currSchool.get(i).getImage();
			schools.setString(1, schoolName);
			schools.setString(2, image);
			schools.execute();	
			List<Department> currDepts = currSchool.get(i).getDepartments();

			System.out.println("Size for currdepts " + currDepts.size());
			try {
				for (int j = 0; j < currDepts.size(); j++) {
					PreparedStatement depts = con
							.prepareStatement("INSERT INTO departments (longName, prefix,school) VALUES(?,?,?)");

					System.out.println(j + " " + currDepts.get(j).getLongName());

					depts.setString(1, currDepts.get(j).getLongName());
					depts.setString(2, currDepts.get(j).getPrefix());
					depts.setString(3, "USC");

					depts.execute();
					List<Course> currCourses = currDepts.get(j).getCourses();
					for (int l = 0; l < currCourses.size(); l++) {
						Course course = currCourses.get(l);
						PreparedStatement courses = con.prepareStatement(
								"INSERT INTO courses (number, title, units, term, year, department, school, syllabus) VALUES(?,?,?,?,?,?,?,?)");
						courses.setString(1, course.getNumber());
						courses.setString(2, course.getTitle());
						courses.setInt(3, course.getUnits());
						courses.setString(4, course.getTerm());
						courses.setInt(5, course.getYear());
						courses.setString(6, currDepts.get(j).getLongName());
						courses.setString(7, currSchool.get(i).getName());
						courses.setString(8, course.getSyllabus().getUrl());
						courses.execute();
						List<StaffMember> currStaff = currCourses.get(l).getStaffMembers();
						for (int s = 0; s < currStaff.size(); s++) {
							StaffMember staff = currStaff.get(s);
							PreparedStatement staffStmt = con.prepareStatement(
									"INSERT INTO staffMembers (idstaffMembers,type, fname, lname, email, image, phone, office, course) VALUES(?,?,?,?,?,?,?,?,?)");
							staffStmt.setInt(1, staff.getID());
							staffStmt.setString(2, staff.getJobType());
							staffStmt.setString(3, staff.getName().getFirstName());
							staffStmt.setString(4, staff.getName().getLastName());
							staffStmt.setString(5, staff.getEmail());
							staffStmt.setString(6, staff.getImage());
							staffStmt.setString(7, staff.getPhone());
							staffStmt.setString(8, staff.getOffice());
							staffStmt.setString(9, course.getNumber());
							staffStmt.execute();
							if (staff.getOH() != null) {
								List<TimePeriod> officeHours = staff.getOH();
								for (int o = 0; o < officeHours.size(); o++) {
									TimePeriod time = officeHours.get(o);
									PreparedStatement OHStmt = con.prepareStatement(
											"INSERT INTO officeHours(startTime, endTime, day, fname, lname) Values(?,?,?,?,?)");
									OHStmt.setString(1, time.getTime().getStartTime());
									OHStmt.setString(2, time.getTime().getEndTime());
									OHStmt.setString(3, time.getDay());
									OHStmt.setString(4, staff.getName().getFirstName());
									OHStmt.setString(5, staff.getName().getLastName());
									OHStmt.execute();
								}
							}

						}
						List<Meeting> currMeetings = currCourses.get(l).getMeetings();
						for (int m = 0; m < currMeetings.size(); m++) {
							Meeting meeting = currMeetings.get(m);
							PreparedStatement meet = con
									.prepareStatement("INSERT INTO meeting(type, section, room) VALUES(?,?,?)");
							meet.setString(1, meeting.getType());
							meet.setString(2, meeting.getSection());
							meet.setString(3, meeting.getRoom());
							meet.execute();
							List<TimePeriod> currPeriods = meeting.getMeetingPeriods();
							for (int p = 0; p < currPeriods.size(); p++) {
								TimePeriod period = currPeriods.get(p);
								PreparedStatement periodPS = con.prepareStatement(
										"INSERT INTO meetingPeriods(day, startTime, endTime, section) values(?,?,?,?)");
								periodPS.setString(1, period.getDay());
								periodPS.setString(2, period.getTime().getStartTime());
								periodPS.setString(3, period.getTime().getEndTime());
								periodPS.setString(4, meeting.getSection());
								periodPS.execute();

							}
							List<StaffMemberID> currAss = meeting.getAssistants();
							for (int SM = 0; SM < currAss.size(); SM++) {
								PreparedStatement SMStmt = con
										.prepareStatement("INSERT INTO assistants (staffMemberID,section) values(?,?)");
								SMStmt.setInt(1, currAss.get(SM).getID());
								SMStmt.setString(2, meeting.getSection());
								SMStmt.execute();
							}
						}
						List<Textbook> currTextBooks = currCourses.get(l).getSchedule().getTextbooks();
						for (int t = 0; t < currTextBooks.size(); t++) {
							PreparedStatement schedule = con.prepareStatement(
									"INSERT INTO textBook(number, author, title, publisher, year, isbn, courseNumber) VALUES(?,?,?,?,?,?,?)");

							schedule.setInt(1, currTextBooks.get(t).getNumber());
							schedule.setString(2, currTextBooks.get(t).getAuthor());
							schedule.setString(3, currTextBooks.get(t).getTitle());
							schedule.setString(4, currTextBooks.get(t).getPublisher());
							schedule.setString(5, currTextBooks.get(t).getYear());
							schedule.setString(6, currTextBooks.get(t).getIsbn());
							schedule.setString(7, course.getNumber());
							schedule.execute();
						}

						List<Week> currWeeks = currCourses.get(l).getSchedule().getWeeks();

						for (int w = 0; w < currWeeks.size(); w++) {
							PreparedStatement week = con
									.prepareStatement("insert into week(idweek, section) values (?,?)");
							week.setInt(1, currWeeks.get(w).getWeek());
							week.setString(2, course.getNumber());
							week.execute();
							List<Lab> currLabs = currWeeks.get(w).getLabs();
							for (int L = 0; L < currLabs.size(); L++) {
								Lab lab = currLabs.get(L);
								PreparedStatement labStatement = con.prepareStatement(
										"insert into labs (week, number, title, url) Values(?,?,?,?)");
								labStatement.setInt(1, currWeeks.get(w).getWeek());
								labStatement.setInt(2, lab.getNumber());
								labStatement.setString(3, lab.getTitle());
								labStatement.setString(4, lab.getUrl());
								labStatement.execute();
								if(lab.getFiles()!=null) {
									List<File> labFiles = currLabs.get(i).getFiles();
									for(File file: labFiles) {
										PreparedStatement labStmt = con.prepareStatement("insert into files (week, number, title, url, type) values(?,?,?,?,?)");
										labStmt.setInt(1, currWeeks.get(w).getWeek());
										labStmt.setInt(2, file.getNumber());
										labStmt.setString(3, file.getTitle());
										labStmt.setString(4, file.getUrl());
										labStmt.setString(5, "lab");
										labStmt.execute();
									}
								}
							}

							List<Lecture> currLecs = currWeeks.get(w).getLectures();
							for (Lecture lec : currLecs) {
								PreparedStatement lecStatement = con.prepareStatement(
										"insert into lectures(number, date, day, week, section) values(?,?,?,?,?)");
								lecStatement.setInt(1, lec.getNumber());
								lecStatement.setString(2, lec.getDate());
								lecStatement.setString(3, lec.getDay());
								lecStatement.setInt(4, currWeeks.get(w).getWeek());
								lecStatement.setString(5, course.getNumber());
								lecStatement.execute();
								if (lec.getTopics() != null) {
									for (Topic topic : lec.getTopics()) {
										PreparedStatement topicStmt = con.prepareStatement(
												"insert into topics(number, title, url, chapter, week, section, lecNumber) values(?,?,?,?,?,?,?)");
										topicStmt.setInt(1, topic.getNumber());

										topicStmt.setString(2, topic.getTitle());
										topicStmt.setString(3, topic.getUrl());
										topicStmt.setString(4, topic.getChapter());

										topicStmt.setInt(5, currWeeks.get(w).getWeek());
										topicStmt.setString(6, course.getNumber());
										topicStmt.setInt(7, lec.getNumber());
										topicStmt.execute();
										if (topic.getPrograms() != null) {
											for (File file : topic.getPrograms().get(0).getFiles()) {
												PreparedStatement programStmt = con.prepareStatement(
														"insert into files(number, title, url, week, section, type,topicNumber) values(?,?,?,?,?,?,?)");
												programStmt.setInt(1, file.getNumber());
												programStmt.setString(2, file.getTitle());
												programStmt.setString(3, file.getUrl());
												programStmt.setInt(4, currWeeks.get(w).getWeek());
												programStmt.setString(5, course.getNumber());
												programStmt.setString(6, "lecture");
												programStmt.setInt(7, topic.getNumber());
												programStmt.execute();
											}
										}
									}
								}

							}
						}
						List<Assignment> currAssignments = currCourses.get(l).getAssignments();
						System.out.println("assignments size " + currAssignments.size());
						for (Assignment currAssignment : currAssignments) {
						
								System.out.println("i go into assignments");
								PreparedStatement assStmt = con.prepareStatement(
										"Insert into assignments(number, dueDate, title, url,gradePercentage,AssignedDate) values(?,?,?,?,?,?)");
								assStmt.setString(1, currAssignment.getNumber());
								assStmt.setString(2, currAssignment.getDueDate());
								assStmt.setString(3, currAssignment.getTitle());
								assStmt.setString(4, currAssignment.getUrl());
								assStmt.setString(5, currAssignment.getGradePercentage());
								assStmt.setString(6, currAssignment.getAssignedDate());
								assStmt.execute();
								if(currAssignment.getFiles() != null) {
									List<File> files = currAssignment.getFiles();
									for (File file : files) {
										PreparedStatement fileStmt = con.prepareStatement(
												"INSERT INTO FILES(number, title, url, week,section, type) values(?,?,?,?,?,?)");
										fileStmt.setInt(1, file.getNumber());
										fileStmt.setString(2, file.getTitle());
										fileStmt.setString(3, file.getUrl());
										fileStmt.setString(4, currAssignment.getNumber());
										fileStmt.setString(5, course.getNumber());
										fileStmt.setString(6, "assignmentfile");
										fileStmt.execute();
									}
								}
								if(currAssignment.getGradingCriteriaFiles() != null ) {
									List<File> gradingCriteria = currAssignment.getGradingCriteriaFiles();

									for (File GCfile : gradingCriteria) {
										PreparedStatement gCStmt = con.prepareStatement(
												"INSERT INTO FILES(number, title, url, week,section, type) values(?,?,?,?,?,?)");
										gCStmt.setInt(1, GCfile.getNumber());
										gCStmt.setString(2, GCfile.getTitle());
										gCStmt.setString(3, GCfile.getUrl());
										gCStmt.setString(4, currAssignment.getNumber());
										gCStmt.setString(5, course.getNumber());
										gCStmt.setString(6, "gradingCriteriaFiles");
										gCStmt.execute();
									}
								}
 							
								if(currAssignment.getSolutionFiles() != null) {
									List<File> solutionFiles = currAssignment.getSolutionFiles();
									for (File sFile : solutionFiles) {
										PreparedStatement solStmt = con.prepareStatement(
												"INSERT INTO FILES(number, title, url, week,section, type) values(?,?,?,?,?,?)");
										solStmt.setInt(1, sFile.getNumber());
										solStmt.setString(2, sFile.getTitle());
										solStmt.setString(3, sFile.getUrl());
										solStmt.setString(4, currAssignment.getNumber());
										solStmt.setString(5, course.getNumber());
										solStmt.setString(6, "solutionFiles");
										solStmt.execute();
									}
								}
							
						}
						Assignment finalAssignment = course.getFinalProject();
						PreparedStatement finalStmt = con.prepareStatement(
									"Insert into assignments(number, dueDate, title, url,gradePercentage,AssignedDate) values(?,?,?,?,?,?)");
							finalStmt.setString(1, finalAssignment.getNumber());
							finalStmt.setString(2, finalAssignment.getDueDate());
							finalStmt.setString(3, finalAssignment.getTitle());
							finalStmt.setString(4, finalAssignment.getUrl());
							finalStmt.setString(5, finalAssignment.getGradePercentage());
							finalStmt.setString(6, finalAssignment.getAssignedDate());
							finalStmt.execute();
							List<File> currFiles = finalAssignment.getFiles();
							for(File file : currFiles) {
								PreparedStatement fileStmt =  con.prepareStatement("Insert into files(number, title, url, type) values(?,?,?,?)");
								fileStmt.setInt(1, file.getNumber());
								fileStmt.setString(2, file.getTitle());
								fileStmt.setString(3, file.getUrl());
								fileStmt.setString(4, "finalFiles");
								fileStmt.execute();
								
							}
							List<Deliverable> currdivs = finalAssignment.getDeliverables();
							for(Deliverable div : currdivs) {
								PreparedStatement divStmt = con.prepareStatement("Insert into deliverables(number, dueDate, title, gradePercentage) values(?,?,?,?)");
								divStmt.setString(1, div.getNumber());
								divStmt.setString(2, div.getDueDate());
								divStmt.setString(3, div.getTitle());
								divStmt.setString(4, div.getGradePercentage());
								divStmt.execute();
								if(div.getFiles()!=null) {
									List<File> divFiles = div.getFiles();
									for(File file : divFiles) {
										PreparedStatement fileStmt = con.prepareStatement(

									"INSERT INTO files(number, title, url, week,section, type, topicNumber) values(?,?,?,?,?,?,?)");
									fileStmt.setInt(1, file.getNumber());
									fileStmt.setString(2, file.getTitle());
									fileStmt.setString(3, file.getUrl());
									fileStmt.setString(4, finalAssignment.getNumber());
									fileStmt.setString(5, course.getNumber());
									fileStmt.setString(6, "deliverableFiles");
									int num = Integer.parseInt(div.getNumber());
									fileStmt.setInt(7, num);
									fileStmt.execute();										
									}
								}
							}

						
							List<Exam> currExams = currCourses.get(l).getExams();
							for(Exam exam : currExams) {
								PreparedStatement examStmt = con.prepareStatement("insert into exams(semester, year) values(?,?)");
								examStmt.setString(1, exam.getSemester());
								examStmt.setString(2, exam.getYear());
								examStmt.execute();
								List<Test> tests =  exam.getTests();
								for(Test test: tests) {
									PreparedStatement testStmt = con.prepareStatement("insert into tests(semester, year, title) values(?,?,?)");
									testStmt.setString(1, exam.getSemester());
									testStmt.setString(2, exam.getYear());
									testStmt.setString(3, test.getTitle());
									testStmt.execute();
									List<File> files = test.getFiles();
									for(File file : files) {
										PreparedStatement fileStmt = con.prepareStatement("insert into examFiles(title, semester, year, number, examTitle, url) values(?,?,?,?,?,?)");
										fileStmt.setString(1, test.getTitle());
										fileStmt.setString(2, exam.getSemester());
										fileStmt.setString(3, exam.getYear());
										fileStmt.setInt(4, file.getNumber());
										fileStmt.setString(5, file.getTitle());
										fileStmt.setString(6, file.getUrl());
										fileStmt.execute();
									}
								}
							}
							
							
							
						
						
//						for(Exam exam : currExams) {
//							PreparedStatement examStmt = con.prepareStatement("insert into exams(semester, year) values(?,?)");
//							examStmt.setString(1, exam.getSemester());
//							examStmt.setString(2, exam.getYear());
//							examStmt.execute();
//							List<Test> tests = exam.getTests();
//							for(Test test :tests) {
//								List<File> examFiles = test.getFiles();
//								for(File file: examFiles) {
//									PreparedStatement fileStmt  = con.prepareStatement("insert into examFiles(title, semester, year, number, examTitle, url) values(?,?,?,?,?,?)");
//									fileStmt.setString(1, test.getTitle());
//									fileStmt.setString(2, exam.getSemester());
//									fileStmt.setString(3, exam.getYear());
//									fileStmt.setInt(4, file.getNumber());
//									fileStmt.setString(5, file.getTitle());
//									fileStmt.setString(6, file.getUrl());
//									fileStmt.execute();
//								}
//							}
//						}

					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

	}

	public static Connection getConnection() {
		return con;
	}

	public ResultSet execute(String statement) {
		java.sql.Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			st = con.createStatement();
			rs = st.executeQuery(statement);

		} catch (Exception e) {
			System.out.println("Error in Connector");
		}
		return null;
	}

}