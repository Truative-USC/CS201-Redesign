package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.Course;
import objects.GeneralAssignment;
import parsing.Parser;
import parsing.StringConstants;

/**
 * Servlet implementation class LecturesSearchServlet
 */
@WebServlet("/AssignmentsSortServlet")
public class AssignmentsSortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get whether we are sorting deliverables or assignments
		String type = request.getParameter("type");
		//get what we are sorting by (due date, grade percentage, etc.)
		String sortBy = request.getParameter("sortBy");
		//get what direction we are sorting by (ascending or descending)
		String dir = request.getParameter("direction");
		
		Course course = (Course) request.getSession().getAttribute(StringConstants.COURSE);
		//get the sorted objects and convert them into a JSON string
		List<GeneralAssignment> sorted = course.getSortedGAs(type, sortBy, dir);
		String responseText = Parser.gson.toJson(sorted, List.class);
	
		response.getWriter().write(responseText);
		response.getWriter().flush();
	}
}
