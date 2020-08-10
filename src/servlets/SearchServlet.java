package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import objects.Course;
import objects.DataContainer;
import parsing.StringConstants;

/**
 * Servlet implementation class SearchStaffServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Gson gson = new Gson();
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the searched string
		String query = request.getParameter("query");
		//get the type of search (either for staff members or for topics)
		String type = request.getParameter("type");
		
		Course course = (Course) request.getSession().getAttribute(StringConstants.COURSE);
		
		if ((query != null) && !query.equals("")){
			String responseText = null;
			
			if (type.equals(StringConstants.HOME)){
				//get a list of staff IDs that match the query
				responseText = gson.toJson(course.matchStaff(query), List.class);
			}
			else if (type.equals(StringConstants.LECTURE)){
				//get a list of topic identifiers that match the query
				responseText = gson.toJson(course.getSchedule().matchTopics(query), List.class);
			}
			
			response.getWriter().write(responseText);
			response.getWriter().flush();	
		}	
	}
}
