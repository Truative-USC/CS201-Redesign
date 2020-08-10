package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import parsing.Parser;
import parsing.StringConstants;

/**
 * Servlet implementation class LecturesFilterServlet
 */
@WebServlet("/LecturesFilterServlet")
public class LecturesFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//contains the appropriate response object for 'Show All'
	private static Map<String, Object> responseObj = new HashMap<>();
	private static Object [] displayLectures;
	private static final String disableSearchKey = "disableSearch";
	private static final String displayLecturesKey = "displayLectures";
	private static final String displayDueDatesKey = "displayDueDates";
	
	static{
		//populate the array and map with the values for the 'Show All' selection
		displayLectures = new Object[]{"table-cell", "1", false};
		responseObj.put(disableSearchKey, false);
		responseObj.put(displayLecturesKey, displayLectures);
		responseObj.put(displayDueDatesKey, "table-row");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the filter choice
		String choice = request.getParameter("choice");
		//make a local copy of the static response object
		Map<String, Object> currentResponseObj = new HashMap<>(responseObj);

		switch(choice){
			//if we are only showing due dates
			case StringConstants.SHOW_DUE:
				//we want the search buttons and input to be disabled
				currentResponseObj.put(disableSearchKey, true);
				//we want to change the parameters for display lectures so they will not be displayed
				currentResponseObj.put(displayLecturesKey, new Object[]{"none", "3", displayLectures[2]});
				break;
			case StringConstants.SHOW_LECTURES:
				//we want to change the parameters for display lectures so they will be displayed
				currentResponseObj.put(displayLecturesKey, new Object[]{displayLectures[0], displayLectures[1], true});
				//we want to hide due dates
				currentResponseObj.put(displayDueDatesKey, "none");
				break;
		}
		
		response.getWriter().write(Parser.gson.toJson(currentResponseObj, Map.class));
		response.getWriter().flush();
	}
}
