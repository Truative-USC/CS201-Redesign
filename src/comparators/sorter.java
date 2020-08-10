package comparators;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import objects.Assignment;
import objects.Deliverable;
import objects.GeneralAssignment;
import parsing.connect;

public class sorter {
	private String type;
	private String sortBy;
	private String direction;
	public connect con = null;
	 
public sorter(String sortType,String sortBy, String sortDirection) {
	this.type = sortType;
	this.direction = sortDirection;
	
}


public List<GeneralAssignment> sortData() throws SQLException {
List<GeneralAssignment> assignment = new ArrayList<GeneralAssignment>();
	
if(type.equals("assignment")) {
	try {
	ResultSet rs = 	con.getConnection().createStatement().executeQuery("select * from assignments where number <> 'Final Project' ORDER BY " + sortBy + " " + direction);
	while(rs.next()) {
		Assignment temp = new Assignment();
		temp.setAssignedDate(rs.getString(7));
		temp.setTitle(rs.getString(4));
		temp.setDueDate(rs.getString(3));
		temp.setNumber(rs.getString(2));
		temp.setGradePercentage(rs.getString(6));
		temp.setUrl(rs.getString(5));
		assignment.add(temp);
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
else if(type.equals("deliverable")) {
	ResultSet rs = 	con.getConnection().createStatement().executeQuery("select * from deliverable  ORDER BY " + sortBy + " " + direction);
try {
	while(rs.next()) {
		Deliverable div = new Deliverable();
		div.setDueDate(rs.getString(2));
		div.setGradePercentage(rs.getString(4));
		div.setTitle(rs.getString(3));
		div.setNumber(rs.getString(1));
		assignment.add(div);
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

return assignment;
}
}
