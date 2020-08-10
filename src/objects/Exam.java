package objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parsing.StringConstants;

public class Exam {
	private String semester;
	public static Map<String, Integer> getTestTitleToIndex() {
		return testTitleToIndex;
	}

	public static void setTestTitleToIndex(Map<String, Integer> testTitleToIndex) {
		Exam.testTitleToIndex = testTitleToIndex;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public void setOrderedTests(Test[] orderedTests) {
		this.orderedTests = orderedTests;
	}

	private String year;
	private List<Test> tests;
	//the tests in order of how they should be display in test.jsp based on their title
	private Test [] orderedTests;
	//static map with test title as the key and index in orderedTests as the value
	private static Map<String, Integer> testTitleToIndex;
	
	//populate our static map
	static{
		testTitleToIndex = new HashMap<>();
		testTitleToIndex.put(StringConstants.WRITTEN_EXAM_1, 0);
		testTitleToIndex.put(StringConstants.PROGRAMMING_EXAM_1, 1);
		testTitleToIndex.put(StringConstants.WRITTEN_EXAM_2, 2);
		testTitleToIndex.put(StringConstants.PROGRAMMING_EXAM_2, 3);
	}

	public String getSemester() {
		return semester;
	}

	public String getYear() {
		return year;
	}

	public List<Test> getTests() {
		return tests;
	}
	
	/*
	 * Returns the tests in the correct order to be displayed on the front end
	 */
	public Test [] getOrderedTests() {
		if (orderedTests == null){
			orderedTests = new Test[4];
			
			tests.stream().forEach(test -> {
				Integer index = testTitleToIndex.get(test.getTitle().toLowerCase());
				
				if (index != null){
					orderedTests[index] = test;
				}
			});
		}
		
		return orderedTests;
	}
}
