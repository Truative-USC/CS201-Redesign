package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comparators.sorter;
import objects.GeneralAssignment;

/**
 * Servlet implementation class sqlSort
 */
@WebServlet("/sqlSort")
public class sqlSort extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public sqlSort() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String sortBy = request.getParameter("sortBy");
		String direction = request.getParameter("direction");
		sorter sort = new sorter(type, sortBy, direction);
		
		try {
			List<GeneralAssignment> assignments = sort.sortData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}



}
