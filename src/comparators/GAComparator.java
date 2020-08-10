package comparators;

import java.util.Comparator;

import objects.GeneralAssignment;

//used to compare assignments and deliverables in order to sort them
public class GAComparator implements Comparator<GeneralAssignment>{
	//either ascending or descending
	private String direction;
	//either by title, due date, grade percentage, or assigned date
	private String sortBy;
	//factory that helps us sort based on the direction and sortBy variables
	private static ComparatorFactory factory = new ComparatorFactory();
	
	public GAComparator(String sortBy, String direction){
		this.direction = direction;
		this.sortBy = sortBy;
	}
	
	@Override
	public int compare(GeneralAssignment o1, GeneralAssignment o2) {
		//have the factory determine which method to sort by
		return factory.compare(sortBy, direction, o1, o2);
	}
}
