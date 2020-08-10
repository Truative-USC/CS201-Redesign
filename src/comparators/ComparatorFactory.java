package comparators;

import java.util.HashMap;
import java.util.Map;

import objects.GeneralAssignment;
import parsing.StringConstants;

public class ComparatorFactory {
	//static map from string to SortMethod
	//the key will be the combination of the sort direction and what we are sorting by
	private static final Map<String, SortMethod> factoryMap;
	//the order in which to compare the elements of a split date (mm-dd-yyyy)
	private static Integer [] order = new Integer[]{2, 0, 1};
	
	//populate the map
	static{
		factoryMap = new HashMap<>();
		factoryMap.put(StringConstants.ASCENDING+","+StringConstants.TITLE, new AscStringSort());
		factoryMap.put(StringConstants.DESCENDING+","+StringConstants.TITLE, new DescStringSort());
		factoryMap.put(StringConstants.ASCENDING+","+StringConstants.DUE, new AscDueSort());
		factoryMap.put(StringConstants.DESCENDING+","+StringConstants.DUE, new DescDueSort());
		factoryMap.put(StringConstants.ASCENDING+","+StringConstants.GRADE, new AscGradeSort());
		factoryMap.put(StringConstants.DESCENDING+","+StringConstants.GRADE, new DescGradeSort());
		factoryMap.put(StringConstants.ASCENDING+","+StringConstants.ASSIGNED, new AscAssignedSort());
		factoryMap.put(StringConstants.DESCENDING+","+StringConstants.ASSIGNED, new DescAssignedSort());
	}
	
	//given what to sort by, the direction, and the two objects, get the appropriate SortMethod and call the sort method
	public int compare(String sortBy, String direction, GeneralAssignment one, GeneralAssignment two){
		return factoryMap.get(direction+","+sortBy).sort(one, two);
	}
	
	//abstract class that takes two GeneralAssignment objects and returns an int value of their comparison
	abstract static class SortMethod{
		abstract public int sort(GeneralAssignment one, GeneralAssignment two);
	}
	
	//return object 1 compared against object 2 based on their assigned date values
	static class AscAssignedSort extends SortMethod{
		@Override
		public int sort(GeneralAssignment one, GeneralAssignment two) {
			return sortDate(one.getAssignedDate(), two.getAssignedDate());
		}
	}
	
	//return object 2 compared against object 1 based on their assigned date values
	static class DescAssignedSort extends SortMethod{
		@Override
		public int sort(GeneralAssignment one, GeneralAssignment two) {
			return sortDate(two.getAssignedDate(), one.getAssignedDate());
		}
	}
	
	//return object 1 compared against object 2 based on their due date values
	static class AscDueSort extends SortMethod{
		@Override
		public int sort(GeneralAssignment one, GeneralAssignment two) {
			return sortDate(one.getDueDate(), two.getDueDate());
		}
	}
	
	//return object 2 compared against object 1 based on their due date values
	static class DescDueSort extends SortMethod{
		@Override
		public int sort(GeneralAssignment one, GeneralAssignment two) {
			return sortDate(two.getDueDate(), one.getDueDate());
		}
	}
	
	//return object 1 compared against object 2 based on their grade percentage values
	static class AscGradeSort extends SortMethod{
		@Override
		public int sort(GeneralAssignment one, GeneralAssignment two) {
			return sortGrade(one.getGradePercentage(), two.getGradePercentage());
		}
	}
	
	//return object 2 compared against object 1 based on their grade percentage values
	static class DescGradeSort extends SortMethod{
		@Override
		public int sort(GeneralAssignment one, GeneralAssignment two) {
			return sortGrade(two.getGradePercentage(), one.getGradePercentage());
		}
	}
	
	//return object 1 compared against object 2 based on their title values
	static class AscStringSort extends SortMethod{
		@Override
		public int sort(GeneralAssignment one, GeneralAssignment two) {
			return sortString(one.getTitle(), two.getTitle());
		}
	}
	
	//return object 2 compared against object 1 based on their title values
	static class DescStringSort extends SortMethod{
		@Override
		public int sort(GeneralAssignment one, GeneralAssignment two) {
			return sortString(two.getTitle(), one.getTitle());
		}
	}
	
	//method that compares dates
	private static int sortDate(String d1, String d2){
		//split the dates into [mm, dd, yyyy]
		String [] splitD1 = d1.split("-");
		String [] splitD2 = d2.split("-");
		//iterate over the arrays in order of which element comparisons take precendence
		for (Integer i : order){
			Integer one = Integer.parseInt(splitD1[i]);
			Integer two = Integer.parseInt(splitD2[i]);
			//if the current elements are not equal, return their comparison value
			if (!one.equals(two)){
				return one.compareTo(two);
			}
		}
		
		return 0;
	}
	
	//method that compares grade percentages
	private static int sortGrade(String g1, String g2){
		String grade1 = g1.split("%")[0];
		String grade2 = g2.split("%")[0];
		Double dg1 = Double.parseDouble(grade1);
		Double dg2 = Double.parseDouble(grade2);
		return dg1.compareTo(dg2);
	}
	
	//method that compares strings
	private static int sortString(String s1, String s2){
		
		if (s1 == null){
			return Integer.MAX_VALUE;
		}
		else if (s2 == null){
			return Integer.MIN_VALUE;
		}
		else{
			return s1.toLowerCase().compareTo(s2.toLowerCase());
		}
	}
}
